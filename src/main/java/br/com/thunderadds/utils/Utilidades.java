package br.com.thunderadds.utils;

import java.util.Optional;

import br.com.thunderadds.domain.Usuario;
import br.com.thunderadds.security.Login;

public class Utilidades {
    
    public static Usuario getUserBySession(String authorizationHeader) {
	try {
	    String token = authorizationHeader.substring("Bearer".length(), authorizationHeader.indexOf("@@@")).trim();
	    Optional<Usuario> result = Login.usuariosLogados.stream().filter(user -> user.getTokenSessao().equalsIgnoreCase(token)).findFirst();
	    Usuario user = result.isPresent() ? result.get() : null;
	    
	    return user;
	} catch (Exception ignore) {
	    return null;
	}
    }

}
