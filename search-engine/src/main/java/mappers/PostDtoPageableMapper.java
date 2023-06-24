package mappers;

import dto.AccountSearchDto;
import dto.AccountSearchDtoPageable;
import dto.PostSearchDto;
import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.regex.Pattern;


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

        Sort sortWay;
        if (dto.getSort().contains("DESC") || dto.getSort().contains("desc")) {
           String sortBy = (dto.getSort().contains("time")) ? "publishDate" : dto.getSort().substring(0, (dto.getSort().length() - 6));
            sortWay = Sort.by(sortBy).descending();
        }
        else if (dto.getSort().contains("ASC") || dto.getSort().contains("asc")) {
            String sortBy = (dto.getSort().contains("time")) ? "publishDate" : dto.getSort().substring(0, (dto.getSort().length() - 5));
            sortWay = Sort.by(sortBy).ascending();

        } else {
            sortWay = Sort.by("createdDate").descending();
        }

        return PageRequest.of(Integer.parseInt(pageFromDto), Integer.parseInt(sizeFromDto), sortWay);
    }

}
