package ru.itgroup.intouch.mapper;

import model.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itgroup.intouch.dto.message.LastMessageDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LastMessageDTOMapper {
    @Mapping(source = "dialogId", target = "dialogId")
    @Mapping(source = "status", target = "readStatusDto")
    @Mapping(target = "time", qualifiedByName = "instantToSeconds")
    LastMessageDTO toDialogMessageDTO(Message message);

    List<LastMessageDTO> toDialogMessageDTO(Collection<Message> messages);

    @Named("instantToSeconds")
    static Long toInstant(Instant time) {
        return time.atZone(ZoneId.of("Europe/Moscow")).toEpochSecond();
    }
}
