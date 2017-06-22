package com.changyi.fi.auth;

import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.LogUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@RequestParam String request) {
        LogUtil.info(this.getClass(), "Do authentication for user: {} ", request);
        try {
            AuthResponse response = this.authService.authenticate(new Payload(request).as(AuthRequest.class));
            String responseContent = new Payload(response).from(AuthResponse.class);
            return Response.status(Response.Status.OK).entity(responseContent).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Authentication failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

}
