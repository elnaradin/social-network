package ru.itgroup.intouch.mapper;

import model.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itgroup.intouch.dto.message.SendMessageDto;

import java.time.Instant;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface SendMessageDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "SENT")
    @Mapping(target = "time", qualifiedByName = "secondsToInstant")
    Message toMessage(SendMessageDto messageDto);

    @Named("secondsToInstant")
    static Instant toInstant(Long time) {
        return Instant.from(Instant.ofEpochMilli(time).atZone(ZoneId.of("Europe/Moscow")));
    }
}
