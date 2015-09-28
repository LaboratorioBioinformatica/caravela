package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.inject.Inject;

import lbi.usp.br.caravela.dto.ContigTO;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

@Controller
public class ContigController {
	
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	
	public ContigController() {
		this(null,null,null);
	}
	
	@Inject
	public ContigController(Result result, WebUser webUser,  SampleDAO sampleDAO) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
	}
	
	public void view() throws FileNotFoundException{
		Sample sample = sampleDAO.load(1l);
		SampleFile fileWithAllInformation = sample.getFileWithAllInformation();
		String filePath = fileWithAllInformation.getFilePath();
		Gson gson = new Gson();
		
		JsonStreamParser parser = new JsonStreamParser(new FileReader(filePath));
		ContigTO contig = gson.fromJson(parser.next(), ContigTO.class);
		
		result.include("sample", sample);
		result.include("contig", contig);
	}
	
}
