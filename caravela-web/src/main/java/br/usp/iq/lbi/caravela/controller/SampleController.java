package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.usp.iq.lbi.caravela.dao.*;
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
import br.usp.iq.lbi.caravela.domain.SampleLoader;
import br.usp.iq.lbi.caravela.domain.SampleReporter;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.Study;


@Controller
public class SampleController {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	private static final int SAMPLE_NAME_MIN_SIZE = 3;
	private final Result result;
	private WebUser webUser;
	private final StudyDAO studyDAO;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final SampleReporter sampleReporter;
	private final Validator validator;
	private final SampleLoader sampleLoader;
	private final ClassifiedReadByContextDAO classifiedReadByContextDAO;
	private final TaxonOnContigDAO taxonOnContigDAO;
	
	protected SampleController(){
		this(null, null, null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public SampleController(Result result, WebUser webUser, StudyDAO studyDAO, SampleDAO sampleDAO,
							ContigDAO contigDAO, SampleReporter sampleReporter, Validator validator, SampleLoader sampleLoader, ClassifiedReadByContextDAO classifiedReadByContextDAO, TaxonOnContigDAO taxonOnContigDAO){
		this.result = result;
		this.webUser = webUser;
		this.studyDAO = studyDAO;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.sampleReporter = sampleReporter;
		this.validator = validator;
		this.sampleLoader = sampleLoader;
		this.classifiedReadByContextDAO = classifiedReadByContextDAO;
		this.taxonOnContigDAO = taxonOnContigDAO;
	}
	
	
	public void view(){
		List<Study> studyList = studyDAO.findAll();
		result.include("studyList", studyList);
	}
	
	@Get
	public void uploadSampleFileForm(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		System.out.println(sampleId);
		result.include("sample", sample);
		
	}
	
	@Path("/sample/new")
	public void newSample(){
		List<Study> studyList = studyDAO.findAll();
		result.include("studyList", studyList);
	}
	
	@Post
	public void add(Long studyId, String sampleName, String description){
		
		Study study = studyDAO.load(studyId);
		
		validator.addIf(sampleName == null || sampleName.length() < SAMPLE_NAME_MIN_SIZE, 
				new SimpleMessage("sample.name", "can not be null or less than " + SAMPLE_NAME_MIN_SIZE, Severity.ERROR));
		validator.addIf(study == null , new SimpleMessage("sample.study", "invalid", Severity.ERROR));
		
		validator.onErrorForwardTo(this).form();
		
		result.include("studySelected", studyId);
		result.include("sampleName", sampleName);
		result.include("description", description);
		
		Sample sample = new Sample(study, SampleStatus.CREATED, sampleName, description);
		sampleDAO.save(sample);
				
		result.forwardTo(this).list(studyId);
	}

	@Post
	public void delete(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		sample.toBeDelete();
		sampleDAO.update(sample);
		result.forwardTo(this).list(sample.getStudy().getId());
	}


	//TODO move to assync process
//	@Post
//	public void deleteSample(Long sampleId){
//		Sample sample = sampleDAO.load(sampleId);
//		System.out.println("delete sample: " + sample.getName());
//		sampleDAO.delete(sample);
//		taxonOnContigDAO.removeBySample(sampleId);
//		System.out.println("delete classified reads by context from sample: " + sample.getName());
//		classifiedReadByContextDAO.removeBySample(sampleId);
////		contigStatisticByTiiDAO.removeBySample(sampleId);
//		result.forwardTo(this).list(sample.getStudy().getId());
//	}
	
	@Get
	@Path("/sample/list/by/study/{studyId}")
	public void listByStudy(Long studyId){
		list(studyId);
	}
	
	
	@Post
	public void list(Long studyId){
		final Study study = studyDAO.load(studyId);
		final List<Sample> sampleList = sampleDAO.listAllActiveSampleByStudy(study);

		Boolean sampleLoaderRunning = sampleLoader.isRunningSampleLoader();

		if (sampleList.isEmpty()) {
			validator.add(new SimpleMessage("study.list", "There is no sample to show", Severity.WARN));
		}
		
		result.include("sampleList", sampleList);
		result.include("studySelected", studyId);
		result.include("isSampleLoaderRunning", sampleLoaderRunning);
		result.forwardTo(this).view();
	}
	
	@Get
	public void form() {
		result.include("studyList", studyDAO.findAll());

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
