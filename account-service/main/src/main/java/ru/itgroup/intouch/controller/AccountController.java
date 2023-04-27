package ru.itgroup.intouch.controller;

import dto.AccountSearchDto;
import lombok.RequiredArgsConstructor;
import model.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.service.registration.RegistrationService;
import ru.itgroup.intouch.service.search.AccountSearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final RegistrationService registrationService;
    private final AccountSearchService accountSearchService;
    @GetMapping("/api/v1/account/me")
    public AccountDto myAccount() {
        return registrationService.getUserInfo();
    }

    @GetMapping("/api/v1/account/search")
    public ResponseEntity<List<Account>> search(AccountSearchDto accountSearchDto, Pageable pageable) {
        List<Account> accountList = accountSearchService.getAccountResponse(accountSearchDto, pageable);
        if (accountList.isEmpty()) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<List<Account>>(accountList, HttpStatus.OK);
    }
}
