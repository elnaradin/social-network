package ru.itgroup.intouch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import model.Notification;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.contracts.service.creators.NotificationCreator;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.dto.response.WsDto;
import ru.itgroup.intouch.mapper.NotificationMapper;
import ru.itgroup.intouch.repository.jooq.FriendRepository;
import ru.itgroup.intouch.repository.jooq.NotificationJooqRepository;
import ru.itgroup.intouch.repository.jooq.NotificationSettingRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationCreatorService {
    @Value("${aggregator.protocol}")
    private String protocol;

    @Value("${aggregator.host}")
    private String host;

    @Value("${aggregator.port}")
    private String port;

    @Value("${aggregator.websocket.http}")
    private String endpoint;

    @Value("${server.api.prefix}")
    private String apiPrefix;

    private NotificationCreator notificationCreator;

    private final ru.itgroup.intouch.repository.NotificationRepository notificationRepository;
    private final NotificationJooqRepository notificationJooqRepository;
    private final NotificationSettingRepository notificationSettingRepository;
    private final NotificationMapper notificationMapper;
    private final ObjectMapper objectMapper;
    private final NotificationCreatorFactory notificationCreatorFactory;
    private final FriendRepository friendRepository;

    public void createNotification(@NotNull NotificationRequestDto notificationRequestDto) throws Exception {
        notificationCreator = notificationCreatorFactory
                .getNotificationCreator(notificationRequestDto.getNotificationType());
        if (notificationRequestDto.getReceiverId() == null) {
            createMassNotifications(notificationRequestDto);
            return;
        }

        createSingleNotification(notificationRequestDto);
    }

    private void createMassNotifications(@NotNull NotificationRequestDto notificationRequestDto) throws Exception {
        Set<Long> receiverIdList = new HashSet<>(
                friendRepository
                        .getReceiverIds(notificationRequestDto.getAuthorId(), notificationCreator.getTableField())
        );

        if (receiverIdList.isEmpty()) {
            return;
        }

        List<NotificationDto> notificationDtoList = new ArrayList<>();
        for (Long receiverId : receiverIdList) {
            notificationRequestDto.setReceiverId(receiverId);
            notificationDtoList.add(notificationCreator.create(notificationRequestDto));
        }

        List<Long> notificationIdList = notificationJooqRepository.saveNotifications(notificationDtoList);
        List<Notification> notifications = notificationRepository.findAllById(notificationIdList);
        for (Notification notification : notifications) {
            sendToWs(notification);
        }
    }

    private void createSingleNotification(@NotNull NotificationRequestDto notificationRequestDto) throws Exception {
        boolean isEnableNotification = notificationSettingRepository
                .isEnable(notificationRequestDto.getAuthorId(), notificationCreator.getTableField());
        if (!isEnableNotification) {
            return;
        }

        NotificationDto notificationDto = notificationCreator.create(notificationRequestDto);
        long notificationId = notificationJooqRepository.saveNotificationFromDto(notificationDto);

        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        sendToWs(notification);
    }

    private void sendToWs(Notification notification) throws IOException {
        if (notification == null) {
            return;
        }

        ru.itgroup.intouch.dto.response.notifications.NotificationDto notificationDto = notificationMapper
                .getDestination(notification);
        String json = objectMapper.writeValueAsString(new WsDto(notificationDto));

        URL url = new URL(protocol + host + ":" + port + apiPrefix + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream stream = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            stream.write(input, 0, input.length);
        }

        connection.getResponseCode();
    }
}
