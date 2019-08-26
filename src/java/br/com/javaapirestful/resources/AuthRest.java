package br.com.javaapirestful.resources;

import br.com.javaapirestful.util.Error;
import br.com.javaapirestful.model.User;
import br.com.javaapirestful.controller.UserController;
import br.com.javaapirestful.util.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.mongodb.client.model.Filters.eq;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;

@Path("/auth")
public class AuthRest {

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(String json) {

        String name;
        String email;
        String password;

        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            name = node.path("name").asText("");
            email = node.path("email").asText("");
            password = node.path("password").asText("");

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                throw new NullPointerException();
            }

        } catch (NullPointerException ex) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(Error.setError("You must enter the name, email and password to register"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (Exception ex) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(Error.setError("Expected content is a json object"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }

        User user;

        try {

            user = UserController.search(eq("email", email));

        } catch (Exception ex) {
            return Response.status(Response.Status.OK)
                    .entity(Error.setError("Failed to check if user exists"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }

        if (user != null) {
            return Response.status(Response.Status.OK)
                    .entity(Error.setError("User already registered with this email"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        try {
            
            String token = Util.generateToken();
            String newPassword = DigestUtils.sha256Hex(password);
            
            user = new User(name, email, newPassword, token);
            UserController.insert(user);

            return Response.status(Response.Status.OK)
                    .entity(user)
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch(Exception ex){                    
            return Response.status(Response.Status.OK)
                    .entity(Error.setError("Failed to register"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

    }

    @POST
    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(String json) {

        String email;
        String password;
        
        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            email = node.path("email").asText("");
            password = node.path("password").asText("");

            if (email.isEmpty() || password.isEmpty()) {
                throw new NullPointerException();
            }

        } catch (Exception ex) {
            return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Error.setError("Credentials must be entered"))
                        .type(MediaType.APPLICATION_JSON)
                        .build();

        }
        
        
        User user;

        try {

            user = UserController.search(eq("email", email));

        } catch (Exception ex) {
            return Response.status(Response.Status.OK)
                    .entity(Error.setError("Failed to check if user exists"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }

        if (user == null) {
            return Response.status(Response.Status.OK)
                    .entity(Error.setError("User is not registered"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        
        if (!user.getPassword().equals(DigestUtils.sha256Hex(password))){
            return Response.status(Response.Status.OK)
                    .entity(Error.setError("Invalid credentials"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        
        try {
            
            String token = Util.generateToken();
            user.setToken(token);
            
            UserController.update(eq("email", email), user);

            return Response.status(Response.Status.OK)
                    .entity(user)
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch(Exception ex){                    
            return Response.status(Response.Status.OK)
                    .entity(Error.setError("Failed to authenticate user"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
               
    }

}
