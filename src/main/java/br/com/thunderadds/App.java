package br.com.thunderadds;

import java.nio.charset.StandardCharsets;
import java.util.List;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import com.hcf.HCFConnection;
import com.hcf.HCFFactory;

import br.com.thunderadds.domain.Musica;

@ApplicationPath("/")
public class App extends Application {

    final static public byte[] KEY = "7f-j&CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&RNbTDCA3"
	    .getBytes(StandardCharsets.UTF_8);

    public App() {
	HCFFactory.getInstance().getFactory();
    }
    

}
