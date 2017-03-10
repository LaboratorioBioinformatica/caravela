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
import br.usp.iq.lbi.caravela.domain.SampleLoader;
import br.usp.iq.lbi.caravela.domain.SampleProcessorManager;
import br.usp.iq.lbi.caravela.domain.exception.ServiceAlreadyIsRunningException;

@Controller
public class AdminController {
	

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private final Result result;
	private final NCBITaxonManager ncbiTaxonManager;
	private final Validator validator;
	private final Environment environment;
	private final SampleLoader sampleLoader;
	private final SampleProcessorManager sampleProcessorManager;
	
	protected AdminController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public AdminController(Result result, NCBITaxonManager ncbiTaxonManager, Validator validator, Environment environment, SampleLoader sampleLoader, SampleProcessorManager sampleProcessorManager){
		this.result = result;
		this.ncbiTaxonManager =  ncbiTaxonManager;
		this.validator = validator;
		this.environment = environment;
		this.sampleLoader = sampleLoader;
		this.sampleProcessorManager = sampleProcessorManager;
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
	
	public void sampleProcessorView(){
		boolean sampleProcessorRunning = sampleLoader.isRunningSampleLoader();
		result.include("isSampleProcessorRunning", sampleProcessorRunning);
	}
	
	@Post
	public void processAllSample(){
		
		if( ! sampleLoader.isRunningSampleLoader()){
			sampleProcessorManager.processAllSamplesUploaded();
		} else {
			validator.add(new SimpleMessage("sample.processor", "Action can not be done becouse Sample Loader already is running!", Severity.WARN));
		}
		
		result.forwardTo(this).sampleProcessorView();
	}
	
	public void view(){
		
		if(ncbiTaxonManager.isRegisterRunning()){
			validator.add(new SimpleMessage("ncbi.taxonomy.file.load", "NCBI taxonomy information is loading ... ", Severity.INFO));
		}
		
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
		} catch (ServiceAlreadyIsRunningException isRunningException){
			
			validator.add(
					new SimpleMessage("ncbi.taxonomy.file.load", 
							isRunningException.getMessage(), 
							Severity.WARN));
	
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
