package ru.itgroup.intouch.mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.model.PostEntity;

@Component
public class PropertyManager {

    private final ModelMapper modelMapper;
    public PropertyManager(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(PostEntity.class, PostDto.class);
    }

    public TypeMap<PostEntity, PostDto> getPostPropertyMapper() {
        return this.modelMapper.getTypeMap(PostEntity.class, PostDto.class);
    }


}
