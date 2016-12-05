
package br.usp.iq.lbi.caravela.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.observer.upload.UploadSizeLimit;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.SampleFileDAO;
import br.usp.iq.lbi.caravela.domain.SampleReporter;
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
	private final SampleReporter sampleReporter;
	private final Environment environment;
	
	public UploadController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public UploadController(Result result, WebUser webUser, SampleDAO sampleDAO, SampleReporter sampleReporter, SampleFileDAO sampleFileDAO, Environment environment) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.sampleReporter = sampleReporter;
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
		
		File fileToSaveUniqueFileName = File.createTempFile("sampleFile_", ".json", new File(getFullPathDirectoryUpload()));
		file.writeTo(fileToSaveUniqueFileName);
		
		SampleFile sampleFile = new SampleFile(sample, FileType.ALL_JSON, FileStatus.UPLOADED, "pier", fileToSaveUniqueFileName.getAbsolutePath());
		sample.toUploaded();

		sampleFileDAO.save(sampleFile);
		sampleDAO.save(sample);
		
		result.use(Results.json()).from(sample).serialize();
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
