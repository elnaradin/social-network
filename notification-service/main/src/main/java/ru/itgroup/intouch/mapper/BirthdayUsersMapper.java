package ru.itgroup.intouch.mapper;

import org.jetbrains.annotations.NotNull;
import org.jooq.Record4;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.BirthdayUsersDto;

import static ru.itgroup.intouch.Tables.FRIENDS;
import static ru.itgroup.intouch.Tables.USERS;

@Component
public class BirthdayUsersMapper implements RecordMapper<Record4<Long, Long, String, String>, BirthdayUsersDto> {
    @Override
    public BirthdayUsersDto map(@NotNull Record4<Long, Long, String, String> record) {
        BirthdayUsersDto dto = new BirthdayUsersDto();
        dto.setAuthorId(record.get(FRIENDS.USER_ID_FROM));
        dto.setReceiverId(record.get(FRIENDS.USER_ID_TO));
        dto.setName(record.get(USERS.FIRST_NAME));
        dto.setLastName(record.get(USERS.LAST_NAME));
        return dto;
    }
}
