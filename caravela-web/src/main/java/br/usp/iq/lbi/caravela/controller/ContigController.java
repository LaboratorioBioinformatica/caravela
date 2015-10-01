package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.ContigTOProcessor;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;
import lbi.usp.br.caravela.dto.ContigTO;

@Controller
public class ContigController {
	
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigTOProcessor contigTOProcessor;
	
	public ContigController() {
		this(null,null,null, null);
	}
	
	@Inject
	public ContigController(Result result, WebUser webUser,  SampleDAO sampleDAO, ContigTOProcessor contigCreator) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigTOProcessor = contigCreator;
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
	
	public void save() throws FileNotFoundException{
		Sample sample = sampleDAO.load(1l);
		SampleFile fileWithAllInformation = sample.getFileWithAllInformation();
		String filePath = fileWithAllInformation.getFilePath();
		Gson gson = new Gson();
		
		JsonStreamParser parser = new JsonStreamParser(new FileReader(filePath));
		
		while (parser.hasNext()) {
			ContigTO contigTO = gson.fromJson(parser.next(), ContigTO.class);
			contigTOProcessor.convert(sample, contigTO);
		}
		
		result.include("sample", sample);
		
	}
	
}
