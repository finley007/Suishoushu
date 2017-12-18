package com.changyi.fi.component.statistics;

import com.changyi.fi.component.statistics.request.CustomerStatRequest;
import com.changyi.fi.component.statistics.request.EnterpriseStatRequest;
import com.changyi.fi.component.statistics.request.SystemStatRequest;
import com.changyi.fi.component.statistics.response.CustomerStatResponse;
import com.changyi.fi.component.statistics.response.EnterpriseStatResponse;
import com.changyi.fi.component.statistics.response.SystemStatResponse;
import com.changyi.fi.component.statistics.service.StatisticsService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.exception.NullRequestException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("statistics")
public class StatisticsResource {

    @Resource
    StatisticsService statisticsService;

    @POST
    @Path("/customer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response getCustomerStatInfo(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter customer statistics endpoint, request: " + request);
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            CustomerStatRequest req = new Payload(request).as(CustomerStatRequest.class);
            CustomerStatResponse res = statisticsService.getCustomerStatInfo(req);
            LogUtil.info(this.getClass(), "Complete customer statistics endpoint handle");
            return Response.status(Response.Status.OK).entity(res.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run customer statistics endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("/enterprise")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response getEnterpriseStatInfo(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter enterprise statistics endpoint, request: " + request);
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            EnterpriseStatRequest req = new Payload(request).as(EnterpriseStatRequest.class);
            EnterpriseStatResponse res = statisticsService.getEnterpriseStatInfo(req);
            LogUtil.info(this.getClass(), "Complete enterprise statistics endpoint handle");
            return Response.status(Response.Status.OK).entity(res.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run enterprise statistics endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("/system")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response getSystemStatInfo(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter system statistics endpoint, request: " + request);
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            SystemStatRequest req = new Payload(request).as(SystemStatRequest.class);
            SystemStatResponse res = statisticsService.getSystemStatInfo(req);
            LogUtil.info(this.getClass(), "Complete system statistics endpoint handle");
            return Response.status(Response.Status.OK).entity(res.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run system statistics endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

}
