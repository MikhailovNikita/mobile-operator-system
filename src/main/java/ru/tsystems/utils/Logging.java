package ru.tsystems.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ru.tsystems.exceptions.BusinessLogicException;

import java.util.Arrays;


@Aspect
public class Logging {

    private static Logger logger = LogManager.getLogger(Logging.class);

    @Around("execution(* (@ru.tsystems.service.Service *).*(..))")
    public Object monitorServiceBeans(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();

        logger.debug("Method {} invoked in {} with arguments: {}", methodName, pjp.getTarget().getClass().getSimpleName(), Arrays.toString(pjp.getArgs()));
        String log = "{}({}) has thrown the {}: {}";
        try {
            Object res = pjp.proceed();
            logger.debug("{}({}) has successfully executed in {}. Returned value: {}", methodName, Arrays.toString(pjp.getArgs()), pjp.getTarget().getClass().getSimpleName(), res);
            return res;
        } catch (BusinessLogicException e) {
            logger.info(log, methodName, Arrays.toString(pjp.getArgs()), e.getClass().getSimpleName(), e.getMessage());
            throw e;
        } catch (Throwable e) {
            logger.error(log, methodName, Arrays.toString(pjp.getArgs()), e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }
}

