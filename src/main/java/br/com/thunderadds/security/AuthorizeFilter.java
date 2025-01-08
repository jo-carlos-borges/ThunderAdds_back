package br.com.thunderadds.security;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import br.com.thunderadds.domain.Usuario;
import br.com.thunderadds.utils.Utilidades;

@Provider
@Authorize
@Priority(Priorities.AUTHENTICATION)
public class AuthorizeFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
	String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
	
	try {
	    Usuario admin = Utilidades.getUserBySession(authorizationHeader);
	    
	    if (admin != null) {
		if (Duration.between(admin.getUltimaSolicitacao(), LocalDateTime.now()).toSeconds() > 3600000) {
		    Login.usuariosLogados.remove(admin);
		    requestContext.abortWith(Response.status(Status.REQUEST_TIMEOUT).build());
		} else {
		    admin.setUltimaSolicitacao(LocalDateTime.now());
		}
	    } else {
		requestContext.abortWith(Response.status(Status.REQUEST_TIMEOUT).build());
	    }
	} catch (Exception e) {
	    requestContext.abortWith(Response.status(Status.REQUEST_TIMEOUT).build());
	}
    }
    
    

}
