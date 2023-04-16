package ru.itgroup.intouch.model;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userEntity2UserDto(UserEntity userEntity);
    @Mapping(target="password", source="password1")
    AccountEntity registrationDto2AccountEntity(RegistrationDto registrationDto);

    AccountDto accountEntityToAccountDto(AccountEntity accountEntity);
}
