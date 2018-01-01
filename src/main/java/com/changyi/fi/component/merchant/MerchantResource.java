package com.changyi.fi.component.merchant;

import com.changyi.fi.component.invoice.response.CreateMerchantIDResponse;
import com.changyi.fi.component.merchant.request.MerchantValidateRequest;
import com.changyi.fi.component.merchant.response.QRCodeResponse;
import com.changyi.fi.component.merchant.service.MerchantService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.core.seq.SeqCreatorBuilder;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.exception.InvalidRequestException;
import com.changyi.fi.exception.NullRequestException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("merchant")
public class MerchantResource {

    private static final int MERCHANR_ID_LENGTH = 11;

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
            LogUtil.info(this.getClass(), "Enter validate endpoint and request: " + request);
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            MerchantValidateRequest req = new Payload(request).as(MerchantValidateRequest.class);
            String openId = Token.touch(token).getOpenId();
            merchantService.doRecord(req, openId);
            merchantService.validate(req, openId);
            LogUtil.info(this.getClass(), "Complete validate endpoint handle");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run validate endpoint error: ", t);
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

    @GET
    @Path("createid")
    @Produces
    public Response createId(@QueryParam("num") String num) {
        try {
            int idNum;
            List<String> idList = new ArrayList<String>();
            LogUtil.info(this.getClass(), "Enter createId endpoint");
            if (StringUtils.isBlank(num)) {
                throw new NullRequestException("Parameter num is required");
            }
            try {
                idNum = Integer.valueOf(num);
            } catch (Exception e) {
                LogUtil.error(this.getClass(), "Invalid parameter num: " + num, e);
                throw new InvalidRequestException("Invalid parameter num: " + num);
            }
            for (int i = 0; i < idNum; i++) {
                idList.add(SeqCreatorBuilder.build(SeqCreatorBuilder.SEQ_CREATOR_RANDOWM).createSeq(MERCHANR_ID_LENGTH));
            }
            LogUtil.info(this.getClass(), "Complete createId endpoint handle");
            return Response.status(Response.Status.OK).entity(new CreateMerchantIDResponse(idList).build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run createId endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }
}
