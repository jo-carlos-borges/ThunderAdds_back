package br.com.thunderadds.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;

import com.hcf.HCFConnection;
import com.hcf.enums.HCFOperator;
import com.hcf.enums.HCFParameter;

import br.com.thunderadds.domain.Usuario;

@Path("login")
public class Login {

    public static List<Usuario> usuariosLogados = new ArrayList<>();
    
    @GET
    @SuppressWarnings("unchecked")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response login(@Context HttpServletRequest request, @QueryParam("login") String login, @QueryParam("password") String password) {
	try {
	    Usuario admin = new HCFConnection<>(Usuario.class).searchWithOneResult(null, "login", login, HCFParameter.EQUAL, HCFOperator.NONE,
		    "password", password, HCFParameter.EQUAL, HCFOperator.AND);
	    
	    if(admin != null) {
		
		JSONObject user = new JSONObject();
		String tokenSessao = String.valueOf(Math.abs(request.getRemoteHost().hashCode())) + String.valueOf(Math.abs(admin.getEmail().hashCode()));
		user.put("token", tokenSessao);
		user.put("id", admin.getId());
		user.put("email", admin.getEmail());
		
		if (usuariosLogados.contains(admin)) {
		    usuariosLogados.remove(admin);
		}
		admin.setTokenSessao(tokenSessao);
		admin.setUltimaSolicitacao(LocalDateTime.now());
		
		usuariosLogados.add(admin);
		
		return Response.status(Status.OK).entity(user).build();
	    } else {
		return Response.status(Status.BAD_REQUEST).build();
	    }
	    
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
    }
}
