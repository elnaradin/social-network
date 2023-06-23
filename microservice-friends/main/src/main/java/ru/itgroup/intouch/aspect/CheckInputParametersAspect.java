package ru.itgroup.intouch.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.FriendSearchDto;
import ru.itgroup.intouch.dto.FriendSearchPageableDto;
import ru.itgroup.intouch.exceptions.WrongParameterException;

@Aspect
@Component
@Slf4j
public class CheckInputParametersAspect {

    private static final String LOG_MSG_PARAM_IS_NULL = "Parameter value is null";
    private static final String LOG_MSG_BAD_LONG_VALUE = "Parameter of type Long is null or less than 0";
    private static final String LOG_MSG_BAD_PAGEABLE = "Invalid value of one or more parameters of the " +
            "FriendSearchPageableDto object";

    private static final String LOG_MSG_BAD_FRIEND_SEARCH = "Invalid value of one or more parameters of the " +
            "FriendSearchDto object";
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
            if (o instanceof FriendSearchPageableDto) {
                checkPageableDto((FriendSearchPageableDto) o);
            }
            if (o instanceof FriendSearchDto) {
                checkFriendSearchDto((FriendSearchDto) o);
            }
        }
    }

    private void checkPageableDto(FriendSearchPageableDto friendSearchPageableDto) throws WrongParameterException {
        if (friendSearchPageableDto.getPage() == null || friendSearchPageableDto.getPage() < 0 ||
                friendSearchPageableDto.getSize() == null || friendSearchPageableDto.getSize() < 1) {
            log.warn(LOG_MSG_BAD_PAGEABLE);
            throw new WrongParameterException(EX_MSG_WRONG_PARAM);
        }
    }

    private void checkFriendSearchDto(FriendSearchDto friendSearchDto) {
        if (friendSearchDto == null) {
            log.warn(LOG_MSG_BAD_FRIEND_SEARCH);
            throw new WrongParameterException(EX_MSG_WRONG_PARAM);
        }
    }
}
