package ru.itgroup.intouch.mapper;

import model.message.AccountDialog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.dialog.DialogDTO;

@Mapper(uses = {LastMessageDTOMapper.class, ConversationPartnerDTOMapper.class}, componentModel = "spring")
public interface DialogDTOMapper {

    @Mapping(source = "accountDialog.dialogId", target = "id")
    @Mapping(source = "accountDialog.dialogIsDeleted", target = "deleted")
    @Mapping(source = "accountDialog.dialogLastMessage", target = "lastMessage")
    @Mapping(source = "conversationPartner", target = "conversationPartner")
    DialogDTO toDialogDto(AccountDialog accountDialog, AccountDto conversationPartner);
}
