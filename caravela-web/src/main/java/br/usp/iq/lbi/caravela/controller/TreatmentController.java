package br.usp.iq.lbi.caravela.controller;

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
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.TreatmentDAO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Treatment;

@Controller
public class TreatmentController {

	
	private final Result result;
	private final TreatmentDAO treatmentDAO;
	private final SampleDAO sampleDAO;
	private final Validator validator;

	/**
	 * CDI eyes only
	 */
	public TreatmentController() {
		this(null, null, null, null);
	}

	@Inject
	public TreatmentController(Result result, TreatmentDAO treatmentDAO, SampleDAO sampleDAO, Validator validator) {
		this.result = result;
		this.treatmentDAO = treatmentDAO;
		this.sampleDAO = sampleDAO;
		this.validator = validator;
	}
	
	public void view(){
	}

	@Get
	public void list() {
		List<Treatment> treatments = treatmentDAO.listAll();
		if (treatments.isEmpty()) {
			validator.add(new SimpleMessage("treatment.list", "no treatment found", Severity.WARN));
		}
		result.include("treatments", treatments);
	}
	
	@Path("/treatment/sample/list/{treatmentId}")
	@Get
	public void sampleList(Long treatmentId){
		Treatment treatment = treatmentDAO.load(treatmentId);
		List<Sample> samples = sampleDAO.listAllByTreatment(treatment);
		result.use(Results.json()).withoutRoot().from(samples).exclude("treatment").serialize();
	}
	
	@Get
	public void form() {

	}

	@Post
	public void add(Treatment treatment) {
		System.out.println(treatment.getName());
		System.out.println(treatment.getDescription());
		result.include("treatment", treatment);
		result.redirectTo(TreatmentController.class).form();
	}

}
