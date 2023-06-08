package mappers;

import dto.AccountSearchDto;
import dto.AccountSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountDtoPageableMapper {

    public AccountSearchDto mapToAccountSearchDto(AccountSearchDtoPageable dto) {

        AccountSearchDto accountSearchDto = new AccountSearchDto();

        accountSearchDto.setId(dto.getId());
        accountSearchDto.setDeleted(dto.isDeleted());
        accountSearchDto.setIds(dto.getIds());
        accountSearchDto.setBlockedByIds(dto.getBlockedByIds());
        accountSearchDto.setAuthor(dto.getAuthor());
        accountSearchDto.setFirstName(dto.getFirstName());
        accountSearchDto.setLastName(dto.getLastName());
        accountSearchDto.setCity(dto.getCity());
        accountSearchDto.setCountry(dto.getCountry());
        accountSearchDto.setBlocked(dto.isBlocked());
        accountSearchDto.setAgeFrom(dto.getAgeFrom());
        accountSearchDto.setAgeTo(dto.getAgeTo());

        return accountSearchDto;
    }

    public Pageable mapToPageable(AccountSearchDtoPageable dto) {

        return PageRequest.of(Integer.parseInt(dto.getPage()), Integer.parseInt(dto.getSize()), Sort.by(dto.getSort()));

    }


}
