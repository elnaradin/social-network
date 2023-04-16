package ru.itgroup.intouch.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.model.PostTagEntity;
import ru.itgroup.intouch.repository.PostTagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class TagService {

    private final PostTagRepository postTagRepository;
    public Set<PostTagEntity> getTags(List<String> tags) {
        Set<PostTagEntity> tagEntities = new HashSet<>();
        tags.forEach(tag ->{
            tagEntities.add(getTag(tag));
        });
        return tagEntities;
    }
    private PostTagEntity getTag(String name) {
        Optional<PostTagEntity> optionalPostTag = postTagRepository.findByName(name);
        if (optionalPostTag.isPresent()) {
            return optionalPostTag.get();
        }
        PostTagEntity tagEntity = new PostTagEntity();
        tagEntity.setName(name);
        postTagRepository.save(tagEntity);
        return tagEntity;
    }
}
