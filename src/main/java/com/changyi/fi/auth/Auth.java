package com.changyi.fi.auth;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.exception.NullRequestException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by finley on 1/18/17.
 */
@Component
@Path("/auth")
public class Auth {

    @Resource
    private AuthService authService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@RequestParam String request) {
        LogUtil.info(this.getClass(), "Do authentication for request: ", request);
        try {
            if (StringUtils.isEmpty(request)) {
                throw new NullRequestException("Request is required");
            }
            HashMap<String, String> req = new Payload(request).as(HashMap.class);
            String code = req.get("code");
            if (StringUtils.isEmpty(code)) {
                throw new NullRequestException("User code is required");
            }
            AuthResponse response = this.authService.authenticate(code);
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Authentication failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("/internal")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response internalAuth(@RequestParam String request) {
        LogUtil.info(this.getClass(), "Do internal authentication for request: ", request);
        try {
            if (StringUtils.isEmpty(request)) {
                throw new NullRequestException("Request is required");
            }
            HashMap<String, String> req = new Payload(request).as(HashMap.class);
            String code = req.get("code");
            if (StringUtils.isEmpty(code)) {
                throw new NullRequestException("User code is required");
            }
            AuthResponse response = this.authService.internalAuthenticate(code);
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Internal authentication failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }


}
