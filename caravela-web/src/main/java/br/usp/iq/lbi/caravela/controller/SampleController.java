package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.TreatmentDAO;
import br.usp.iq.lbi.caravela.domain.SampleReporter;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Treatment;


@Controller
public class SampleController {
	
	private final Result result;
	private WebUser webUser;
	private final TreatmentDAO treatmentDAO;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final SampleReporter sampleReporter;
	
	protected SampleController(){
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public SampleController(Result result, WebUser webUser, TreatmentDAO treatmentDAO, SampleDAO sampleDAO, ContigDAO contigDAO, SampleReporter sampleReporter){
		this.result = result;
		this.webUser = webUser;
		this.treatmentDAO = treatmentDAO;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.sampleReporter = sampleReporter;
	}
	
	public void view(){
		List<Treatment> treatmentList = treatmentDAO.findAll();
		result.include("treatmentList", treatmentList);
	}
	
	@Path("/sample/new")
	public void newSample(){
		List<Treatment> treatmentList = treatmentDAO.findAll();
		result.include("treatmentList", treatmentList);
	}
	
	@Post
	public void list(Long treatmentId){
		Treatment treatment = treatmentDAO.load(treatmentId);
		List<Sample> sampleList = sampleDAO.listAllByTreatment(treatment);
		result.include("sampleList", sampleList);
		result.include("treatmentSelected", treatmentId);
		result.forwardTo(this).view();
	}
	
	@Get
	@Path("/sample/analyze/{sampleId}")
	public void analyze(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		Double tiiGreaterOrEqualsThan = 0.5;
		Integer numberOfFeaturesGreaterOrEqualsThan = 1;
		Integer numberOfBoundariesLessOrEqualsThan = 0;
		Double unclassifiedLessOrEqualsThan = 0.0;
		Double undefinedLessOrEqualsThan = 0.0;
		Integer firstResult = 0;
		Integer maxResult = 100;
		
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, tiiGreaterOrEqualsThan, numberOfFeaturesGreaterOrEqualsThan, numberOfBoundariesLessOrEqualsThan, unclassifiedLessOrEqualsThan, undefinedLessOrEqualsThan, firstResult, maxResult);

		result.include("tiiGreaterOrEqualsThan", tiiGreaterOrEqualsThan);
		result.include("numberOfFeaturesGreaterOrEqualsThan", numberOfFeaturesGreaterOrEqualsThan);
		result.include("numberOfBoundariesLessOrEqualsThan", numberOfBoundariesLessOrEqualsThan);
		result.include("unclassifiedLessOrEqualsThan", unclassifiedLessOrEqualsThan);
		result.include("undefinedLessOrEqualsThan", undefinedLessOrEqualsThan);
		result.include("firstResult", firstResult);
		
		result.include("contigList", contigList);
		result.include("sample", sample);
	}
	
	
	@Post
	@Path("/sample/analyze/by")
	public void analyze(Long sampleId, Double tiiGreaterOrEqualsThan, Integer numberOfFeaturesGreaterOrEqualsThan, Integer numberOfBoundariesLessOrEqualsThan, Double unclassifiedLessOrEqualsThan, Double undefinedLessOrEqualsThan){
		Sample sample = sampleDAO.load(sampleId);
		Integer firstResult = 0;
		Integer maxResult = 100;
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, tiiGreaterOrEqualsThan, numberOfFeaturesGreaterOrEqualsThan, numberOfBoundariesLessOrEqualsThan, unclassifiedLessOrEqualsThan, undefinedLessOrEqualsThan, firstResult, maxResult);
	
		result.include("tiiGreaterOrEqualsThan", tiiGreaterOrEqualsThan);
		result.include("numberOfFeaturesGreaterOrEqualsThan", numberOfFeaturesGreaterOrEqualsThan);
		result.include("numberOfBoundariesLessOrEqualsThan", numberOfBoundariesLessOrEqualsThan);
		result.include("unclassifiedLessOrEqualsThan", unclassifiedLessOrEqualsThan);
		result.include("undefinedLessOrEqualsThan", undefinedLessOrEqualsThan);
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
