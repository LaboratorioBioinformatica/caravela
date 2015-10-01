package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.TreatmentDAO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;
import br.usp.iq.lbi.caravela.model.Treatment;
import lbi.usp.br.caravela.dto.ContigTO;

@Controller
public class HomeController {
	
	private final Result result;
	private WebUser webUser;
	private final TreatmentDAO treatmentDAO;
	private final SampleDAO sampleDAO;
	
	protected HomeController(){
		this(null, null, null, null);
	}
	
	@Inject
	public HomeController(Result result, WebUser webUser, TreatmentDAO treatmentDAO, SampleDAO sampleDAO){
		this.result = result;
		this.webUser = webUser;
		this.treatmentDAO = treatmentDAO;
		this.sampleDAO = sampleDAO;
	}
    
	@Get("/")
	public void home() {
		
		List<Treatment> treatments = treatmentDAO.findAll();
		result.include("treatments", treatments);
	}
	
	
	
	public void example() throws FileNotFoundException{
		Sample sample = sampleDAO.load(1l);
		SampleFile fileWithAllInformation = sample.getFileWithAllInformation();
		String filePath = fileWithAllInformation.getFilePath();
		Gson gson = new Gson();
		
		JsonStreamParser parser = new JsonStreamParser(new FileReader(filePath));
		
		while (parser.hasNext()) {
			ContigTO contig = gson.fromJson(parser.next(), ContigTO.class);
			 System.out.println(contig);
			
		}
		
		
		
	}
	

}
