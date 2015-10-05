package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.ContigTOProcessor;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;
import lbi.usp.br.caravela.dto.ContigTO;

@Controller
public class UploadController {
	
	private final Result result;
	
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigTOProcessor contigTOProcessor;
	
	public UploadController() {
		this(null, null, null, null);
	}
	
	@Inject
	public UploadController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigTOProcessor contigTOProcessor) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigTOProcessor = contigTOProcessor;
	}
	
	public void view(){
		result.include("pageTitle", "Caravela - Upload");
	}
	
	@Get
	@Path("/upload/save/sample/{sampleId}")
	public void save(Long sampleId) throws FileNotFoundException{
		Sample sample = sampleDAO.load(sampleId);
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
