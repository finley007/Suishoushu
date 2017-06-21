package com.changyi.fi.component.invoice;

import com.changyi.fi.component.invoice.request.AddInvoiceRequest;
import com.changyi.fi.component.invoice.request.InvoicesRequest;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import com.changyi.fi.component.invoice.service.InvoiceService;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.LogUtil;
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

    @POST
    @Path("/invoices")
    @Consumes("application/json")
    @Produces("application/json")
    public Response listInvoices(@RequestParam String request) {
        LogUtil.info(this.getClass(), "Call listInvoices service");
        LogUtil.debug(this.getClass(), "Request: {} ", request);
        try {
            InvoicesRequest req = new Payload(request).as(InvoicesRequest.class);
            InvoicesResponse response = invoiceService.listInvoice(req);
            String responseContent = new Payload(response).from(InvoicesResponse.class);
            LogUtil.info(this.getClass(), "Complete listInvoices service call");
            LogUtil.info(this.getClass(), "Response: {} ", responseContent);
            return Response.status(Response.Status.OK).entity(responseContent).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Call listInvoices service failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addInvoice(@RequestParam String request) {
        LogUtil.info(this.getClass(), "Call addInvoice service");
        LogUtil.info(this.getClass(), "Request: {} ", request);
        try {
            AddInvoiceRequest req = new Payload(request).as(AddInvoiceRequest.class);
            invoiceService.addInvoice(req);
            LogUtil.info(this.getClass(), "Complete addInvoice service call");
            return Response.status(Response.Status.OK).entity("").build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Call addInvoice service failed: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }


}
