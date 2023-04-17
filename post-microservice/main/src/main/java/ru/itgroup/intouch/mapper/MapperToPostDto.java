package ru.itgroup.intouch.mapper;
import lombok.AllArgsConstructor;
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
    public PostDto getPostDto(PostEntity postEntity) {
        TypeMap<PostEntity, PostDto> propertyMapper = propertyManager.getPostPropertyMapper();
        Converter<Collection<PostTagEntity>, Collection<String>> collectionToTagString =
                c -> c.getSource().stream().map(PostTagEntity::getName).toList();
        propertyMapper.addMappings(mapper -> mapper.map(src -> src.getType().getCode(), PostDto::setType));
        propertyMapper.addMappings(mapper -> mapper.using(collectionToTagString).map(PostEntity::getTags, PostDto::setTags));
        return this.modelMapper.map(postEntity, PostDto.class);
    }

}
