package ru.itgroup.intouch.mapper;

import model.Comment;
import model.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.dto.PostDto;

@Component
public class PropertyManager {

    private final ModelMapper modelMapper;

    public PropertyManager(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(Post.class, PostDto.class);
        this.modelMapper.createTypeMap(Comment.class, CommentDto.class);
    }

    public TypeMap<Post, PostDto> getPostPropertyMapper() {
        return this.modelMapper.getTypeMap(Post.class, PostDto.class);
    }

    public TypeMap<Comment, CommentDto> getCommentPropertyMapper() {
        return this.modelMapper.getTypeMap(Comment.class, CommentDto.class);
    }


}
