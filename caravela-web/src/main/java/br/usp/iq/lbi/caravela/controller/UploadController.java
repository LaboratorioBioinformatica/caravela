package br.usp.iq.lbi.caravela.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.observer.upload.UploadSizeLimit;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.SampleFileDAO;
import br.usp.iq.lbi.caravela.domain.ContigTOProcessor;
import br.usp.iq.lbi.caravela.domain.SampleReporter;
import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.model.FileStatus;
import br.usp.iq.lbi.caravela.model.FileType;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;

@Controller
public class UploadController {
	
	private static final String DEFAULT_RANK_TO_REPORT = "genus";

	private static final Double DEFAULT_TII_VALUE = new Double(0d);

	private final Result result;
	
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final SampleFileDAO sampleFileDAO;
	private final ContigTOProcessor contigTOProcessor;
	private final SampleReporter sampleReporter;
	private final Validator validator;
	private final Environment environment;
	
	public UploadController() {
		this(null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public UploadController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigTOProcessor contigTOProcessor, SampleReporter sampleReporter, Validator validator, SampleFileDAO sampleFileDAO, Environment environment) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigTOProcessor = contigTOProcessor;
		this.sampleReporter = sampleReporter;
		this.validator = validator;
		this.sampleFileDAO = sampleFileDAO;
		this.environment = environment;
	}
	
	public void view(){
		result.include("pageTitle", "Caravela - Upload");
	}
	
	
	@Post
	@UploadSizeLimit(sizeLimit=2024 * 1024 * 1024, fileSizeLimit=2024 * 1024 * 1024)
	public void uploadSampleFile(UploadedFile file, Long sampleId) throws IOException{
		
		Sample sample = sampleDAO.load(sampleId);
		
		File fileToSaveUniqueFileName = File.createTempFile("sampleFile_", ".json", new File(environment.get("directory.upload")));
		file.writeTo(fileToSaveUniqueFileName);
		
		SampleFile sampleFile = new SampleFile(sample, FileType.ALL_JSON, FileStatus.UPLOADED, "pier", fileToSaveUniqueFileName.getAbsolutePath());
		sample.toUploaded();

		sampleFileDAO.save(sampleFile);
		sampleDAO.save(sample);
		
		result.use(Results.json()).from(sample).serialize();
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
