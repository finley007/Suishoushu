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
    public Object caculateTimeCostForService(ProceedingJoinPoint aPoint, Timer timer) {
        Date start = new Date();
        try {
            Object retValue = aPoint.proceed();
            LogUtil.info(aPoint.getTarget().getClass(), "Service {} cost time: " + (new Date().getTime() - start.getTime())
                    , new Object[]{aPoint.getSignature()});
            return retValue;
        } catch (Throwable throwable) {
            LogUtil.error(this.getClass(), "Execute service error: ", throwable);
            String res = ExceptionHandler.handle(throwable);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(res).build();
        }
    }

    @Around(value="execution(* com.changyi.fi.component..*Resource.*(..)) && @annotation(timer)", argNames="timer")
    public Object caculateTimeCostForResource(ProceedingJoinPoint aPoint, Timer timer) {
        //入口处初始化日志序列号
        LogUtil.intSquence();
        Date start = new Date();
        try {
            Object retValue = aPoint.proceed();
            LogUtil.info(aPoint.getTarget().getClass(), "Endpoint {} cost time: " + (new Date().getTime() - start.getTime())
                    , new Object[]{aPoint.getSignature()});
            return retValue;
        } catch (Throwable throwable) {
            LogUtil.error(this.getClass(), "Execute endpoint error: ", throwable);
            String res = ExceptionHandler.handle(throwable);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(res).build();
        }
    }

}
