package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.PhilodistDAO;
import br.usp.iq.lbi.caravela.model.Philodist;

@Controller
public class FeatureController {
	
	private final Result result;
	private final WebUser webUser;
	private final PhilodistDAO philodistDAO;
	
	public FeatureController() {
		this(null, null, null);
	}
	
	@Inject
	public FeatureController(Result result, WebUser webUser, PhilodistDAO philodistDAO) {
		this.result = result;
		this.webUser = webUser;
		this.philodistDAO = philodistDAO;
	}
	
	@Get
	@Path("/feature/philodist/{featureId}")
	public void searchPhilodist(Long featureId){
		Philodist philodist = philodistDAO.findPhilodistByFeatureId(featureId);
		result.use(Results.json()).withoutRoot().from(philodist).serialize();
	}
	
	@Get
	@Path("/feature/{featureId}")
	public void searchFeature(Long featureId){
		Philodist philodist = philodistDAO.findPhilodistByFeatureId(featureId);
		result.use(Results.json()).withoutRoot().from(philodist).serialize();
	}


}
