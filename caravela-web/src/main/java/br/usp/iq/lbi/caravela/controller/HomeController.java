package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;

import br.com.caelum.brutauth.auth.annotations.SimpleBrutauthRules;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.CanAccess;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;

@Controller
public class HomeController {
	
	private final Result result;
	private WebUser webUser;
	
	protected HomeController(){
		this(null, null);
	}
	
	@Inject
	public HomeController(Result result, WebUser webUser){
		this.result = result;
		this.webUser = webUser;
	}
    
	@SimpleBrutauthRules(CanAccess.class)
	@Get("/")
	public void home() {
		
		result.include("teste", "Esse é mais um teste de novo!");
		result.include("userName", webUser.getName());
	}
	

}
