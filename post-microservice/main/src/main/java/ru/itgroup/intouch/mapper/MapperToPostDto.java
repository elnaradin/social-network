package ru.itgroup.intouch.mapper;

import lombok.AllArgsConstructor;
import model.Post;
import model.Tag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.model.PostEntity;
import ru.itgroup.intouch.model.PostTagEntity;

import java.util.Collection;

@Component
@AllArgsConstructor
public class MapperToPostDto {

    private final ModelMapper modelMapper;
    private final PropertyManager propertyManager;

    public PostDto getPostDto(Post postEntity) {
        TypeMap<Post, PostDto> propertyMapper = propertyManager.getPostPropertyMapper();
        Converter<Collection<Tag>, Collection<String>> collectionToTagString =
                c -> c.getSource().stream().map(Tag::getName).toList();
        propertyMapper.addMappings(mapper -> mapper.map(Post::getType, PostDto::setType));
        propertyMapper.addMappings(mapper -> mapper.using(collectionToTagString).map(Post::getTags, PostDto::setTags));
        return this.modelMapper.map(postEntity, PostDto.class);
    }

}
