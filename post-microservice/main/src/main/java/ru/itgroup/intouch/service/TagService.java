package ru.itgroup.intouch.service;
import lombok.AllArgsConstructor;
import model.post.PostTag;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.repository.PostTagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class TagService {
    private final PostTagRepository postTagRepository;
    public Set<PostTag> getTags(List<String> tags) {
        Set<PostTag> tagEntities = new HashSet<>();
        tags.forEach(tag ->{
            tagEntities.add(getTag(tag));
        });
        return tagEntities;
    }
    private PostTag getTag(String name) {
        Optional<PostTag> optionalPostTag = postTagRepository.findByName(name);
        if (optionalPostTag.isPresent()) {
            return optionalPostTag.get();
        }
        PostTag tagEntity = new PostTag();
        tagEntity.setName(name);
        postTagRepository.save(tagEntity);
        return tagEntity;
    }
}
