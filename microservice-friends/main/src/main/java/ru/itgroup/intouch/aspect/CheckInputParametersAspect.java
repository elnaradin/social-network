package ru.itgroup.intouch.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import ru.itgroup.intouch.dto.FriendSearchDto;
import ru.itgroup.intouch.dto.Pageable;
import ru.itgroup.intouch.exceptions.WrongParameterException;

@Aspect
@Component
@Slf4j
public class CheckInputParametersAspect {

    private static final String LOG_MSG_PARAM_IS_NULL = "Parameter value is null";
    private static final String LOG_MSG_BAD_LONG_VALUE = "Parameter of type Long is null or less than 0";
    private static final String LOG_MSG_BAD_PAGEABLE = "Invalid value of one or more parameters of the Pageable object";
    private static final String EX_MSG_WRONG_PARAM = "Invalid parameter value";

    @Pointcut(value = "@annotation(ru.itgroup.intouch.aspect.ValidateParams)")
    public void checkParamMethod() {
    }

    @Before("checkParamMethod()")
    public void execAdviceCheckParamMethod(JoinPoint joinPoint) throws WrongParameterException {
        for (Object o : joinPoint.getArgs()) {
            if (o == null) {
                log.warn(LOG_MSG_PARAM_IS_NULL);
                throw new WrongParameterException(EX_MSG_WRONG_PARAM);
            }
            if (o instanceof Long && ((Long) o) <= 0) {
                log.warn(LOG_MSG_BAD_LONG_VALUE);
                throw new WrongParameterException(EX_MSG_WRONG_PARAM);
            }
            if (o instanceof Pageable){
                checkPageableDto((Pageable) o);
            }
            if (o instanceof FriendSearchDto){
                checkFriendSearchDto((FriendSearchDto) o);
            }
        }
    }

    private void checkPageableDto(Pageable pageable) throws WrongParameterException {
        if(pageable.getPage() == null || pageable.getPage() < 0 ||
        pageable.getSize() == null || pageable.getSize() < 1){
            log.warn(LOG_MSG_BAD_PAGEABLE);
            throw new WrongParameterException(EX_MSG_WRONG_PARAM);
        }
    }

    private void checkFriendSearchDto(FriendSearchDto friendSearchDto){
        //TODO: реализовать проверку
    }
}
