package mappers;

import dto.PostSearchDto;
import dto.PostSearchDtoPageable;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import model.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@RequiredArgsConstructor

public class PostDtoPageableMapper {


    public PostSearchDto mapToPostSearchDto(PostSearchDtoPageable postSearchDtoPageable) {

        PostSearchDto postSearchDto = new PostSearchDto();
        postSearchDto.setId(postSearchDtoPageable.getId());
        postSearchDto.setDeleted(postSearchDtoPageable.isDeleted());
        postSearchDto.setIds(postSearchDtoPageable.getIds());
        postSearchDto.setAccountIds(postSearchDtoPageable.getAccountIds());
        postSearchDto.setBlockedIds(postSearchDtoPageable.getBlockedIds());
        postSearchDto.setAuthor(postSearchDtoPageable.getAuthor());
        postSearchDto.setWithFriends(postSearchDtoPageable.isWithFriends());
        postSearchDto.setTags(postSearchDtoPageable.getTags());
        postSearchDto.setDateFrom(dateTimeFormatter(postSearchDtoPageable.getDateFrom()));
        postSearchDto.setDateTo(dateTimeFormatter(postSearchDtoPageable.getDateTo()));

        return postSearchDto;
    }

    public Pageable mapToPageable(PostSearchDtoPageable dto) {

        return PageRequest.of(Integer.parseInt(dto.getPage()), Integer.parseInt(dto.getSize()), Sort.by(dto.getSort()));
    }

    private LocalDateTime dateTimeFormatter(String dateTimeFromDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        return LocalDateTime.parse(dateTimeFromDto, formatter);

    }


}
