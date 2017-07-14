package com.changyi.fi.component.endpoint;

import com.changyi.fi.component.endpoint.response.InboundDetailResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.exception.ExceptionHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by finley on 7/14/17.
 */
@Component
@Path("/inbound")
public class InboundResource {

    @GET
    @Path("/detail")
    @Produces("application/json")
    public Response inboundDetail(@Context HttpServletRequest req) {
        try {
            LogUtil.info(this.getClass(), "Enter inboundDetail endpoint");
            InboundDetailResponse response = new InboundDetailResponse(req);
            LogUtil.info(this.getClass(), "Complete inboundDetail endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run inboundDetail endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

}
