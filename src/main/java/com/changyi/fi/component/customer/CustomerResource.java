package com.changyi.fi.component.customer;

import com.changyi.fi.component.customer.service.CustomerService;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.exception.NullRequestException;
import com.changyi.fi.vo.Customer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Path("/customer")
public class CustomerResource {

    @Resource
    private CustomerService customerService;

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
    public Response updateCustomer(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        try {
            LogUtil.info(this.getClass(), "Enter updateCustomer endpoint");
            LogUtil.debug(this.getClass(), "Request: {} ", request);
            if (StringUtils.isEmpty(request)) {
                throw new NullRequestException("Request is required");
            }
            Customer customer = new Payload(request).as(Customer.class);
            Token curToken = Token.touch(token);
            String openId = curToken.getSession().setCustomer(customer).getOpenId();
            Token.update(curToken);
            customerService.updateCustomer(customer, openId);
            LogUtil.info(this.getClass(), "Complete updateCustomer endpoint handle");
            return Response.status(Response.Status.OK).entity(new NormalResponse().build()).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run updateCustomer endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }
}
