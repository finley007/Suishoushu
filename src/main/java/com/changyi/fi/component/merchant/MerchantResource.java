package com.changyi.fi.component.merchant;

import com.changyi.fi.component.customer.response.ChannelListResponse;
import com.changyi.fi.component.invoice.response.CreateMerchantIDResponse;
import com.changyi.fi.vo.Merchant;
import com.changyi.fi.component.merchant.request.MerchantValidateRequest;
import com.changyi.fi.component.merchant.request.QRCodesRequest;
import com.changyi.fi.component.merchant.response.QRCodeResponse;
import com.changyi.fi.component.merchant.response.QRCodesResponse;
import com.changyi.fi.component.merchant.service.MerchantService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.exception.InvalidRequestException;
import com.changyi.fi.exception.NullRequestException;
import com.changyi.fi.exception.OutOfBoundsException;
import com.changyi.fi.vo.Channel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            LogUtil.info(this.getClass(), "Enter validate endpoint and request: " + request);
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            MerchantValidateRequest req = new Payload(request).as(MerchantValidateRequest.class);
            String openId = Token.touch(token).getOpenId();
            boolean result = merchantService.validate(req, openId);
            merchantService.recordVisit(req, openId, result);
            if (!result) {
                throw new OutOfBoundsException("Current QR code is out of bounds");
            }
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
    @Path("/createid")
    @Produces
    public Response createId(@QueryParam("num") String num) {
        try {
            int idNum;

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
            List<String> idList = merchantService.createMerchantIds(idNum);
            LogUtil.info(this.getClass(), "Complete createId endpoint handle");
            return Response.status(Response.Status.OK).entity(new CreateMerchantIDResponse(idList).build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run createId endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("/qrcodes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response createQRCodes(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter createQRCodes endpoint and request: " + request);
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            QRCodesRequest req = new Payload(request).as(QRCodesRequest.class);
            List<String> idList = merchantService.createMerchants(req.getNumber(), req.getChannelId());
            List<Map> result = new ArrayList<Map>();
            if (idList != null && idList.size() > 0) {
                for (String id  : idList) {
                    String url = merchantService.createQRCode(id);
                    Map map = new HashMap();
                    map.put(QRCodesResponse.KEY_MERCHANT_ID, id);
                    map.put(QRCodesResponse.KEY_URL, url);
                    result.add(map);
                }
            } else {
                LogUtil.info(this.getClass(), "No merchant id created");
            }
            LogUtil.info(this.getClass(), "Complete createQRCodes endpoint handle");
            return Response.status(Response.Status.OK).entity(new QRCodesResponse(result).build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run createQRCodes endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @PUT
    @Path("/channel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response registerChannel(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter registerChannel endpoint");
            LogUtil.debug(this.getClass(), "Request: {} ", request);
            if (StringUtils.isEmpty(request)) {
                throw new NullRequestException("Request is required");
            }
            Channel channel = new Payload(request).as(Channel.class);
            Token curToken = Token.touch(token);
            merchantService.updateChannel(channel);
            LogUtil.info(this.getClass(), "Complete registerChannel endpoint handle");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run registerChannel endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @GET
    @Path("/channel/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response listChannel(@HeaderParam(Token.KEY) String token) {
        try {
            LogUtil.info(this.getClass(), "Enter listChannel endpoint");
            Token curToken = Token.touch(token);
            ChannelListResponse response = new ChannelListResponse(merchantService.listChannel());
            LogUtil.info(this.getClass(), "Complete listChannel endpoint handle");
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run listChannel endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured
    @Timer
    public Response registerMerchant(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter registerMerchant endpoint");
            LogUtil.debug(this.getClass(), "Request: {} ", request);
            Token curToken = Token.touch(token);
            if (StringUtils.isBlank(request)) {
                throw new NullRequestException("Request is required");
            }
            Merchant req = new Payload(request).as(Merchant.class);
            merchantService.merchantRegister(req);
            LogUtil.info(this.getClass(), "Complete registerMerchant endpoint handle");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run registerMerchant endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }
}
