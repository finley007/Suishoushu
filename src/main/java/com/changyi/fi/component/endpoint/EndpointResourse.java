package com.changyi.fi.component.endpoint;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.exception.ExceptionHandler;
import com.changyi.fi.core.tool.FileReader;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/endpoints")
public class EndpointResourse {

    @GET
    @Produces("application/json")
    public Response listEndpoints() {
        LogUtil.info(this.getClass(), "Enter listEndpoints endpoint");
        try {
            String response = FileReader.readFileContentByClasspath("endpoints.json");
            LogUtil.info(this.getClass(), "Complete matchEnterprise endpoint handle");
            LogUtil.debug(this.getClass(), "Response: {} ", response);
            return Response.status(Response.Status.OK).
                    header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON + ";charset=UTF-8").entity(response).build();
        } catch (Throwable t) {
            LogUtil.error(this.getClass(), "Run matchEnterprise endpoint error: ", t);
            String res = ExceptionHandler.handle(t);
            return Response.status(Response.Status.OK).entity(res).build();
        }
    }

}
