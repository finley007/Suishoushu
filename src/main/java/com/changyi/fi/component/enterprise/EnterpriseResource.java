package com.changyi.fi.component.enterprise;

import com.changyi.fi.component.enterprise.request.GetEnterpriseRequest;
import com.changyi.fi.component.enterprise.response.GetEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.component.enterprise.service.EnterpriseService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.exception.GetEnterpriseFailException;
import com.changyi.fi.exception.NullRequestException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by finley on 6/21/17.
 */
@Component
@Path("/enterprise")
public class EnterpriseResource {

    @Resource
    private EnterpriseService enterpriseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response matchEnterprise(@HeaderParam(Token.KEY) String token, @QueryParam("key") String key) {
        try {
            LogUtil.info(this.getClass(), "Enter matchEnterprise endpoint for key: " + key);
            if (StringUtils.isEmpty(key)) {
                throw new NullRequestException("Key is required");
            }
            MatchEnterpriseResponse response = enterpriseService.matchEnterprise(key);
            LogUtil.info(this.getClass(), "Complete matchEnterprise endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run matchEnterprise endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response getEnterprise(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter getEnterprise endpoint for request: " + request);
            if (StringUtils.isEmpty(request)) {
                throw new NullRequestException("Request is required");
            }
            GetEnterpriseRequest req = new Payload(request).as(GetEnterpriseRequest.class);
            GetEnterpriseResponse response = enterpriseService.getEnterprise(req);
            LogUtil.info(this.getClass(), "Complete getEnterprise endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run getEnterprise endpoint error: ", t);
            String res = ExceptionHandler.handle(new GetEnterpriseFailException("Get enterprise information failed"));
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

}
