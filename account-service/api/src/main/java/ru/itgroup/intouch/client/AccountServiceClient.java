package ru.itgroup.intouch.client;

import dto.AccountSearchDtoPageable;
import model.account.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itgroup.intouch.dto.AccountDto;

import java.util.List;


@FeignClient(name = "account-service", url = "${SN_ACCOUNT_HOST}" + ":" + "${SN_ACCOUNT_PORT}", path = "/api/v1/account")
public interface AccountServiceClient {
    @GetMapping("/me")
    AccountDto myAccount();

    @PutMapping("/me")
    AccountDto changeProfile(@RequestBody AccountDto accountDto);

    @DeleteMapping("/me")
    void deleteAccount();
    @GetMapping("/search")
    List<Account> search(AccountSearchDtoPageable dto);

}
