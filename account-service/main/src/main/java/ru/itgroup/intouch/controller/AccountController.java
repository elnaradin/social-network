package ru.itgroup.intouch.controller;

import dto.AccountSearchDtoPageable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.account.Account;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.exceptions.NoUserLoggedInException;
import ru.itgroup.intouch.service.account.AccountService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final ru.itgroup.intouch.service.search.AccountSearchService accountSearchService;

    @GetMapping("/me")
    public AccountDto myAccount(HttpServletRequest request) throws NoUserLoggedInException {
        log.warn("../account/me AUTHORIZATION request header value (must NOT be null): "
                + request.getHeader("AUTHORIZATION"));
        return accountService.getUserInfo();
    }

    @PutMapping("/me")
    public AccountDto changeProfile(@RequestBody AccountDto accountDto)
            throws NoUserLoggedInException {
        accountService.updateAccountData(accountDto);
        return accountDto;
    }

    @DeleteMapping("/me")
    public void deleteAccount() throws NoUserLoggedInException {
        accountService.setAccountDeleted();
    }

    @GetMapping("/search")
    public List<Account> search(AccountSearchDtoPageable dto) {
        return accountSearchService.getAccountResponse(dto.getDto(), dto.getPageable());

    }

}
