package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itgroup.intouch.dto.message.DialogMessageDTO;
import ru.itgroup.intouch.dto.message.ResponseDialogMessageDTO;
import ru.itgroup.intouch.dto.message.UnreadMessageResponseDTO;
import ru.itgroup.intouch.mapper.DialogMessageDTOMapper;
import ru.itgroup.intouch.repository.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final DialogMessageDTOMapper dialogMessageDTOMapper;


    public UnreadMessageResponseDTO getUnreadMessageCountByAccountId(Integer accountId){
        UnreadMessageResponseDTO responseDTO = new UnreadMessageResponseDTO();
        responseDTO.setData(messageRepository.getUnreadMessageCountByAccountId(accountId));
        return responseDTO;
    }

    public ResponseDialogMessageDTO findAllByDialogId(Long dialogId, Integer offset, Integer itemPerPage){

        ResponseDialogMessageDTO responseDTO = new ResponseDialogMessageDTO();
        Pageable pageable = PageRequest.of(offset, itemPerPage, Sort.by("time").descending());

        Page<Message> messages = messageRepository.findAllByDialogId(dialogId, pageable);
        List<DialogMessageDTO> messageDTOS = dialogMessageDTOMapper.toDialogMessageDTO(messages.getContent());

        responseDTO.setOffset(offset);
        responseDTO.setPerPage(itemPerPage);
        responseDTO.setTotal(messages.getTotalPages());
        responseDTO.setData(messageDTOS);

        return responseDTO;
    }

    @Transactional
    @Override
    public void updateMessageStatus(Long dialogId, Long userId) {
        messageRepository.updateMessageStatus(dialogId, userId);
    }
}
