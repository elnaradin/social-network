package ru.itgroup.intouch.aggregator.controller;

import dto.AccountSearchDtoPageable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.dto.AccountDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Slf4j
public class AccountController {
    private final AccountServiceClient client;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto myAccount(HttpServletRequest request) {
        log.info("../account/me (get method) AUTHORIZATION request header value: "
                + request.getHeader("AUTHORIZATION"));
        return client.myAccount();
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto changeProfile(@RequestBody AccountDto accountDto) {
        return client.changeProfile(accountDto);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount() {
        client.deleteAccount();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> search(AccountSearchDtoPageable dto) { return client.search(dto); }



}
