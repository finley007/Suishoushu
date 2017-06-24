package com.changyi.fi.auth;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.exception.NullRequestException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by finley on 1/18/17.
 */
@Component
@Path("/auth")
public class Auth {

    @Resource
    private AuthService authService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@QueryParam("code") String code) {
        LogUtil.info(this.getClass(), "Do authentication for code: ", code);
        try {
            if (StringUtils.isEmpty(code)) {
                throw new NullRequestException("User code is empty");
            }
            AuthResponse response = this.authService.authenticate(code);
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Authentication failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

}
