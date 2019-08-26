package br.com.javaapirestful.resources;

import br.com.javaapirestful.util.Error;
import br.com.javaapirestful.model.User;
import br.com.javaapirestful.controller.UserController;
import br.com.javaapirestful.util.Util;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import static com.mongodb.client.model.Filters.eq;
import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader
                = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(Error.setError("Invalid token. Could not access resource"))
                            .type(MediaType.APPLICATION_JSON)
                            .build());
        }

        String parts[] = authorizationHeader.split(" ");

        try {

            User user = UserController.search(eq("token", parts[1].trim()));

            if (user == null) {
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity(Error.setError("Invalid token. Could not access resource"))
                                .type(MediaType.APPLICATION_JSON)
                                .build());
            }

            try {

                boolean validToken = Util.isValidToken(user.getToken());

                if (!validToken) {
                    throw new JWTVerificationException("");
                }

            } catch (TokenExpiredException ex) {
                requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(Error.setError(ex.getMessage()))
                            .type(MediaType.APPLICATION_JSON)
                            .build());

            } catch (JWTVerificationException ex) {
                requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(Error.setError("Unable to validate token"))
                            .type(MediaType.APPLICATION_JSON)
                            .build());

            }

        } catch (Exception ex) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(Error.setError("Invalid token. Could not access resource"))
                            .type(MediaType.APPLICATION_JSON)
                            .build());
        }

    }

}
