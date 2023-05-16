package ru.itgroup.intouch.service.search;

import Filters.AccountFilterBuilder;
import dto.AccountSearchDto;
import lombok.RequiredArgsConstructor;
import model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.repository.AccountRepository;
import searchUtils.Filter;
import searchUtils.SpecificationBuilder;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountSearchService {

    private final AccountRepository accountRepository;
    private final AccountFilterBuilder filterBuilder;
    private final SpecificationBuilder specificationBuilder;

    public List<Account> getAccountResponse(AccountSearchDto dto, Pageable pageable) {

        List<Filter> filter = filterBuilder.createFilter(dto);

        if (filter.isEmpty()) {
            return new ArrayList<>();
        }

        Specification<Account> specification = (Specification<Account>) specificationBuilder.getSpecificationFromFilters(filter);

        Page<Account> pageResult = accountRepository.findAll(specification, pageable);

        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }
}

