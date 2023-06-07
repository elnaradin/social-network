package ru.itgroup.intouch.controller;

import dto.AccountSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.account.Account;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.EmailDto;
import ru.itgroup.intouch.dto.UserDto;
import ru.itgroup.intouch.service.account.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final ru.itgroup.intouch.service.search.AccountSearchService accountSearchService;

    @PostMapping("/accounts")
    public List<AccountDto> accounts(@RequestBody List<Long> userIds) {
        return accountService.getListOfUsers(userIds);
    }

    @PostMapping("/currentUser")
    UserDto currentUser(@RequestBody EmailDto emailDto) {
        return accountService.getUserInfo(emailDto.getEmail());
    }

    @PostMapping("/me")
    public AccountDto myAccount(@RequestBody EmailDto emailDto) {
        return accountService.getAccountInfo(emailDto.getEmail());
    }

    @PutMapping("/me")
    public AccountDto changeProfileInfo(@RequestBody AccountDto accountDto) {
        accountService.updateAccountData(accountDto);
        return accountDto;
    }

    @DeleteMapping("/me")
    public void deleteAccount(@RequestBody EmailDto emailDto) {
        accountService.setAccountDeleted(emailDto.getEmail());
    }

    @GetMapping("/search")
    public List<Account> search(AccountSearchDtoPageable dto) {
        return accountSearchService.getAccountResponse(dto.getDto(), dto.getPageable());

    }

}
