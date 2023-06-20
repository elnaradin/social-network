package ru.itgroup.intouch.listners;

import lombok.RequiredArgsConstructor;
import model.message.Dialog;
import model.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.message.SendMessageDto;
import ru.itgroup.intouch.dto.notifications.NotificationMessageDto;
import ru.itgroup.intouch.dto.notifications.WebSocketMessageDto;
import ru.itgroup.intouch.dto.parents.WebSocketDto;
import ru.itgroup.intouch.mapper.DialogMessageDTOMapper;
import ru.itgroup.intouch.service.DialogService;

@Component
@RequiredArgsConstructor
public class MessageKafkaListener {

    @Value("${spring.kafka.message-event}")
    private  String messageTopic;
    @Value("${spring.kafka.notification-event}")
    private  String notificationTopic;
    private final DialogService dialogService;
    private final DialogMessageDTOMapper mapper;
    private final KafkaTemplate<Long, WebSocketDto> kafkaTemplate;
    private final String webSocketMessageType = "MESSAGE";

    @KafkaListener(topics = "${spring.kafka.message-serv}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(SendMessageDto sendMessageDto) {
        Dialog dialog = dialogService.saveDialogInfoBySendMessage(sendMessageDto);
        Message message = dialog.getLastMessage();

        WebSocketMessageDto webSocketMessage = new WebSocketMessageDto(webSocketMessageType,
                message.getRecipientId(), mapper.toDialogMessageDTO(message));

        kafkaTemplate.send(messageTopic, webSocketMessage);

        NotificationMessageDto notificationMessageDto = new NotificationMessageDto(webSocketMessageType,
                sendMessageDto.getAuthorId(), sendMessageDto.getRecipientId());

        kafkaTemplate.send(notificationTopic, notificationMessageDto);
    }
}


