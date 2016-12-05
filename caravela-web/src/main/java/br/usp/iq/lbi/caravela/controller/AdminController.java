package br.usp.iq.lbi.caravela.controller;

import java.io.File;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.usp.iq.lbi.caravela.domain.NCBITaxonManager;

@Controller
public class AdminController {
	

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private final Result result;
	private final NCBITaxonManager ncbiTaxonManager;
	private final Validator validator;
	private final Environment environment;
	
	protected AdminController() {
		this(null, null, null, null);
	}
	
	@Inject
	public AdminController(Result result, NCBITaxonManager ncbiTaxonManager, Validator validator, Environment environment){
		this.result = result;
		this.ncbiTaxonManager =  ncbiTaxonManager;
		this.validator = validator;
		this.environment = environment;
	}
	
	
	@Path("/admin/ncbiTaxonomyLoader")
	public void ncbiTaxonomyLoaderView(){
		
		File catalinaBase = new File( System.getProperty("catalina.base")).getAbsoluteFile();
		String catalinaDirectoryBase = catalinaBase.getParent();
		environment.set("dir.base.caravela", catalinaDirectoryBase);
		
		Long numberOfTaxon = ncbiTaxonManager.countNumberOfTaxon();
		
		
		result.include("ncbiFilePathTaxonomyName", getfullPath(environment.get("directory.config")).concat(environment.get("ncbi.file.taxonomy.name")));
		result.include("ncbiFilePathTaxonomyNode", getfullPath(environment.get("directory.config")).concat(environment.get("ncbi.file.taxonomy.node")));
		
		result.include("numberOfTaxon", numberOfTaxon);
		
	}
	
	public void view(){
		
		File catalinaBase = new File( System.getProperty("catalina.base")).getAbsoluteFile();
		String catalinaDirectoryBase = catalinaBase.getParent();
		environment.set("dir.base.caravela", catalinaDirectoryBase);
		
		Long numberOfTaxon = ncbiTaxonManager.countNumberOfTaxon();
		
		result.include("ncbiFilePathTaxonomyName", getfullPath(environment.get("directory.config")).concat(environment.get("ncbi.file.taxonomy.name")));
		result.include("ncbiFilePathTaxonomyNode", getfullPath(environment.get("directory.config")).concat(environment.get("ncbi.file.taxonomy.node")));
		
		
		result.include("directoryUpload", getfullPath(environment.get("directory.upload")));
		result.include("directoryBase", environment.get("dir.base.caravela"));
		result.include("directoryConfig", getfullPath(environment.get("directory.config")));
		
		result.include("numberOfTaxon", numberOfTaxon);
		
	}
	
	private String getfullPath(String string) {
		return environment.get("dir.base.caravela").concat(string);
	}

	@Post
	public void load(String scientificaNameFile, String nodesFile) {
		try {
			File fileNCBIScientificNames = new File(scientificaNameFile);
			File fileNCBINodes = new File(nodesFile);
			ncbiTaxonManager.clear();
			ncbiTaxonManager.register(fileNCBIScientificNames, fileNCBINodes);
			validator.add(new SimpleMessage("ncbi.taxonomy.file.load", "NCBI taxonomy information loaded successfuly", Severity.SUCCESS));
		} catch (Exception e) {
			logger.error("NCBI files: Scientific names and Nodes NOT FOUND!", e);
			validator.add(
					new SimpleMessage("ncbi.taxonomy.file.load", 
							"Scientific names/Nodes not found! Verify if " + scientificaNameFile + " and " + nodesFile + " exists.", 
							Severity.ERROR));
		}
		
		validator.onErrorForwardTo(this).view();
		result.forwardTo(this).view();
	}

}
