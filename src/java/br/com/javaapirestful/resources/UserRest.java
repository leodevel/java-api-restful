package br.com.javaapirestful.resources;

import br.com.javaapirestful.model.User;
import br.com.javaapirestful.controller.UserController;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserRest {
    
    @GET    
    @Secured
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response users(){        
        
        List<User> list = UserController.all();
        
        return Response.status(Response.Status.OK)
                .entity(list)
                .type(MediaType.APPLICATION_JSON)
                .build();
        
    }
    
}