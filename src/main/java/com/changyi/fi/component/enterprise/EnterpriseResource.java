package com.changyi.fi.component.enterprise;

import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.component.enterprise.service.EnterpriseService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.ExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
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
    @Path("/{key}")
    @Produces("application/json")
    public Response matchEnterprise(@HeaderParam("token") String token, @QueryParam("key") String key) {
        LogUtil.info(this.getClass(), "Call matchEnterprise service for key: " + key);
        try {
            MatchEnterpriseResponse response = enterpriseService.matchEnterprise(key);
            String responseContent = new Payload(response).from(MatchEnterpriseResponse.class);
            LogUtil.info(this.getClass(), "Complete matchEnterprise service call");
            LogUtil.info(this.getClass(), "Response: {} ", responseContent);
            return Response.status(Response.Status.OK).entity(responseContent).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Call matchEnterprise service failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }


}
