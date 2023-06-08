package ru.itgroup.intouch.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
    @Around("@annotation(ru.itgroup.intouch.annotation.Loggable)")
    public Object loadAdvice(@NotNull ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        Object returnValue = null;
        try {
            log.info("Method " +
                    signature.getDeclaringType().getSimpleName() +
                    "::" +
                    signature.getMethod().getName() +
                    " started");
            returnValue = proceedingJoinPoint.proceed();
            log.info("Method " +
                    signature.getDeclaringType().getSimpleName() +
                    "::" +
                    signature.getMethod().getName() +
                    " success " +
                    "finished");
        } catch (Throwable e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return returnValue;
    }
}
