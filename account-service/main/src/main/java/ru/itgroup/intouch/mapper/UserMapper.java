package ru.itgroup.intouch.mapper;


import model.account.Account;
import model.account.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userEntity2UserDto(User entity);

    @Mapping(target = "password", source = "password1")
    Account registrationDto2AccountEntity(RegistrationDto dto);

    AccountDto accountEntityToAccountDto(Account entity);
    List<AccountDto> accountsToDtos(List<Account> accountList);

    @Mapping(target = "regDate", qualifiedByName = "stringToLDT")
    @Mapping(target = "birthDate", qualifiedByName = "stringToLDT")
    @Mapping(target = "createdOn", qualifiedByName = "stringToLDT")
    @Mapping(target = "updateOn", qualifiedByName = "stringToLDT")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateAccountFromDto(AccountDto dto, @MappingTarget Account entity);

    @Named("stringToLDT")
    static LocalDateTime toLocalDateTime(String date) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        if (date == null) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

}
