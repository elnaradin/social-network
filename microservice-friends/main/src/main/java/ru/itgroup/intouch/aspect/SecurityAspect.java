package ru.itgroup.intouch.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.account.Account;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.mapper.FriendMapper;

import javax.security.sasl.AuthenticationException;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityAspect {
    private final AccountServiceClient accountServiceClient;
    private final FriendMapper friendMapper;

    @Pointcut("@annotation(ru.itgroup.intouch.aspect.CheckAndGetAuthUser) && (args(..,account) || args(account))")
    public void getSecurityUser(Account account) {
    }

    @Around(value = "getSecurityUser(account)", argNames = "proceedingJoinPoint,account")
    public Object execAdviceForGetSecurityUser(ProceedingJoinPoint proceedingJoinPoint, Account account)
            throws Throwable {
        AccountDto accountDto = accountServiceClient.myAccount();
        if (accountDto == null) {
            throw new AuthenticationException();
        }
        Account currentAccount = friendMapper.accountDtoToAccount(accountDto);
        proceedingJoinPoint.getArgs()[proceedingJoinPoint.getArgs().length - 1] = currentAccount;
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}
