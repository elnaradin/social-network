package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.message.Dialog;
import model.message.Message;
import model.message.AccountDialog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.dialog.DialogDTO;
import ru.itgroup.intouch.dto.dialog.ResponseDialogDTO;
import ru.itgroup.intouch.dto.errors.IllegalRequestParameterException;
import ru.itgroup.intouch.dto.message.SendMessageDto;
import ru.itgroup.intouch.mapper.DialogDTOMapper;
import ru.itgroup.intouch.mapper.SendMessageDtoMapper;
import ru.itgroup.intouch.repository.AccountDialogRepository;
import ru.itgroup.intouch.repository.DialogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {

    private final DialogRepository dialogRepository;
    private final AccountDialogRepository accountDialogRepository;
    private final DialogDTOMapper dialogDTOMapper;
    private final SendMessageDtoMapper sendMessageDtoMapper;
    private final AccountServiceClient accountClient;

    @Transactional
    public Dialog saveDialogInfoBySendMessage(SendMessageDto sendMessage){
        Optional<AccountDialog> accountDialog = accountDialogRepository
                .findByAccountIdAndRecipientId(sendMessage.getAuthorId(), sendMessage.getRecipientId());

        Dialog dialog = accountDialog.isPresent() ? accountDialog.get().getDialog() :
                dialogRepository.save(createNewDialog(sendMessage.getAuthorId(), sendMessage.getRecipientId()));
        Message message = sendMessageDtoMapper.toMessage(sendMessage);
        message.setDialog(dialog);
        dialog.setLastMessage(message);

        return dialogRepository.save(dialog);
    }

    private Dialog createNewDialog(Long firstAccountId, Long secondAccountId){
        Dialog dialog = new Dialog();
        dialog.setAccounts(new ArrayList<>());
        dialog.addAccount(createNewAccountDialog(dialog, firstAccountId, secondAccountId));
        dialog.addAccount(createNewAccountDialog(dialog, secondAccountId, firstAccountId));

        return dialog;
    }

    private AccountDialog createNewAccountDialog(Dialog dialog, Long accountId, Long recipientId){
        AccountDialog accountDialog = new AccountDialog();
        accountDialog.setAccountId(accountId);
        accountDialog.setRecipientId(recipientId);
        accountDialog.setDialog(dialog);

        return accountDialog;
    }

    @Override
    public Long getDialogId(Integer firstUserId, Long secondUserId) {
        return accountDialogRepository.findByAccountIdAndRecipientId(Long.valueOf(firstUserId), secondUserId)
                .orElseThrow(() -> new IllegalRequestParameterException(
                        "the requested dialog between the given users does not exist"))
                .getDialogId();

    }

    @Override
    public Long getDialogIdOrCreateNew(Integer firstUserId, Long secondUserId) {
        Optional<AccountDialog> accountDialog = accountDialogRepository
                .findByAccountIdAndRecipientId(Long.valueOf(firstUserId), secondUserId);
        if(accountDialog.isPresent()){
            return accountDialog.get().getDialogId();
        }
        Dialog newDialog = dialogRepository.save(createNewDialog(Long.valueOf(firstUserId), secondUserId));
        return newDialog.getId();
    }

    @Transactional
    public ResponseDialogDTO getDialogsPage(Integer accountId, Integer offset, Integer itemPerPage){
        Pageable pageable = PageRequest.of(offset, itemPerPage);
        Page<AccountDialog> dialogPage = accountDialogRepository.getDialogsPageByAccountId(accountId, pageable);

        List<AccountDialog> accountDialogs = dialogPage.getContent();

        List<DialogDTO> data = new ArrayList<>();

        if(!accountDialogs.isEmpty()){
            List<Long> userIds = accountDialogs.stream().map(AccountDialog::getRecipientId).toList();
            Map<Integer, AccountDto> recipients = accountClient.accounts(userIds)
                    .stream()
                    .collect(Collectors.toMap(AccountDto::getId, Function.identity()));

            accountDialogs.forEach(d -> {
                DialogDTO dialogDTO = dialogDTOMapper.toDialogDto(d, recipients.get(d.getRecipientId().intValue()));
                dialogDTO.setUnreadCount(dialogRepository.getUnreadMessageCount(d.getAccountId(),d.getDialogId()));
                data.add(dialogDTO);
            });
        }
        ResponseDialogDTO responseDTO = new ResponseDialogDTO();
        responseDTO.setPerPage(itemPerPage);
        responseDTO.setOffset(offset);
        responseDTO.setCurrentUserId(accountId);
        responseDTO.setTotal(dialogPage.getTotalPages());
        responseDTO.setData(data);

        return responseDTO;
    }
}
