package ru.itgroup.intouch.service.notification.sender;

import lombok.RequiredArgsConstructor;
import model.Notification;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itgroup.intouch.contracts.service.notification.sender.NotificationObserver;
import ru.itgroup.intouch.dto.response.WsDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationDto;
import ru.itgroup.intouch.mapper.NotificationMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationWsObserver implements NotificationObserver {
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

    private final NotificationMapper notificationMapper;
    private final RestTemplate restTemplate;

    @Override
    public void send(Notification notification) {
        if (notification == null) {
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        NotificationDto notificationDto = notificationMapper.getDestination(notification);
        HttpEntity<WsDto> requestEntity = new HttpEntity<>(new WsDto(notificationDto), headers);
        String url = protocol + host + ":" + port + apiPrefix + endpoint;

        try {
            restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void send(@NotNull List<Notification> notifications) {
        notifications.forEach(this::send);
    }
}
