package ru.itgroup.intouch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itgroup.intouch.dto.dialog.ResponseDialogDTO;
import ru.itgroup.intouch.dto.message.MessageStatusUpdateResponseDto;
import ru.itgroup.intouch.dto.message.ResponseDialogMessageDTO;
import ru.itgroup.intouch.dto.message.SendMessageDto;
import ru.itgroup.intouch.dto.message.UnreadMessageResponseDTO;

@FeignClient(name = "message-service", url = "${SN_MESSAGE_HOST}:${SN_MESSAGE_PORT}",
        path = "/api/v1/dialogs")
public interface MessageServiceClient {
    @GetMapping("")
    public ResponseDialogDTO feignGetAllDialogs(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "20") Integer itemPerPage,
            @RequestParam Integer userId
    );

    @GetMapping("/unreaded")
    public UnreadMessageResponseDTO feignGetUnreadMessageCount(@RequestParam Integer userId);

    @GetMapping("/messages")
    public ResponseDialogMessageDTO feignGetAllMessages(
            @RequestParam String companionId, @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer itemPerPage, @RequestParam Integer userId
    );

    @PutMapping("/messages/read")
    public MessageStatusUpdateResponseDto feignSetStatusMessageRead(@RequestParam String companionId,
                                                                    @RequestParam Integer userId);
}
