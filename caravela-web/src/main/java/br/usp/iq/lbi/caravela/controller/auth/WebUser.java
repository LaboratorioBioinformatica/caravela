package br.usp.iq.lbi.caravela.controller.auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.usp.iq.lbi.caravela.model.User;

@SessionScoped
@Named("userLoggedIn")
public class WebUser implements Serializable {
	
	private static final long serialVersionUID = 4566449233287384303L;

	private User loggedUser;
	
	public void login(User user){
		loggedUser = user;
	}
	
	public String getName() {
		return loggedUser.getName();
	}
	
	public Boolean isLogged() {
		return loggedUser != null;
	}
	
	public void logout(){
		loggedUser = null;
	}

}
