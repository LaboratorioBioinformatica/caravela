package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;

import br.com.caelum.brutauth.auth.annotations.Public;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.model.User;

@Controller 
@Public
public class LoginController {
	
	private Result result;
	private WebUser webUser;
	
	protected LoginController(){
		this(null, null);
	}
	
	@Inject	
	public LoginController(Result result, WebUser webUser){
		this.result = result;
		this.webUser = webUser;
	}
	
	public void form(){
		result.include("pageTitle", "Caravela - Login");
		
	}
	
	@Post
	public void login(User user){
		webUser.login(user);
		result.redirectTo(HomeController.class).home();
	}
	
	public void logout(){
		webUser.logout();
		result.redirectTo(LoginController.class).form();
	}

}
