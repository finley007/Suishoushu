package com.changyi.fi.component.enterprise;

import com.changyi.fi.component.enterprise.response.GetEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.component.enterprise.service.EnterpriseService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
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
    @Produces("application/json")
    @Secured
    public Response matchEnterprise(@HeaderParam("token") String token, @QueryParam("key") String key) {
        LogUtil.info(this.getClass(), "Enter matchEnterprise endpoint for key: " + key);
        try {
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

    @GET
    @Path("/{creditCode}")
    @Produces("application/json")
    @Secured
    public Response getEnterprise(@HeaderParam("token") String token, @PathParam("creditCode") String creditCode) {
        LogUtil.info(this.getClass(), "Enter getEnterprise endpint for creditCode: " + creditCode);
        try {
            GetEnterpriseResponse response = enterpriseService.getEnterprise(creditCode);
            LogUtil.info(this.getClass(), "Complete getEnterprise endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run getEnterprise endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }



}
