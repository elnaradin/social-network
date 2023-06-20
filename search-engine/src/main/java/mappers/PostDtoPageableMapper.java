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

        String pageFromDto = (dto.getPage() == null || dto.getPage().equals("-1")) ? "0" : dto.getPage();
        String sizeFromDto = (dto.getSize() == null) ? "5" : dto.getSize();

        String[] sort = dto.getSort().split(",");

        String sortBy = (sort[0].equals("time")) ? "timeChanged" : sort[0];
        Sort sortWay;
        if (sort.length > 1) {
            sortWay = (sort[1].equals("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        } else {
            sortWay = Sort.by(sortBy).ascending();
        }

        return PageRequest.of(Integer.parseInt(pageFromDto), Integer.parseInt(sizeFromDto), sortWay);
    }

}
