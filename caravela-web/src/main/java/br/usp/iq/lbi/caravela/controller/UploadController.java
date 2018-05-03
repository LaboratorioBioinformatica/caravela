
package br.usp.iq.lbi.caravela.controller;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.observer.upload.UploadSizeLimit;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.SampleFileDAO;
import br.usp.iq.lbi.caravela.domain.SampleReporter;
import br.usp.iq.lbi.caravela.infrastructure.Zipper;
import br.usp.iq.lbi.caravela.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	private static final String JSON = "application/json";
	private static final String ZIP = "application/zip";
	private static final String GZIP = "application/gzip";

	private static final String DEFAULT_RANK_TO_REPORT = "genus";

	private static final Double DEFAULT_TII_VALUE = new Double(0d);

	private final Result result;
	
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final SampleFileDAO sampleFileDAO;
	private final SampleReporter sampleReporter;
	private final Environment environment;
	private final Validator validator;
	
	public UploadController() {
		this(null, null, null, null, null, null, null);
	}
	
	@Inject
	public UploadController(Result result, WebUser webUser, SampleDAO sampleDAO, SampleReporter sampleReporter, SampleFileDAO sampleFileDAO, Environment environment, Validator validator) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.sampleReporter = sampleReporter;
		this.sampleFileDAO = sampleFileDAO;
		this.environment = environment;
		this.validator = validator;
	}
	
	public void view(){
		result.include("pageTitle", "Caravela - Upload");
	}
	
	
	@Post
	@UploadSizeLimit(sizeLimit=2024 * 1024 * 1024, fileSizeLimit=2024 * 1024 * 1024)
	public void uploadSampleFile(UploadedFile file, Long sampleId) throws IOException{

		Sample sample = sampleDAO.load(sampleId);


		file.getContentType();

		final String fullPathDirectoryUpload = getFullPathDirectoryUpload();

		final File fileTMP = new File(fullPathDirectoryUpload);

		final File temporaryFileToSave = File.createTempFile("sampleTemporaryFile_", ".tmp", fileTMP);
		file.writeTo(temporaryFileToSave);
		final String temporaryAbsoluteFilePath = temporaryFileToSave.getAbsolutePath();

		logger.info("Temporary file was created - " + temporaryFileToSave.getName());

		final String contentType = file.getContentType();
		logger.info("Application type: " + contentType);

		final File ultimateFile = File.createTempFile("sampleFile_", ".json", fileTMP);
		if(JSON.equals(contentType)){
			temporaryFileToSave.renameTo(ultimateFile);
			logger.info("File uploaded renamed to: " + ultimateFile);

		}

		if(ZIP.equals(contentType)){
			final String sampleFileName = ultimateFile.getName();

			new Zipper().unZipIt(temporaryAbsoluteFilePath, fullPathDirectoryUpload, sampleFileName);
			logger.info("Unzip file: " + sampleFileName);

			temporaryFileToSave.delete();
			logger.info("Temporary file: " + temporaryAbsoluteFilePath + " deleted.");

		}


		updateSampleToUploaded(sample, ultimateFile);
		result.use(Results.json()).from(sample).serialize();



	}

	private void updateSampleToUploaded(Sample sample, File fileToSaveUniqueFileName) {
		SampleFile sampleFile = new SampleFile(sample, FileType.ALL_JSON, FileStatus.UPLOADED, "pier", fileToSaveUniqueFileName.getAbsolutePath());
		sample.toUploaded();

		logger.info("Sample " + sample.getName() + " change status to " + SampleStatus.UPLOADED);

		sampleFileDAO.save(sampleFile);
		sampleDAO.save(sample);
	}

	private String getFullPathDirectoryUpload() {
		File catalinaBase = new File( System.getProperty("catalina.base")).getAbsoluteFile();
		String catalinaDirectoryBase = catalinaBase.getParent();
		return catalinaDirectoryBase.concat(environment.get("directory.upload"));
	}
	
	
	@Get
	@Path("/upload/save/sample/{sampleId}")
	public void save(Long sampleId) throws FileNotFoundException{
		Sample sample = sampleDAO.load(sampleId);
		
		sampleReporter.reportChimericPotentialFromContig(sample, DEFAULT_TII_VALUE, DEFAULT_RANK_TO_REPORT);
		
		result.include("sample", sample);
	}
	

}
