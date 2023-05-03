package ru.itgroup.intouch.model;


import model.Account;
import model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userEntity2UserDto(User userEntity);
    @Mapping(target="password", source="password1")
    Account registrationDto2AccountEntity(RegistrationDto registrationDto);

    AccountDto accountEntityToAccountDto(Account accountEntity);
}
