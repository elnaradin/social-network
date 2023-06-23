package ru.itgroup.intouch.aggregator.controller;

import dto.AccountSearchDtoPageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.dto.AccountDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Slf4j
public class AccountController {
    private final AccountServiceClient client;

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(client.getAccountById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<AccountDto> myAccount(Authentication authentication) {
        return ResponseEntity.ok().body(client.myAccount(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<AccountDto> changeProfile(@Valid @RequestBody AccountDto accountDto,
                                                    Authentication authentication) {
        accountDto.setEmail(authentication.getName());
        return ResponseEntity.ok(client.changeProfile(accountDto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        client.deleteAccount(authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public Page<AccountDto> search(AccountSearchDtoPageable dto) {
        return client.search(dto);
    }

}
