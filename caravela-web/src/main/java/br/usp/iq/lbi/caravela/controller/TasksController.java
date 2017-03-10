package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.domain.NCBITaxonManager;

@Controller
public class TasksController {
	
	
	private final Result result;
	private final NCBITaxonManager ncbiTaxonManager;	
	
	protected TasksController(){
		this(null, null);
	}
	
	
	@Inject
	public TasksController(Result result, NCBITaxonManager ncbiTaxonManager) {
		this.result = result;
		this.ncbiTaxonManager = ncbiTaxonManager;
	}
	
	public void view(){
		
		String taskStatus = "No Status";
		if(ncbiTaxonManager.isRegisterRunning()){
			System.out.println("NCBI register is running!");
			taskStatus = "NCBI register is running!";
		} else {
			System.out.println("NCBI register is NOT running!");
			taskStatus = "NCBI register is NOT running!";
		}
		
		result.include("taskStatus",  taskStatus);
		
		
	}
	
	
}
