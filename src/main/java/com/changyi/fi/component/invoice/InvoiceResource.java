package com.changyi.fi.component.invoice;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import com.changyi.fi.component.invoice.service.InvoiceService;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.exception.AuthenticationFailedException;
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
    @Produces("application/json")
    @Secured
    public Response listInvoices(@HeaderParam(Token.KEY) String token) {
        LogUtil.info(this.getClass(), "Enter invoices endpoint");
        try {
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
        LogUtil.info(this.getClass(), "Enter updateInvoice endpoint");
        LogUtil.debug(this.getClass(), "Request: {} ", request);
        try {
            PutInvoiceRequest req = new Payload(request).as(PutInvoiceRequest.class);
            invoiceService.updateInvoice(Token.touch(token).getOpenId(), req);
            LogUtil.info(this.getClass(), "Complete addInvoice service call");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Call addInvoice service failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @DELETE
    @Produces("application/json")
    @Secured
    public Response deleteInvoice(@HeaderParam(Token.KEY) String token, @QueryParam("id") String id) {
        LogUtil.info(this.getClass(), "Call deleteInvoice service for id: " + id);
        try {
            invoiceService.deleteInvoice(token, id);
            LogUtil.info(this.getClass(), "Complete deleteInvoice service call");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Call deleteInvoice service failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    @Secured
    public Response getInvoice(@HeaderParam(Token.KEY) String token, @PathParam("id") String id) {
        LogUtil.info(this.getClass(), "Enter invoice endpoint for id: " + id);
        try {
            GetInvoiceResponse response = invoiceService.getInvoice(id);
            //验证openId
            String cOpenId = Token.touch(token).getOpenId();
            if (!cOpenId.equals(response.getInvoice().getOpen_id())) {
                LogUtil.info(this.getClass(), "Current user: " + cOpenId + " and invoice owner: " + response.getInvoice().getOpen_id());
                throw new AuthenticationFailedException("Current user has no permission to current invoice");
            }
            LogUtil.info(this.getClass(), "Complete invoice endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Call getInvoice service failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }


}
