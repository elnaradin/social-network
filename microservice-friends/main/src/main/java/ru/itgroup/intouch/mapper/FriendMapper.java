package ru.itgroup.intouch.mapper;

import model.account.Account;
import model.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itgroup.intouch.dto.FriendDto;


@Mapper(componentModel = "spring")
public interface FriendMapper {

    @Mapping(source = "friend.statusCode", target = "statusCode")
    @Mapping(source = "friend.userIdFrom.id", target = "accountFrom")
    @Mapping(source = "friend.userIdTo.id", target = "accountTo")
    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.photo", target = "photo")
    @Mapping(source = "account.firstName", target = "firstName")
    @Mapping(source = "account.lastName", target = "lastName")
    @Mapping(source = "account.city", target = "city")
    @Mapping(source = "account.country", target = "country")
    @Mapping(source = "account.birthDate", target = "birthDate")
    @Mapping(source = "previousStatusCode", target = "previousStatusCode")
    FriendDto toFriendDto(Friend friend, Account account, String previousStatusCode);
}