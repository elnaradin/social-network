package ru.itgroup.intouch.mapper;

import lombok.AllArgsConstructor;
import model.Post;
import model.Tag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.PostDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        Converter<LocalDateTime, ZonedDateTime> converterDateTime = t -> ZonedDateTime.of(t.getSource(), ZoneId.systemDefault());
        propertyMapper.addMappings(mapper -> mapper.map(Post::getPostType, PostDto::setPostType));
        propertyMapper.addMappings(mapper -> mapper.using(collectionToTagString).map(Post::getPostTags, PostDto::setPostTags));
        propertyMapper.addMappings(mapper -> mapper.using(converterDateTime).map(Post::getTimeChanged, PostDto::setTimeChanged));
        propertyMapper.addMappings(mapper -> mapper.using(converterDateTime).map(Post::getCreatedDate, PostDto::setTime));
        propertyMapper.addMappings(mapper -> mapper.using(converterDateTime).map(Post::getPublishDate, PostDto::setPublishDate));
        return this.modelMapper.map(postEntity, PostDto.class);
    }

}
