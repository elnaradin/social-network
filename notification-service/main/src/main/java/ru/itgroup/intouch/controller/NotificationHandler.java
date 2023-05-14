package ru.itgroup.intouch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itgroup.intouch.dto.response.notifications.NotificationDto;
import ru.itgroup.intouch.service.NotificationService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationHandler extends TextWebSocketHandler {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status)
            throws IOException {
        sessions.remove(session.getId()).close();
    }

    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message)
            throws IOException, InterruptedException {
        Thread.sleep(2000);
        WebSocketSession userSession = sessions.get(session.getId());
        userSession.sendMessage(notificationService.getRandomNotification());
    }

    public void sendNotification(NotificationDto notification) {
        // todo: сделать рассылку уведомлений только по нужным сессиям
        for (WebSocketSession session : sessions.values()) {
            try {
                String json = objectMapper.writeValueAsString(notification);
                session.sendMessage(new TextMessage(json));
            } catch (IOException ignored) {
            }
        }
    }
}
