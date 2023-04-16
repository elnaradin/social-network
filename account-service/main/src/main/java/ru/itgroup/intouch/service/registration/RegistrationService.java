package ru.itgroup.intouch.service.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.model.AccountEntity;
import ru.itgroup.intouch.model.UserMapper;
import ru.itgroup.intouch.repository.AccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerNewUser(RegistrationDto registrationDto) {
        AccountEntity accountEntity = userMapper.registrationDto2AccountEntity(registrationDto);
        accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
        accountRepository.save(accountEntity);
    }

    public AccountDto getUserInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AccountEntity> account = accountRepository.findFirstByEmail(userDetails.getUsername());
        if (account.isPresent()) {
            return userMapper.accountEntityToAccountDto(account.get());
        } else {
            throw new UsernameNotFoundException("account not found");
        }

    }
}
