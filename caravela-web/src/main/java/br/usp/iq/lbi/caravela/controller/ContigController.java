package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.ContigManager;
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
	private final ContigDAO contigDAO;
	private final ContigManager contigManager;
	
	
	public ContigController() {
		this(null,null,null, null, null);
	}
	
	@Inject
	public ContigController(Result result, WebUser webUser,  SampleDAO sampleDAO, ContigDAO contigDAO, ContigManager contigManager) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.contigManager = contigManager;
		
	}
	
	@Path("/contig/view/{contigId}")
	public void view(Long contigId) {
		Contig contig = contigDAO.load(contigId);
		ContigTO contigTO = contigManager.searchContigById(contigId);
		Sample sample = contig.getSample();
		
		result.include("sample", sample);
		result.include("contig", contigTO);
	}
	
	
	
	
	
	public void viewJson() throws FileNotFoundException{
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
