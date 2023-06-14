package ru.itgroup.intouch.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.account.Account;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.repository.AccountRepository;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityAspect {
    private final AccountRepository accountRepository;

    @Pointcut("@annotation(ru.itgroup.intouch.aspect.CheckAndGetAuthUser) && (args(..,account) || args(account))")
    public void getSecurityUser(Account account) {
    }

    @Around(value = "getSecurityUser(account)", argNames = "proceedingJoinPoint,account")
    public Object execAdviceForGetSecurityUser(ProceedingJoinPoint proceedingJoinPoint, Account account)
            throws Throwable {
        if (account == null) {
            throw new AuthenticationException();
        }
        Optional<Account> currentAccount = accountRepository.getAccountByEmail(account.getEmail());
        if (!currentAccount.isPresent()) {
            throw new AuthenticationException();
        }
        proceedingJoinPoint.getArgs()[proceedingJoinPoint.getArgs().length - 1] = currentAccount.get();
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}
