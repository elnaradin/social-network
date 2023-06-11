package mappers;

import dto.AccountSearchDto;
import dto.AccountSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountDtoPageableMapper {

    public AccountSearchDto mapToAccountSearchDto(AccountSearchDtoPageable dto) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, AccountSearchDto.class);
    }

    public Pageable mapToPageable(AccountSearchDtoPageable dto) {

        return PageRequest.of(Integer.parseInt(dto.getPage()), Integer.parseInt(dto.getSize()), Sort.by(dto.getSort()));

    }


}
