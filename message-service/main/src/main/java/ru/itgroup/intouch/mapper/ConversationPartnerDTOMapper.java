package ru.itgroup.intouch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.dialog.ConversationPartner;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface ConversationPartnerDTOMapper {

    @Mapping(target = "regDate", source = "regDate", qualifiedByName = "localDateTimeToSecond")
    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "localDateTimeToSecond",
            dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Mapping(target = "lastOnlineTime", source = "lastOnlineTime", qualifiedByName = "localDateTimeToSecond")
    @Mapping(target = "createdOn", source = "createdOn", qualifiedByName = "localDateTimeToSecond")
    @Mapping(target = "updatedOn", source = "updateOn", qualifiedByName = "localDateTimeToSecond")
    @Mapping(target = "password", ignore = true)
    ConversationPartner toConversationPartner(AccountDto account);

    @Named("localDateTimeToSecond")
    static Long toSeconds(LocalDateTime time) {
        return time.atZone(ZoneId.of("Europe/Moscow")).toInstant().toEpochMilli();
    }
}
