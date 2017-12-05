package com.changyi.fi.core.aop;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.exception.ExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Date;

@Component
@Aspect
@Order(1)
public class TimerAspect {

    @Around(value="execution(* com.changyi.fi..*Service.*(..)) && @annotation(timer)", argNames="timer")
    public Object caculateTimeCostForService(ProceedingJoinPoint aPoint, Timer timer) throws Throwable {
        Date start = new Date();
        Object retValue = aPoint.proceed();
        LogUtil.info(aPoint.getTarget().getClass(), "Service {} cost time: " + (new Date().getTime() - start.getTime())
                , new Object[]{aPoint.getSignature()});
        return retValue;
    }

    @Around(value="execution(* com.changyi.fi.component..*Resource.*(..)) && @annotation(timer)", argNames="timer")
    public Object caculateTimeCostForResource(ProceedingJoinPoint aPoint, Timer timer) throws Throwable {
        //入口处初始化日志序列号
        LogUtil.intSquence();
        Date start = new Date();
        Object retValue = aPoint.proceed();
        LogUtil.info(aPoint.getTarget().getClass(), "Endpoint {} cost time: " + (new Date().getTime() - start.getTime())
                , new Object[]{aPoint.getSignature()});
        return retValue;
    }

}
