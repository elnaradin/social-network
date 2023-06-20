package ru.itgroup.intouch.mapper;

import lombok.AllArgsConstructor;
import model.Comment;
import model.Post;
import model.Tag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.dto.PostDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Component
@AllArgsConstructor
public class MapperToCommentDto {
    private final ModelMapper modelMapper;
    private final PropertyManager propertyManager;

    public CommentDto getCommentDto(Comment comment) {
        TypeMap<Comment, CommentDto> propertyMapper = propertyManager.getCommentPropertyMapper();

        Converter<LocalDateTime, ZonedDateTime> converterDateTime = t -> ZonedDateTime.of(t.getSource(), ZoneId.systemDefault());
        propertyMapper.addMappings(mapper -> mapper.using(converterDateTime).map(Comment::getTime, CommentDto::setTime));
        propertyMapper.addMappings(mapper -> mapper.using(converterDateTime).map(Comment::getTimeChanged, CommentDto::setTimeChanged));
        return this.modelMapper.map(comment, CommentDto.class);
    }
}
