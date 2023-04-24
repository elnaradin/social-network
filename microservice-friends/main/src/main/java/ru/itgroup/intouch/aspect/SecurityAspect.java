package ru.itgroup.intouch.aspect;

import model.Account;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.repository.AccountRepository;

@Aspect
@Component
public class SecurityAspect {

    private final AccountRepository accountRepository;

    @Autowired
    public SecurityAspect(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Pointcut("@annotation(ru.itgroup.intouch.aspect.CheckAndGetAuthUser) && (args(..,account) || args(account))")
    public void getSecurityUser(Account account) {
    }

    @Around(value = "getSecurityUser(account)", argNames = "proceedingJoinPoint,account")
    public Object execAdviceForGetSecurityUser(ProceedingJoinPoint proceedingJoinPoint, Account account)
            throws Throwable {

        //TODO добавить проверку и получение пользователя из account сервиса
        //TODO если пользователь не авторизован выбросить исключение
        //TODO объект currentAccount должен передавать текущего пользователя
        Account currentAccount = accountRepository.getReferenceById(1L);


        proceedingJoinPoint.getArgs()[proceedingJoinPoint.getArgs().length - 1] = currentAccount;
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

}
