package ru.itgroup.intouch.aggregator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.client.MessageServiceClient;
import ru.itgroup.intouch.dto.dialog.ResponseDialogDTO;
import ru.itgroup.intouch.dto.message.MessageStatusUpdateResponseDto;
import ru.itgroup.intouch.dto.message.ResponseDialogMessageDTO;
import ru.itgroup.intouch.dto.message.UnreadMessageResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dialogs")
public class DialogController {
    private final MessageServiceClient client;
    private final AccountServiceClient accountClient;

    @GetMapping
    public ResponseDialogDTO getAllDialogs(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "20") Integer itemPerPage
    ) {
        Integer userId = getAccountId();
        return client.feignGetAllDialogs(offset, itemPerPage, userId);
    }

    @GetMapping("/unreaded")
    public UnreadMessageResponseDTO getUnreadMessageCount() {
        Integer userId = getAccountId();
        return client.feignGetUnreadMessageCount(userId);
    }

    @GetMapping("/messages")
    public ResponseDialogMessageDTO getAllMessages(
            @RequestParam String companionId, @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer itemPerPage) {
        Integer userId = getAccountId();
        return client.feignGetAllMessages(companionId, offset, itemPerPage, userId);
    }

    @PutMapping("/{companionId}")
    public MessageStatusUpdateResponseDto setStatusMessageRead(@PathVariable String companionId){
        Integer userId = getAccountId();
        return client.feignSetStatusMessageRead(companionId, userId);
    }

    private Integer getAccountId(){
        return accountClient.myAccount().getId();
    }
}
