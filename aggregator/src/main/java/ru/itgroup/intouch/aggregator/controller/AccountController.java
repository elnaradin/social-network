package ru.itgroup.intouch.aggregator.controller;

import dto.AccountSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.account.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.EmailDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Slf4j
public class AccountController {
    private final AccountServiceClient client;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto myAccount() {
        return client.myAccount(new EmailDto(
                SecurityContextHolder
                .getContext()
                .getAuthentication().getName())
        );
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto changeProfile(@RequestBody AccountDto accountDto) {
        return client.changeProfile(accountDto);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@RequestBody EmailDto emailDto) {
        client.deleteAccount(emailDto);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> search(AccountSearchDtoPageable dto) { return client.search(dto); }

}
