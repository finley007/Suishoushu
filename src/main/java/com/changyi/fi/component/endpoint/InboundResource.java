package com.changyi.fi.component.endpoint;

import com.changyi.fi.component.endpoint.response.InboundDetailResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.tool.DictionaryManager;
import com.changyi.fi.exception.InvalidRequestException;
import com.changyi.fi.exception.NullRequestException;
import com.changyi.fi.external.enterprise.manager.EnternalEnterpriseAPIManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
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

    @POST
    @Path("/refresh")
    public Response refresh() {
        try {
            LogUtil.info(this.getClass(), "Enter refresh endpoint");
            DictionaryManager.refresh();
            EnternalEnterpriseAPIManager.refresh();
            LogUtil.info(this.getClass(), "Complete refresh endpoint handle");
            return Response.status(Response.Status.OK).entity("success").build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run refresh endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("/enterprise_api/{id}/{weight}")
    public Response updateEnterpriseExternalAPIWeight(@PathParam("id") String id, @PathParam("weight") String weight) {
        try {
            LogUtil.info(this.getClass(), "Enter enterprise_api weight update endpoint");
            if (StringUtils.isBlank(id)) {
                throw new NullRequestException("Id is required");
            }
            if (StringUtils.isBlank(weight)) {
                throw new NullRequestException("Weight is required");
            }
            int intWeight = 0;
            try {
                intWeight = Integer.valueOf(weight);
            } catch (Exception e) {
                throw new InvalidRequestException("Weight should be integer: " + weight);
            }
            EnternalEnterpriseAPIManager.updateAPIWeight(id, intWeight);
            LogUtil.info(this.getClass(), "Complete enterprise_api weight update endpoint handle");
            return Response.status(Response.Status.OK).entity("success").build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run enterprise_api weight update error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }

    }

}
