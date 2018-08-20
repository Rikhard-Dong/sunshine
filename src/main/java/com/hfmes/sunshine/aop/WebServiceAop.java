package com.hfmes.sunshine.aop;

import com.hfmes.sunshine.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/17 12:01
 */
@Component
@Slf4j
@Aspect
public class WebServiceAop {

    @Pointcut("execution(* com.hfmes.sunshine.ws.*.*(..))")
    public void wsMethod() {
    }

    @Around("wsMethod()")
    public Object wsMethodAround(ProceedingJoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("执行方法method --> {}, 参数args -> {}", method, args);
        try {
            Object result = joinPoint.proceed(args);
            log.info("方法执行完成 -> {}", method);
            return result;
        } catch (Throwable throwable) {
            log.error("执行发生异常 --> {}", throwable.getMessage());
            return null;
        }
    }
}
