package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.ContigTOProcessor;
import br.usp.iq.lbi.caravela.domain.SampleReporter;
import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

@Controller
public class UploadController {
	
	private static final String DEFAULT_RANK_TO_REPORT = "genus";

	private static final Double DEFAULT_TII_VALUE = new Double(0d);

	private final Result result;
	
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigTOProcessor contigTOProcessor;
	private final SampleReporter sampleReporter;
	
	public UploadController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public UploadController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigTOProcessor contigTOProcessor, SampleReporter sampleReporter) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigTOProcessor = contigTOProcessor;
		this.sampleReporter = sampleReporter;
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
		
		FileReader reader = new FileReader(filePath);
		JsonStreamParser parser = new JsonStreamParser(reader);
		
		Double totalNumberOfContigLoaded = 0d;
		Double totalNumberOfContigToBeLoading = getTotalNumberOfFileLines(filePath);
		
		System.out.println("Total Number Of contig to be loading: " + totalNumberOfContigToBeLoading);
		while (parser.hasNext()) {
			ContigTO contigTO = gson.fromJson(parser.next(), ContigTO.class);
			contigTOProcessor.convert(sample, contigTO);
			totalNumberOfContigLoaded++;
			System.out.println(totalNumberOfContigLoaded/totalNumberOfContigToBeLoading);
		}
		System.out.println("Contig loaded");
		
		sampleReporter.reportChimericPotentialFromContig(sample, DEFAULT_TII_VALUE, DEFAULT_RANK_TO_REPORT);
		
		
		result.include("sample", sample);
	}
	
	private Double getTotalNumberOfFileLines(String fileName){
		Long totalLines = 0l;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			totalLines = stream.count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (double) totalLines;
	}

}
