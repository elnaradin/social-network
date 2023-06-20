package ru.itgroup.intouch.service;

import model.message.Dialog;
import ru.itgroup.intouch.dto.message.SendMessageDto;
import ru.itgroup.intouch.dto.parents.ResponseDTO;

public interface DialogService {

    ResponseDTO getDialogsPage(Integer accountId, Integer offset, Integer itemPerPage);

    Dialog saveDialogInfoBySendMessage(SendMessageDto sendMessageDto);

    Long getDialogId(Integer userId, Long valueOf);
}
