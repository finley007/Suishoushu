package com.changyi.fi.component.invoice;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import com.changyi.fi.component.invoice.service.InvoiceService;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.response.NormalResponse;
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
    public Response listInvoices(@HeaderParam("token") String token) {
        LogUtil.info(this.getClass(), "Call listInvoices service");
        try {
            InvoicesResponse response = invoiceService.listInvoice(token);
            LogUtil.info(this.getClass(), "Complete listInvoices service call");
            LogUtil.info(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Call listInvoices service failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateInvoice(@HeaderParam("token") String token, @RequestParam String request) {
        LogUtil.info(this.getClass(), "Call addInvoice service");
        LogUtil.info(this.getClass(), "Request: {} ", request);
        try {
            PutInvoiceRequest req = new Payload(request).as(PutInvoiceRequest.class);
            invoiceService.updateInvoice(req);
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
    public Response deleteInvoice(@HeaderParam("token") String token, @QueryParam("id") String id) {
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
    public Response getInvoice(@PathParam("id") String id) {
        LogUtil.info(this.getClass(), "Call getInvoice service for id: " + id);
        try {
            GetInvoiceResponse response = invoiceService.getInvoice(id);
            LogUtil.info(this.getClass(), "Complete getInvoice service call");
            LogUtil.info(this.getClass(), "Response: {} ", response.build());
            return Response.status(Response.Status.OK).entity(response.build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Call getInvoice service failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }


}
