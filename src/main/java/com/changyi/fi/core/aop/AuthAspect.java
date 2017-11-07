package com.changyi.fi.core.aop;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.exception.UnauthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

/**
 * Created by finley on 2/2/17.
 */
@Component
@Aspect
@Order(2)
public class AuthAspect {

    @Around(value="execution(* com.changyi.fi.component..*.*(..)) && @annotation(secured)", argNames="secured")
    public Response authToken(ProceedingJoinPoint aPoint, Secured secured) {
        LogUtil.debug(this.getClass(), "Auth aspect handle for component: {} and service: {}",
                new Object[]{aPoint.getTarget().getClass(), aPoint.getSignature()});
        String tokenKey = (String)aPoint.getArgs()[0];
        LogUtil.debug(this.getClass(), "Token key: " + tokenKey);
        if (tokenKey != null) {
            Token token = Token.touch(tokenKey);
            if (token != null) {
                try {
                    return (Response) aPoint.proceed();
                } catch (Throwable throwable) {
                    LogUtil.error(this.getClass(), "Execute endpoint error: ", throwable);
                }
            }
        }
        String res = ExceptionHandler.handle(new UnauthorizedException("Unauthorized error"));
        return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
    }

}
