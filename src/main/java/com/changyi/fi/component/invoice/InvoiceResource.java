package com.changyi.fi.component.invoice;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import com.changyi.fi.component.invoice.service.InvoiceService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.exception.NullRequestException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by rongb on 2016/12/26.
 */

@Component
@Path("/invoice")
public class InvoiceResource {

    @Resource
    private InvoiceService invoiceService;

    @GET
    @Path("/invoices")
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
    public Response listInvoices(@HeaderParam(Token.KEY) String token) {
        try {
            LogUtil.info(this.getClass(), "Enter invoices endpoint");
            InvoicesResponse response = invoiceService.listInvoice(Token.touch(token).getOpenId());
            LogUtil.info(this.getClass(), "Complete invoices endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run invoices endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
    public Response updateInvoice(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter updateInvoice endpoint");
            LogUtil.debug(this.getClass(), "Request: {} ", request);
            if (StringUtils.isEmpty(request)) {
                throw new NullRequestException("Request is required");
            }
            PutInvoiceRequest req = new Payload(request).as(PutInvoiceRequest.class);
            invoiceService.updateInvoice(req, Token.touch(token).getOpenId());
            LogUtil.info(this.getClass(), "Complete addInvoice service call");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run addInvoice endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    @Secured
    public Response deleteInvoice(@HeaderParam(Token.KEY) String token, @PathParam("id") String id) {
        try {
            LogUtil.info(this.getClass(), "Enter deleteInvoice endpoint for id: " + id);
            if (StringUtils.isEmpty(id)) {
                throw new NullRequestException("Id is required");
            }
            invoiceService.deleteInvoice(Token.touch(token).getOpenId(), id);
            LogUtil.info(this.getClass(), "Complete deleteInvoice endpoint handle");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run deleteInvoice endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    @Secured
    public Response getInvoice(@HeaderParam(Token.KEY) String token, @PathParam("id") String id) {
        try {
            LogUtil.info(this.getClass(), "Enter getInvoice endpoint for id: " + id);
            if (StringUtils.isEmpty(id)) {
                throw new NullRequestException("Id is required");
            }
            GetInvoiceResponse response = invoiceService.getInvoice(Token.touch(token).getOpenId(), id);
            LogUtil.info(this.getClass(), "Complete getInvoice endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run getInvoice endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }


}
