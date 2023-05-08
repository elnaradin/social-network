package ru.itgroup.intouch.service.security;

import lombok.RequiredArgsConstructor;
import model.account.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.config.UserDetailsImpl;
import ru.itgroup.intouch.mapper.UserMapper;
import ru.itgroup.intouch.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userEntity = userRepository.findFirstByEmail(username);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(mapper.userEntity2UserDto(userEntity.get()));
    }
}
