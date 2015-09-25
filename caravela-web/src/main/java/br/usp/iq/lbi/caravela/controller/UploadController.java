package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;

@Controller
public class UploadController {
	
	private final Result result;
	private WebUser webUser;
	
	public UploadController() {
		this(null, null);
	}
	
	@Inject
	public UploadController(Result result, WebUser webUser) {
		this.result = result;
		this.webUser = webUser;
	}
	
	public void view(){
		result.include("pageTitle", "Caravela - Upload");
	}
	
	

}
