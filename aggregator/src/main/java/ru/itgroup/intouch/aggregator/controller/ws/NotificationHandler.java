package ru.itgroup.intouch.aggregator.controller.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itgroup.intouch.aggregator.config.security.jwt.JWTUtil;
import ru.itgroup.intouch.aggregator.utils.CookieUtil;
import ru.itgroup.intouch.dto.message.SendMessageDto;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class NotificationHandler extends TextWebSocketHandler {
    @Value("${spring.kafka.message-serv}")
    private String messageTopic;

    private final CookieUtil cookieUtil;
    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;
    private final KafkaTemplate<Long, SendMessageDto> kafkaTemplate;

    private final ConcurrentHashMap<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws IOException {
        Long userId = getUserId(session);
        if (userId == null) {
            session.close();
            return;
        }

        sessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status)
            throws IOException {
        Long userId = getUserId(session);
        if (userId == null) {
            session.close();
            return;
        }

        sessions.remove(userId).close();
    }

    public void sendMessage(String notification) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(notification).get("data");
        if (jsonNode == null) {
            return;
        }

        long receiverId = jsonNode.get("receiverId").asLong(0);
        if (sessions.containsKey(receiverId)) {
            sessions.get(receiverId).sendMessage(new TextMessage(notification));
        }
    }

    public void sendDialogMessage(String message) throws IOException {
        if (message.isEmpty()) {
            return;
        }

        long recipientId = objectMapper.readTree(message).get("recipientId").asLong();

        if (sessions.containsKey(recipientId)) {
            TextMessage textMessage = new TextMessage(message);
            sessions.get(recipientId).sendMessage(textMessage);
        }
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message)
            throws IOException {
        JsonNode jsonNode = objectMapper.readTree(message.getPayload()).get("data");
        SendMessageDto messageDto = objectMapper.treeToValue(jsonNode, SendMessageDto.class);

        kafkaTemplate.send(messageTopic, messageDto);
    }

    private @Nullable Long getUserId(@NotNull WebSocketSession session) {
        String jwt = cookieUtil.getJwt(session.getHandshakeHeaders());
        if (jwt == null) {
            return null;
        }

        return jwtUtil.extractUserId(jwt).longValue();
    }
}
