package ru.itgroup.intouch.service;

import ru.itgroup.intouch.dto.message.UnreadMessageResponseDTO;
import ru.itgroup.intouch.dto.parents.ResponseDTO;

public interface MessageService {
    UnreadMessageResponseDTO getUnreadMessageCountByAccountId(Integer accountId);

    ResponseDTO findAllByDialogId(Long accountId, Integer offset, Integer itemPerPage);

    void updateMessageStatus(Long dialogId, Long userId);
}
