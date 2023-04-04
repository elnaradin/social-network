package ru.itgroup.intouch.service;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.mapper.MapperToPostDto;
import ru.itgroup.intouch.model.PostEntity;
import ru.itgroup.intouch.repository.PostRepository;
import ru.itgroup.intouch.dto.PostDto;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postsRepository;
    private final MapperToPostDto mapperToPostDto;
    public PostDto getPostById(Long id) {
        Optional<PostEntity> postEntity = postsRepository.findById(id);
        return postEntity.map(mapperToPostDto::getPostDto).orElse(null);
    }


}
