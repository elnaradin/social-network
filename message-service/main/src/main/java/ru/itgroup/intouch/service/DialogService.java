package ru.itgroup.intouch.service;

import ru.itgroup.intouch.dto.message.SendMessageDto;
import ru.itgroup.intouch.dto.parents.ResponseDTO;

public interface DialogService {

    ResponseDTO getDialogsPage(Integer accountId, Integer offset, Integer itemPerPage);

    void saveDialogInfoBySendMessage(SendMessageDto sendMessageDto);

    Long getDialogId(Integer userId, Long valueOf);
}
