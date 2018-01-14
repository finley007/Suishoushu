package com.changyi.fi.component.enterprise;

import com.changyi.fi.component.enterprise.request.GetEnterpriseRequest;
import com.changyi.fi.component.enterprise.response.GetEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.SyncCheckResponse;
import com.changyi.fi.component.enterprise.service.EnterpriseService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.exception.GetEnterpriseFailException;
import com.changyi.fi.exception.NullRequestException;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.manager.EnternalEnterpriseAPIManager;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by finley on 6/21/17.
 */
@Component
@Path("/enterprise")
public class EnterpriseResource {

    private static final String PROP_CHANGYI_ENTERPRISE_INFO = "changyi.enterprise.info";
    private static final String PROP_CHANGYI_ENTERPRISE = "changyi.enterprise.";

    @Resource
    private EnterpriseService enterpriseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response matchEnterprise(@HeaderParam(Token.KEY) String token, @QueryParam("key") String key) {
        try {
            LogUtil.info(this.getClass(), "Enter matchEnterprise endpoint for key: " + key);
            if (StringUtils.isEmpty(key)) {
                throw new NullRequestException("Parameter: key is required");
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

    @GET
    @Path("/heartbeat")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response heartbeat(@HeaderParam(Token.KEY) String token, @QueryParam("key") String key, @QueryParam("api") String api) {
        try {
            LogUtil.info(this.getClass(), "Enter heartbeat endpoint for key: " + key + " and API: " + api);
            if (StringUtils.isEmpty(key)) {
                throw new NullRequestException("Parameter key is required");
            }
            if (StringUtils.isEmpty(api)) {
                throw new NullRequestException("Parameter api is required");
            }
            MatchEnterpriseResponse response = enterpriseService.heartbeat(key, api);
            LogUtil.info(this.getClass(), "Complete heartbeat endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run heartbeat endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @GET
    @Path("/synccheck")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response synccheck(@HeaderParam(Token.KEY) String token) {
        try {
            LogUtil.info(this.getClass(), "Enter synccheck endpoint");
            String[] apis = new String[]{FIConstants.API_QIXINBAO, FIConstants.API_QICHACHA, FIConstants.API_TIANYANCHA};
            Map ent = new HashMap<String, Object>();
            for (int i = 0; i < apis.length; i++) {
                ExternalEnterpriseAPIService api = EnternalEnterpriseAPIManager.getAPIImpl(apis[i]);
                try {
                    EnterprisePO entPO = api.getEnterpriseByCode(Properties.get(PROP_CHANGYI_ENTERPRISE + apis[i]));
                    ent.put(apis[i], entPO);
                } catch (Exception e) {
                    LogUtil.error(this.getClass(), "Call API: " + apis[i] + " for sync check error: ", e);
                }
            }
            return Response.status(Response.Status.OK).entity(new SyncCheckResponse(ent).build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run synccheck endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

}
