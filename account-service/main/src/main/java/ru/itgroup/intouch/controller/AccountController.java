package ru.itgroup.intouch.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.exceptions.NoUserLoggedInException;
import ru.itgroup.intouch.service.account.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/me")
    public AccountDto myAccount(HttpServletRequest request) throws NoUserLoggedInException {
        log.warn("../account/me AUTHORIZATION request header value: "
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

}

