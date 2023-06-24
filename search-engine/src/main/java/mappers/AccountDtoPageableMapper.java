package mappers;

import dto.AccountSearchDto;
import dto.AccountSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AccountDtoPageableMapper {

    public AccountSearchDto mapToAccountSearchDto(AccountSearchDtoPageable dto) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, AccountSearchDto.class);
    }

    public Pageable mapToPageable(AccountSearchDtoPageable dto) {

        Sort sortWay;
        if (dto.getSort().contains("DESC") || dto.getSort().contains("desc")) {
            String sortBy = dto.getSort().substring(0, (dto.getSort().length() - 6));
            sortWay = Sort.by(sortBy).descending();
        }
        else if (dto.getSort().contains("ASC") || dto.getSort().contains("asc")) {
            String sortBy = dto.getSort().substring(0, (dto.getSort().length() - 5));
            sortWay = Sort.by(sortBy).ascending();

        } else {
            sortWay = Sort.by("lastName").descending();
        }

        return PageRequest.of(Integer.parseInt(dto.getPage()), Integer.parseInt(dto.getSize()), sortWay);

    }


}
