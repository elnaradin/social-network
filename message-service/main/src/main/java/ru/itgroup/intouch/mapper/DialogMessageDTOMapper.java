package ru.itgroup.intouch.mapper;

import model.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itgroup.intouch.dto.message.DialogMessageDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DialogMessageDTOMapper {

    @Mapping(source = "message.id", target = "id")
    @Mapping(source = "message.isDeleted", target = "deleted")
    @Mapping(source = "message.time", target = "time", qualifiedByName = "instantToSeconds")
    @Mapping(source = "message.authorId", target = "authorId")
    @Mapping(source = "message.messageText", target = "messageText")
    DialogMessageDTO toDialogMessageDTO(Message message);

    List<DialogMessageDTO> toDialogMessageDTO(Collection<Message> messages);

    @Named("instantToSeconds")
    static Long toSeconds(Instant time) {
        return time.atZone(ZoneId.of("Europe/Moscow")).toEpochSecond();
    }
}
