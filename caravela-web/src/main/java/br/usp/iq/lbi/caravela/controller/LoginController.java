package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;

@Controller
public class LoginController {
	
	private Result result;
	
	protected LoginController(){
		this(null);
	}
	
	@Inject
	public LoginController(Result result){
		this.result = result;
	}
	
	public void form(){
		
	}

}
