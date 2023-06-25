package mappers;

import dto.PostSearchDto;
import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostDtoPageableMapper {

    public PostSearchDto mapToPostSearchDto(PostSearchDtoPageable postSearchDtoPageable) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(postSearchDtoPageable, PostSearchDto.class);
    }

    public Pageable mapToPageable(@NotNull PostSearchDtoPageable dto) {
        log.info("i'm in mapToPageable at 26 string");

        String pageFromDto = (dto.getPage() == null || dto.getPage().equals("-1")) ? "0" : dto.getPage();
        String sizeFromDto = (dto.getSize() == null) ? "5" : dto.getSize();

        log.info("i'm in mapToPageable at 26 string");
        Sort sortWay;
        if (dto.getSort().contains("DESC") || dto.getSort().contains("desc")) {
            log.info("i'm in mapToPageable at 26 string");
            String sortBy = (dto.getSort().contains("time"))
                            ? "publishDate"
                            : dto.getSort().substring(0, (dto.getSort().length() - 6));
            sortWay = Sort.by(sortBy).descending();
            log.info("i'm in mapToPageable at 26 string");
        } else if (dto.getSort().contains("ASC") || dto.getSort().contains("asc")) {
            log.info("i'm in mapToPageable at 26 string");
            String sortBy = (dto.getSort().contains("time"))
                            ? "publishDate"
                            : dto.getSort().substring(0, (dto.getSort().length() - 5));
            sortWay = Sort.by(sortBy).ascending();
            log.info("i'm in mapToPageable at 26 string");
        } else {
            log.info("i'm in mapToPageable at 26 string");
            sortWay = Sort.by("createdDate").descending();
            log.info("i'm in mapToPageable at 26 string");
        }

        log.info("i'm in mapToPageable at 26 string");
        return PageRequest.of(Integer.parseInt(pageFromDto), Integer.parseInt(sizeFromDto), sortWay);
    }

}
