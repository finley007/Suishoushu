package com.changyi.fi.component.merchant;

import com.changyi.fi.component.merchant.response.MerchantValidateResponse;
import com.changyi.fi.component.merchant.service.MerchantService;
import com.changyi.fi.core.annotation.Secured;
import com.changyi.fi.core.response.Response;
import com.changyi.fi.core.token.Token;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.ws.rs.*;

@Component
@Path("merchant")
public class MerchantResource {

    @Resource
    private MerchantService merchantService;

    @POST
    @Path("/validate")
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
    public Response validate(@HeaderParam(Token.KEY) String token, @RequestParam String request) {
        return new MerchantValidateResponse();
    }

}
