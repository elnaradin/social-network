package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.dialog.ResponseDialogDTO;
import ru.itgroup.intouch.dto.message.MessageStatusUpdateResponseDto;
import ru.itgroup.intouch.dto.message.ResponseDialogMessageDTO;
import ru.itgroup.intouch.dto.message.SendMessageDto;
import ru.itgroup.intouch.dto.message.UnreadMessageResponseDTO;
import ru.itgroup.intouch.dto.parents.ResponseDTO;
import ru.itgroup.intouch.dto.parents.ResponseMessage;
import ru.itgroup.intouch.service.DialogService;
import ru.itgroup.intouch.service.MessageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dialogs")
public class DialogController {

    private final DialogService dialogService;
    private final MessageService messageService;

    @GetMapping
    public ResponseDialogDTO getAllDialogs(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "20") Integer itemPerPage,
            @RequestParam Integer userId
    ) {
        ResponseDialogDTO pageDTO = (ResponseDialogDTO)dialogService.getDialogsPage(userId, offset, itemPerPage);
        return pageDTO;
    }

    @GetMapping("/unreaded")
    public UnreadMessageResponseDTO getUnreadMessageCount(@RequestParam Integer userId) {
        UnreadMessageResponseDTO responseDTO = messageService.getUnreadMessageCountByAccountId(userId);
        return responseDTO;
    }

    @GetMapping("/messages")
    public ResponseDialogMessageDTO getAllMessages(
            @RequestParam String companionId, @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer itemPerPage, @RequestParam Integer userId
    ) {
        Long dialogId = dialogService.getDialogIdOrCreateNew(userId, Long.valueOf(companionId));
        ResponseDTO responseDTO = messageService.findAllByDialogId(dialogId,
                offset, itemPerPage);
        return (ResponseDialogMessageDTO) responseDTO;
    }

    @PutMapping("/messages/read")
    public MessageStatusUpdateResponseDto setStatusMessageRead(@RequestParam String companionId,
                                                               @RequestParam Integer userId){
        Long dialogId = dialogService.getDialogId(userId, Long.valueOf(companionId));
        messageService.updateMessageStatus(dialogId, Long.valueOf(userId));

        MessageStatusUpdateResponseDto responseDto = new MessageStatusUpdateResponseDto();
        responseDto.setData(new ResponseMessage("ok"));

        return responseDto;
    }
}
