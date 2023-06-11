package mappers;

import dto.PostSearchDto;
import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor

public class PostDtoPageableMapper {

    public PostSearchDto mapToPostSearchDto(PostSearchDtoPageable postSearchDtoPageable) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(postSearchDtoPageable, PostSearchDto.class);
    }

    public Pageable mapToPageable(PostSearchDtoPageable dto) {

        return PageRequest.of(Integer.parseInt(dto.getPage()), Integer.parseInt(dto.getSize()), Sort.by(dto.getSort()));
    }

}
