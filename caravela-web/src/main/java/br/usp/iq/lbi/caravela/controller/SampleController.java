package br.usp.iq.lbi.caravela.controller;

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
	
	protected SampleController(){
		this(null, null, null, null, null);
	}
	
	@Inject
	public SampleController(Result result, WebUser webUser, TreatmentDAO treatmentDAO, SampleDAO sampleDAO, ContigDAO contigDAO){
		this.result = result;
		this.webUser = webUser;
		this.treatmentDAO = treatmentDAO;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
	}
	
	public void view(){
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
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, 100);
		result.include("contigList", contigList);
		result.include("sample", sample);
	}
	
	@Post
	@Path("/sample/analyze")
	public void analyze(Long sampleId, String operator, Double tiiValue ){
		Sample sample = sampleDAO.load(sampleId);
		List<Contig> contigList = contigDAO.FindByContigBySampleAndTiiGreatherThan(sample, tiiValue, 100);
		result.include("tiiValue", tiiValue);
		result.include("contigList", contigList);
		result.include("sample", sample);
		
	}


}
