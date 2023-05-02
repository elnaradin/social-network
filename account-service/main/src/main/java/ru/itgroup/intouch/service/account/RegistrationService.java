package ru.itgroup.intouch.service.account;

import lombok.RequiredArgsConstructor;
import model.account.Account;
import model.account.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.UserAlreadyExistsException;
import ru.itgroup.intouch.mapper.UserMapper;
import ru.itgroup.intouch.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void registerNewUser(RegistrationDto registrationDto) throws UserAlreadyExistsException, CaptchaNotValidException {
        if (!Objects.equals(registrationDto.getCaptchaCode(), registrationDto.getCaptchaSecret())) {
            throw new CaptchaNotValidException("entered captcha code doesn't equal secret");
        }
        Optional<User> firstByEmail = userRepository.findFirstByEmail(registrationDto.getEmail());
        if (firstByEmail.isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        Account accountEntity = userMapper.registrationDto2AccountEntity(registrationDto);
        accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
        accountEntity.setCreatedOn(LocalDateTime.now());
        accountEntity.setRegDate(LocalDateTime.now());
        accountEntity.setUpdateOn(LocalDateTime.now());
        userRepository.save(accountEntity);
    }


}

