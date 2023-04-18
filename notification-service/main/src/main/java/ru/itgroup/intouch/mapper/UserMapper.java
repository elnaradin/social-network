package ru.itgroup.intouch.mapper;

import com.googlecode.jmapper.JMapper;
import model.User;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.response.notifications.AuthorDto;

@Component
public class UserMapper {
    private final JMapper<AuthorDto, User> mapper = new JMapper<>(AuthorDto.class, User.class);

    public AuthorDto getDestination(User user) {
        return mapper.getDestination(user);
    }
}
