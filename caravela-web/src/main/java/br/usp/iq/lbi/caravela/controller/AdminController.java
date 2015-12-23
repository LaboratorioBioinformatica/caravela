package br.usp.iq.lbi.caravela.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeSet;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.domain.ContigTOProcessorImpl;
import br.usp.iq.lbi.caravela.domain.NCBITaxonManager;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(ContigTOProcessorImpl.class);
	
	private final Result result;
	private final NCBITaxonManager ncbiTaxonManager;
	
	protected AdminController() {
		this(null, null);
	}
	
	@Inject
	public AdminController(Result result, NCBITaxonManager ncbiTaxonManager){
		this.result = result;
		this.ncbiTaxonManager =  ncbiTaxonManager;
	}
	
	
	public void view(){
		Long numberOfTaxon = ncbiTaxonManager.countNumberOfTaxon();
		result.include("numberOfTaxon", numberOfTaxon);
		
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
	
	public static void main(String[] args) {
		TreeSet<String> treeSet = new TreeSet<String>();
		
	}
	

}
