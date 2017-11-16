package com.changyi.fi.component.merchant;

import com.changyi.fi.component.merchant.request.DoRecordRequest;
import com.changyi.fi.component.merchant.request.MerchantValidateRequest;
import com.changyi.fi.component.merchant.response.QRCodeResponse;
import com.changyi.fi.component.merchant.service.MerchantService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.response.NormalResponse;
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
@Path("merchant")
public class MerchantResource {

    @Resource
    private MerchantService merchantService;

    @POST
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response validate(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter validate endpoint");
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            MerchantValidateRequest req = new Payload(request).as(MerchantValidateRequest.class);
            merchantService.validate(req, Token.touch(token).getOpenId());
            LogUtil.info(this.getClass(), "Complete validate endpoint handle");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run validate endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("/doRecord")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response doRecord(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter doRecord endpoint");
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            DoRecordRequest req = new Payload(request).as(DoRecordRequest.class);
            merchantService.doRecord(req, Token.touch(token).getOpenId());
            LogUtil.info(this.getClass(), "Complete doRecord endpoint handle");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run doRecord endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("/qrcode/{merchantId}")
    @Produces
    @Timer
    public Response createQRCode(@PathParam("merchantId") String merchantId) {
        try {
            LogUtil.info(this.getClass(), "Enter createQRCode endpoint");
            String url = merchantService.createQRCode(merchantId);
            LogUtil.info(this.getClass(), "Complete createQRCode endpoint handle");
            return Response.status(Response.Status.OK).entity(new QRCodeResponse(merchantId, url).build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run createQRCode endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

}
