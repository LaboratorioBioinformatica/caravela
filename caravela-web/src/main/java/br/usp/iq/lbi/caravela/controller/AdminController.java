package br.usp.iq.lbi.caravela.controller;

import java.io.File;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.usp.iq.lbi.caravela.domain.ContigTOProcessorImpl;
import br.usp.iq.lbi.caravela.domain.NCBITaxonManager;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(ContigTOProcessorImpl.class);
	
	private final Result result;
	private final NCBITaxonManager ncbiTaxonManager;
	private final Validator validator;
	
	protected AdminController() {
		this(null, null, null);
	}
	
	@Inject
	public AdminController(Result result, NCBITaxonManager ncbiTaxonManager, Validator validator){
		this.result = result;
		this.ncbiTaxonManager =  ncbiTaxonManager;
		this.validator = validator;
	}
	
	
	public void view(){
		Long numberOfTaxon = ncbiTaxonManager.countNumberOfTaxon();
		result.include("numberOfTaxon", numberOfTaxon);
		
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
	
	public void register(){
		ncbiTaxonManager.clear();
		File fileNCBIScientificNames = new File("/data/caravela/config/ncbiScientificNames.txt");
		File fileNCBINodes = new File("/data/caravela/config/ncbiNodes.txt");
		try {
			ncbiTaxonManager.register(fileNCBIScientificNames, fileNCBINodes);
		} catch (FileNotFoundException e) {
			logger.error("NCBI files: Scientific names and Nodes NOT FOUND!", e);
		}
		result.forwardTo(this).view();
		
	}

}
