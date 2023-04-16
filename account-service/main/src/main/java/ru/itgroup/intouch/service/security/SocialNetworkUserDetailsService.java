package ru.itgroup.intouch.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.config.SocialNetworkUserDetails;
import ru.itgroup.intouch.model.UserEntity;
import ru.itgroup.intouch.model.UserMapper;
import ru.itgroup.intouch.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SocialNetworkUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userDto = userRepository.findFirstByEmail(username);
        if (userDto.isPresent()) {
            return new SocialNetworkUserDetails(mapper.userEntity2UserDto(userDto.get()));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
