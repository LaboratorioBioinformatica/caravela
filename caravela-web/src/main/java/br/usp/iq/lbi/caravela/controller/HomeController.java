package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.domain.NCBITaxonManager;

@Controller
public class HomeController {
	
	private final Result result;
	private WebUser webUser;
	private final NCBITaxonManager ncbiTaxonManager;
	
	protected HomeController(){
		this(null, null, null);
	}
	
	@Inject
	public HomeController(Result result, WebUser webUser, NCBITaxonManager ncbiTaxonManager){
		this.result = result;
		this.webUser = webUser;
		this.ncbiTaxonManager = ncbiTaxonManager;
	}
    
	@Get("/")
	public void home() {
		if(ncbiTaxonManager.isClean()){
			result.forwardTo(AdminController.class).ncbiTaxonomyLoaderView();
		}
		result.forwardTo(SampleController.class).view();
		
	}
	
	

}
