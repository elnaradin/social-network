package ru.itgroup.intouch.service.account;

import lombok.RequiredArgsConstructor;
import model.account.Account;
import model.account.User;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.UserAlreadyRegisteredException;
import ru.itgroup.intouch.mapper.UserMapper;
import ru.itgroup.intouch.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void registerNewUser(RegistrationDto registrationDto) {
        if (!Objects.equals(registrationDto.getCaptchaCode(), registrationDto.getCaptchaSecret())) {
            throw new CaptchaNotValidException("Капча введена неверно.");
        }
        Optional<User> firstByEmail = userRepository.findFirstByEmail(registrationDto.getEmail());
        if (firstByEmail.isPresent()) {
            throw new UserAlreadyRegisteredException("Пользователь с адресом \"" +
                    registrationDto.getEmail() + "\" уже зарегистрирован.");
        }
        Account accountEntity = userMapper.registrationDto2AccountEntity(registrationDto);
        accountEntity.setCreatedOn(LocalDateTime.now());
        accountEntity.setRegDate(LocalDateTime.now());
        accountEntity.setUpdateOn(LocalDateTime.now());
        userRepository.save(accountEntity);
    }


}

