package ru.itgroup.intouch.service;

import lombok.AllArgsConstructor;
import model.Tag;
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

    public Set<Tag> getTags(List<String> tags) {
        Set<Tag> tagEntities = new HashSet<>();
        tags.forEach(tag -> {
            tagEntities.add(getTag(tag));
        });
        return tagEntities;
    }

    private Tag getTag(String name) {
        Optional<Tag> optionalPostTag = postTagRepository.findByName(name);
        if (optionalPostTag.isPresent()) {
            return optionalPostTag.get();
        }
        Tag tagEntity = new Tag();
        tagEntity.setName(name);
        postTagRepository.save(tagEntity);
        return tagEntity;
    }
}
