package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;

@Controller
public class AdminController {

	private final Result result;
	
	protected AdminController() {
		this(null);
	}
	
	@Inject
	public AdminController(Result result){
		this.result = result;
	}
	
	
	public void view(){
		
	}
	

}
