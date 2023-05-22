package ru.itgroup.intouch.aggregator.controller.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api.prefix}")
public class WsController {
    private final NotificationHandler notificationHandler;

    @PostMapping("${SN_WEBSOCKET_HTTP_ENDPOINT}")
    public ResponseEntity<?> sendToWs(@RequestBody String json) throws IOException {
        notificationHandler.sendMessage(json);
        return ResponseEntity.ok("");
    }
}
