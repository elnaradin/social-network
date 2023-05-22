package ru.itgroup.intouch.aggregator.controller.ws;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itgroup.intouch.aggregator.utils.CookieUtil;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class NotificationHandler extends TextWebSocketHandler {
    private final CookieUtil cookieUtil;

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws IOException {
        // todo: заменить на юзер_ид
        String username = cookieUtil.getUsernameFromCookie(session.getHandshakeHeaders());
        if (username == null) {
            session.close();
            return;
        }

        sessions.put(username, session);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status)
            throws IOException {
        // todo: заменить на юзер_ид
        String username = cookieUtil.getUsernameFromCookie(session.getHandshakeHeaders());
        if (username == null) {
            return;
        }

        sessions.remove(username).close();
    }

    public void sendMessage(String notification) throws IOException {
        // todo: сделать рассылку уведомлений только по нужным сессиям
        for (WebSocketSession session : sessions.values()) {
            session.sendMessage(new TextMessage(notification));
        }
    }
}
