package br.com.thunderadds.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hcf.HCFConnection;
import com.hcf.HCFFactory;
import com.hcf.enums.HCFOperator;
import com.hcf.enums.HCFParameter;

import br.com.thunderadds.domain.Musica;
import br.com.thunderadds.domain.Usuario;
import br.com.thunderadds.security.Authorize;
import br.com.thunderadds.utils.Utilidades;

@Path("musica")
public class MusicaService {
    
    @GET
    @Authorize
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMusicas(@Context HttpServletRequest request) {
	try {
	    Usuario usuario = Utilidades.getUserBySession(request.getHeader("Authorization"));
	    
	    List<Musica> musicas = new HCFConnection<>(Musica.class).search(null, "idUsuario", usuario.getId(), HCFParameter.EQUAL, HCFOperator.NONE);

	    return Response.status(Status.OK).entity(musicas).build();
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro " + e.getMessage()).build();
	}
    }
    
    @GET
    @Authorize
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMusicaById(@Context HttpServletRequest request, @PathParam("id") Long id) {
	try {
	    Usuario usuario = Utilidades.getUserBySession(request.getHeader("Authorization"));
	    
	    Musica musica = new HCFConnection<>(Musica.class).searchWithOneResult(null,
		    "id", id, HCFParameter.EQUAL, HCFOperator.NONE,
		    "idUsuario", usuario.getId(), HCFParameter.EQUAL, HCFOperator.AND);

	    return Response.status(Status.OK).entity(musica).build();
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro " + e.getMessage()).build();
	}
    }

    @POST
    @Authorize
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response saveMusica(@Context HttpServletRequest request, String json) {
	try {
	    Usuario usuario = Utilidades.getUserBySession(request.getHeader("Authorization"));

	    JSONObject jsonObj = (JSONObject) new JSONParser().parse(json);
	    
	    Musica musica = new Musica(usuario.getId());
	    
	    if(jsonObj.get("id") != null) {
		musica = new HCFConnection<>(Musica.class).getById(Long.valueOf(jsonObj.get("id").toString()));
	    }
	    
	    if(!musica.getIdUsuario().equals(usuario.getId())) {
		return Response.status(Status.FORBIDDEN).build();
	    }
	    
	    musica.setNome(""); // TODO
	    musica.setUrl(""); // TODO
	    musica.setIdUsuario(usuario.getId());
	    
	    new HCFConnection<>(Musica.class).save(musica);

	    return Response.status(Status.OK).build();
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro " + e.getMessage()).build();
	}
    }

    @DELETE
    @Authorize
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteMusica(@Context HttpServletRequest request, @PathParam("id") Long id) {
	try {
	    Usuario usuario = Utilidades.getUserBySession(request.getHeader("Authorization"));
	    
	    Musica musica = new HCFConnection<>(Musica.class).searchWithOneResult(null, 
		    "id", id, HCFParameter.EQUAL, HCFOperator.NONE,
		    "idUsuario", usuario.getId(), HCFParameter.EQUAL, HCFOperator.AND);
	    
	    new HCFConnection<>(Musica.class).delete(musica);

	    return Response.status(Status.OK).build();
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro " + e.getMessage()).build();
	}
    }
}

















