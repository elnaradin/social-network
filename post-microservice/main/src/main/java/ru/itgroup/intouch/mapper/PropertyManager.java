package ru.itgroup.intouch.mapper;

import model.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.PostDto;

@Component
public class PropertyManager {

    private final ModelMapper modelMapper;

    public PropertyManager(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(Post.class, PostDto.class);
    }

    public TypeMap<Post, PostDto> getPostPropertyMapper() {
        return this.modelMapper.getTypeMap(Post.class, PostDto.class);
    }


}
