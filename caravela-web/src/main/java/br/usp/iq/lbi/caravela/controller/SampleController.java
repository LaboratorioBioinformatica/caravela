package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ClassifiedReadByContextDAO;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.ContigStatisticByTiiDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.TaxonOnContigDAO;
import br.usp.iq.lbi.caravela.dao.TreatmentDAO;
import br.usp.iq.lbi.caravela.domain.SampleLoader;
import br.usp.iq.lbi.caravela.domain.SampleProcessorManager;
import br.usp.iq.lbi.caravela.domain.SampleReporter;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;
import br.usp.iq.lbi.caravela.model.Treatment;


@Controller
public class SampleController {
	
	private static final int SAMPLE_NAME_MIN_SIZE = 3;
	private final Result result;
	private WebUser webUser;
	private final TreatmentDAO treatmentDAO;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final SampleReporter sampleReporter;
	private final Validator validator;
	private final SampleLoader sampleLoader;
	private final TaxonOnContigDAO taxonOnContigDAO;
	private final ClassifiedReadByContextDAO classifiedReadByContextDAO;
	private final ContigStatisticByTiiDAO contigStatisticByTiiDAO;
	private final SampleProcessorManager sampleProcessorManager;
	
	protected SampleController(){
		this(null, null, null, null, null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public SampleController(Result result, WebUser webUser, TreatmentDAO treatmentDAO, SampleDAO sampleDAO, 
			ContigDAO contigDAO, SampleReporter sampleReporter, Validator validator, SampleLoader sampleLoader,
			TaxonOnContigDAO taxonOnContigDAO, ClassifiedReadByContextDAO classifiedReadByContextDAO, ContigStatisticByTiiDAO contigStatisticByTiiDAO, SampleProcessorManager sampleProcessorManager){
		this.result = result;
		this.webUser = webUser;
		this.treatmentDAO = treatmentDAO;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.sampleReporter = sampleReporter;
		this.validator = validator;
		this.sampleLoader = sampleLoader;
		this.taxonOnContigDAO = taxonOnContigDAO;
		this.classifiedReadByContextDAO = classifiedReadByContextDAO;
		this.contigStatisticByTiiDAO = contigStatisticByTiiDAO;
		this.sampleProcessorManager = sampleProcessorManager;
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
	public void process(Long sampleId){
		
		try {
			sampleLoader.loadFromFileToDatabase(sampleId);
			validator.add(new SimpleMessage("treatment.load", "Sample proccess successfuly", Severity.SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
			validator.add(new SimpleMessage("sample.process", "Something was wrong!", Severity.ERROR));
			result.use(Results.http()).sendError(500, "Error to process sample");
		}
		result.forwardTo(this).list(sampleId);
	}
	
	@Get
	@Path("/sample/process/all")
	public void processAllSample(){
		sampleProcessorManager.processAllSamplesUploaded();
		result.use(Results.nothing());
	}
	
	@Post
	public void list(Long treatmentId){
		Treatment treatment = treatmentDAO.load(treatmentId);
		List<Sample> sampleList = sampleDAO.listAllByTreatment(treatment);
		
		if (sampleList.isEmpty()) {
			validator.add(new SimpleMessage("treatment.list", "There is no sample to show", Severity.WARN));
		}
		
		result.include("sampleList", sampleList);
		result.include("treatmentSelected", treatmentId);
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
		Double tiiGreaterOrEqualsThan = 0.5;
		Integer numberOfFeaturesGreaterOrEqualsThan = 0;
		Integer numberOfBoundariesLessOrEqualsThan = 0;
		Double indexOfConsistencyTaxonomicByCountReadsGreaterOrEqualsThan = 0.40;
		Double indexOfVerticalConsistencyTaxonomicGreaterOrEqualsThan = 0.70;
		TaxonomicRank genus = TaxonomicRank.GENUS;
		String rankString = genus.toString();
		
		Double taxonCoverage = 0.50;
		
		Integer firstResult = 0;
		Integer maxResult = 100;
		
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, tiiGreaterOrEqualsThan, numberOfFeaturesGreaterOrEqualsThan, genus, numberOfBoundariesLessOrEqualsThan, indexOfConsistencyTaxonomicByCountReadsGreaterOrEqualsThan, indexOfVerticalConsistencyTaxonomicGreaterOrEqualsThan, firstResult, maxResult);

		
		result.include("taxonCoverage", taxonCoverage);
		result.include("rankSelected", rankString.toUpperCase());
		result.include("tiiGreaterOrEqualsThan", tiiGreaterOrEqualsThan);
		result.include("numberOfFeaturesGreaterOrEqualsThan", numberOfFeaturesGreaterOrEqualsThan);
		result.include("numberOfBoundariesLessOrEqualsThan", numberOfBoundariesLessOrEqualsThan);
		result.include("ICTCRGreaterOrEqualsThan", indexOfConsistencyTaxonomicByCountReadsGreaterOrEqualsThan);
		result.include("IVCTGreaterOrEqualsThan", indexOfVerticalConsistencyTaxonomicGreaterOrEqualsThan);
		result.include("firstResult", firstResult);
		
		result.include("contigList", contigList);
		result.include("sample", sample);
	}
	
	
	@Post
	@Path("/sample/analyze/by")
	public void analyze(Long sampleId, Double tiiGreaterOrEqualsThan, Integer numberOfFeaturesGreaterOrEqualsThan, String rank, Integer numberOfBoundariesLessOrEqualsThan, Double ICTCRGreaterOrEqualsThan, Double IVCTGreaterOrEqualsThan){
		Sample sample = sampleDAO.load(sampleId);
		Integer firstResult = 0;
		Integer maxResult = 100;
		String rankString = rank.toUpperCase();
		TaxonomicRank taxonomicRank = TaxonomicRank.valueOf(rankString);
		
		
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, tiiGreaterOrEqualsThan, numberOfFeaturesGreaterOrEqualsThan, taxonomicRank, numberOfBoundariesLessOrEqualsThan, ICTCRGreaterOrEqualsThan, IVCTGreaterOrEqualsThan, firstResult, maxResult);
	
		result.include("rankSelected", rankString.toUpperCase());
		result.include("tiiGreaterOrEqualsThan", tiiGreaterOrEqualsThan);
		result.include("numberOfFeaturesGreaterOrEqualsThan", numberOfFeaturesGreaterOrEqualsThan);
		result.include("numberOfBoundariesLessOrEqualsThan", numberOfBoundariesLessOrEqualsThan);
		result.include("ICTCRGreaterOrEqualsThan", ICTCRGreaterOrEqualsThan);
		result.include("IVCTGreaterOrEqualsThan", IVCTGreaterOrEqualsThan);
		result.include("firstResult", firstResult);
		
		result.include("contigList", contigList);
		result.include("sample", sample);
	}
	
	@Post
	@Path("/sample/analyze/by/contigName")
	public void analyze(Long sampleId, String contigName){
		Sample sample = sampleDAO.load(sampleId);
		 Contig contig = contigDAO.findContigBySampleAndContigReference(sample, contigName);
		List<Contig> contigList = new ArrayList<Contig>();
		contigList.add(contig);

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
	
	
	@Post
	@Path("/sample/analyze")
	public void analyze(Long sampleId, String operator, String tiiValue ){
		Sample sample = sampleDAO.load(sampleId);
		Double tiiDoubleValue = new Double(tiiValue);
		List<Contig> contigList = contigDAO.FindByContigBySampleAndTiiGreatherThan(sample, tiiDoubleValue, 100);
		result.include("tiiValue", tiiValue);
		result.include("contigList", contigList);
		result.include("sample", sample);
		
	}

}
