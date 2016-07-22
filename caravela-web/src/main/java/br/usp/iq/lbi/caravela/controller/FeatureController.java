package br.usp.iq.lbi.caravela.controller;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.FeatureDAO;
import br.usp.iq.lbi.caravela.dao.PhilodistDAO;
import br.usp.iq.lbi.caravela.dao.GeneProductDAO;
import br.usp.iq.lbi.caravela.dao.FeatureAnnotationDAO;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.FeatureAnnotation;
import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Philodist;

@Controller
public class FeatureController {
	
	private final Result result;
	private final WebUser webUser;
	private final PhilodistDAO philodistDAO;
	private final FeatureDAO featureDAO;
	private final GeneProductDAO geneProductDAO;
	private final FeatureAnnotationDAO featureAnnotationDAO;
	
	
	public FeatureController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public FeatureController(Result result, WebUser webUser, PhilodistDAO philodistDAO, FeatureDAO featureDAO, GeneProductDAO geneProductDAO, FeatureAnnotationDAO featureAnnotationDAO) {
		this.result = result;
		this.webUser = webUser;
		this.philodistDAO = philodistDAO;
		this.featureDAO = featureDAO;
		this.geneProductDAO = geneProductDAO;
		this.featureAnnotationDAO = featureAnnotationDAO;
	}
	
	@Get
	@Path("/feature/philodist/{featureId}")
	public void searchPhilodist(Long featureId){
		Philodist philodist = philodistDAO.findPhilodistByFeatureId(featureId);
		result.use(Results.json()).withoutRoot().from(philodist).serialize();
	}
	
	@Get
	@Path("/feature/geneproduct/{featureId}")
	public void searchGeneProduct(Long featureId){
		
		try {
			GeneProduct geneProduct = geneProductDAO.findGeneProductByFeatureId(featureId);
			result.use(Results.json()).withoutRoot().from(geneProduct).serialize();
			
		} catch (NoResultException nre) {
			result.use(Results.json());
		}

	}
	
	@Get
	@Path("/feature/annotations/{featureId}")
	public void searchFeatureAnnotations(Long featureId){
		List<FeatureAnnotation> featureAnnotations = featureAnnotationDAO.findFeatureAnnotationsByFeatureId(featureId);
		
		result.use(Results.json()).withoutRoot().from(featureAnnotations).serialize();
	}
	
	@Get
	@Path("/feature/{featureId}")
	public void searchFeature(Long featureId){
		Feature feature = featureDAO.load(featureId);
		result.use(Results.json()).withoutRoot().from(feature).serialize();
	}


}
