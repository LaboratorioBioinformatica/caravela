package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ClassifiedReadByContextDAO;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.TreatmentDAO;
import br.usp.iq.lbi.caravela.domain.SampleLoader;
import br.usp.iq.lbi.caravela.domain.SampleReporter;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.Treatment;


@Controller
public class SampleController {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	private static final int SAMPLE_NAME_MIN_SIZE = 3;
	private final Result result;
	private WebUser webUser;
	private final TreatmentDAO treatmentDAO;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final SampleReporter sampleReporter;
	private final Validator validator;
	private final SampleLoader sampleLoader;
	private final ClassifiedReadByContextDAO classifiedReadByContextDAO;
	
	protected SampleController(){
		this(null, null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public SampleController(Result result, WebUser webUser, TreatmentDAO treatmentDAO, SampleDAO sampleDAO, 
			ContigDAO contigDAO, SampleReporter sampleReporter, Validator validator, SampleLoader sampleLoader, ClassifiedReadByContextDAO classifiedReadByContextDAO){
		this.result = result;
		this.webUser = webUser;
		this.treatmentDAO = treatmentDAO;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.sampleReporter = sampleReporter;
		this.validator = validator;
		this.sampleLoader = sampleLoader;
		this.classifiedReadByContextDAO = classifiedReadByContextDAO;
	}
	
	
	public void view(){
		List<Treatment> treatmentList = treatmentDAO.findAll();
		result.include("treatmentList", treatmentList);
	}
	
	@Get
	public void uploadSampleFileForm(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		System.out.println(sampleId);
		result.include("sample", sample);
		
	}
	
	@Path("/sample/new")
	public void newSample(){
		List<Treatment> treatmentList = treatmentDAO.findAll();
		result.include("treatmentList", treatmentList);
	}
	
	@Post
	public void add(Long treatmentId, String sampleName, String description){
		
		Treatment treatment = treatmentDAO.load(treatmentId);
		
		validator.addIf(sampleName == null || sampleName.length() < SAMPLE_NAME_MIN_SIZE, 
				new SimpleMessage("sample.name", "can not be null or less than " + SAMPLE_NAME_MIN_SIZE, Severity.ERROR));
		validator.addIf(treatment == null , new SimpleMessage("sample.treatment", "invalid", Severity.ERROR));
		
		validator.onErrorForwardTo(this).form();
		
		result.include("treatmentSelected", treatmentId);
		result.include("sampleName", sampleName);
		result.include("description", description);
		
		Sample sample = new Sample(treatment, SampleStatus.CREATED, sampleName, description);
		sampleDAO.save(sample);
				
		result.forwardTo(this).list(treatmentId);
	}
	
	@Post
	public void deleteSample(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		System.out.println("delete sample: " + sample.getName());
		
		sampleDAO.delete(sample);
//		taxonOnContigDAO.removeBySample(sampleId);
		System.out.println("delete classified reads by context from sample: " + sample.getName());
		classifiedReadByContextDAO.removeBySample(sampleId);
//		contigStatisticByTiiDAO.removeBySample(sampleId); 
		result.forwardTo(this).list(sample.getTreatment().getId());
		
		
	}
	
	@Get
	@Path("/sample/list/by/treatment/{treatmentId}")
	public void listByTreatment(Long treatmentId){
		list(treatmentId);
	}
	
	
	@Post
	public void list(Long treatmentId){
		Treatment treatment = treatmentDAO.load(treatmentId);
		List<Sample> sampleList = sampleDAO.listAllByTreatment(treatment);
		Boolean sampleLoaderRunning = sampleLoader.isRunningSampleLoader();
		
		if (sampleList.isEmpty()) {
			validator.add(new SimpleMessage("treatment.list", "There is no sample to show", Severity.WARN));
		}
		
		result.include("sampleList", sampleList);
		result.include("treatmentSelected", treatmentId);
		result.include("isSampleLoaderRunning", sampleLoaderRunning);
		result.forwardTo(this).view();
	}
	
	@Get
	public void form() {
		result.include("treatmentList", treatmentDAO.findAll());

	}
	
	@Get
	@Path("/sample/analyze/{sampleId}")
	public void analyze(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		
		Double taxonCoverage = 0.50;
		
		result.include("taxonCoverage", taxonCoverage);
		result.include("sample", sample);
	}
	
	
		
	@Post
	@Path("/sample/analyze/by/contigName")
	public void analyze(Long sampleId, String contigName){
		Sample sample = sampleDAO.load(sampleId);
		List<Contig> contigList = new ArrayList<Contig>();
		try {
			Contig contig = contigDAO.findContigBySampleAndContigReference(sample, contigName);
			contigList.add(contig);
		} catch (NoResultException nre) {
			validator.add(new SimpleMessage("search.by.contigReference", "No contig found!", Severity.WARN));
			logger.warn(nre.getMessage(), nre);
		}

		result.include("contigList", contigList);
		result.include("sample", sample);
	}
	
	@Get
	@Path("/sample/report/{sampleId}/{tiiValue}/{rank}")
	public void reportChimericPotential(Long sampleId, String tiiValue, String rank){
		Sample sample = sampleDAO.load(sampleId);
		Double tii = new Double(tiiValue);
		sampleReporter.reportChimericPotentialFromContig(sample, tii, rank);
		
	}
	
}
