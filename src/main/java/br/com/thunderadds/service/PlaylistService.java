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
import com.hcf.enums.HCFOperator;
import com.hcf.enums.HCFParameter;

import br.com.thunderadds.domain.Playlist;
import br.com.thunderadds.domain.Usuario;
import br.com.thunderadds.security.Authorize;
import br.com.thunderadds.utils.Utilidades;

@Path("playlist")
public class PlaylistService {
    
    @GET
    @Authorize
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getPlaylists(@Context HttpServletRequest request) {
	try {
	    Usuario usuario = Utilidades.getUserBySession(request.getHeader("Authorization"));
	    
	    List<Playlist> playlist = new HCFConnection<>(Playlist.class).search(null, "idUsuario", usuario.getId(), HCFParameter.EQUAL, HCFOperator.NONE);

	    return Response.status(Status.OK).entity(playlist).build();
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro " + e.getMessage()).build();
	}
    }
    
    @GET
    @Authorize
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getPlaylistById(@Context HttpServletRequest request, @PathParam("id") Long id) {
	try {
	    Usuario usuario = Utilidades.getUserBySession(request.getHeader("Authorization"));
	    
	    Playlist playlist = new HCFConnection<>(Playlist.class).searchWithOneResult(null,
		    "id", id, HCFParameter.EQUAL, HCFOperator.NONE,
		    "idUsuario", usuario.getId(), HCFParameter.EQUAL, HCFOperator.AND);

	    return Response.status(Status.OK).entity(playlist).build();
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro " + e.getMessage()).build();
	}
    }

    @POST
    @Authorize
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response savePlaylist(@Context HttpServletRequest request, String json) {
	try {
	    Usuario usuario = Utilidades.getUserBySession(request.getHeader("Authorization"));
	    
	    JSONObject jsonObj = (JSONObject) new JSONParser().parse(json);
	    
	    Playlist playlist = new Playlist(usuario.getId());
	    
	    if(jsonObj.get("id") != null) {
		playlist = new HCFConnection<>(Playlist.class).getById(Long.valueOf(jsonObj.get("id").toString()));
	    }
	    
	    if(!playlist.getIdUsuario().equals(usuario.getId())) {
		return Response.status(Status.FORBIDDEN).build();
	    }
	    
	    playlist.setNome(jsonObj.get("nome").toString());
	    playlist.setGenero(jsonObj.get("genero") != null ? jsonObj.get("genero").toString() : null);
	    
	    new HCFConnection<>(Playlist.class).save(playlist);

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
    public Response deletePlaylist(@Context HttpServletRequest request, @PathParam("id") Long id) {
	try {
	    Usuario usuario = Utilidades.getUserBySession(request.getHeader("Authorization"));
	    
	    Playlist playlist = new HCFConnection<>(Playlist.class).searchWithOneResult(null, 
		    "id", id, HCFParameter.EQUAL, HCFOperator.NONE,
		    "idUsuario", usuario.getId(), HCFParameter.EQUAL, HCFOperator.AND);
	    
	    new HCFConnection<>(Playlist.class).delete(playlist);

	    return Response.status(Status.OK).build();
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro " + e.getMessage()).build();
	}
    }
    
}


























