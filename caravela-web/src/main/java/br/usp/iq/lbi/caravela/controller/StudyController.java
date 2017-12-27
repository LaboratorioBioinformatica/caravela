package br.usp.iq.lbi.caravela.controller;

import java.util.List;

import javax.inject.Inject;

import br.usp.iq.lbi.caravela.model.Study;

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
import br.usp.iq.lbi.caravela.dao.StudyDAO;
import br.usp.iq.lbi.caravela.model.Sample;

@Controller
public class StudyController {

	
	private static final int STUDY_NAME_MIN_SIZE = 3;
	private final Result result;
	private final StudyDAO studyDAO;
	private final SampleDAO sampleDAO;
	private final Validator validator;

	/**
	 * CDI eyes only
	 */
	public StudyController() {
		this(null, null, null, null);
	}

	@Inject
	public StudyController(Result result, StudyDAO studyDAO, SampleDAO sampleDAO, Validator validator) {
		this.result = result;
		this.studyDAO = studyDAO;
		this.sampleDAO = sampleDAO;
		this.validator = validator;
	}
	
	public void view(){
	}

	@Get
	public void list() {
		List<Study> studies = studyDAO.listAll();
		if (studies.isEmpty()) {
			validator.add(new SimpleMessage("study.list", "There is no study to show", Severity.WARN));
		}
		result.include("studies", studies);
	}
	
	@Path("/study/sample/list/{studyId}")
	@Get
	public void sampleList(Long studyId){
		Study study = studyDAO.load(studyId);
		List<Sample> samples = sampleDAO.listAllByStudy(study);
		result.use(Results.json()).withoutRoot().from(samples).exclude("study").serialize();
	}
	
	@Get
	public void form() {

	}

	@Post
	public void add(Study study) {
			validator.addIf(study.getName() == null || study.getName().length() < STUDY_NAME_MIN_SIZE,
					new SimpleMessage("study.name", "can not be null or less than " + STUDY_NAME_MIN_SIZE, Severity.ERROR));
		result.include("study", study);
		validator.onErrorForwardTo(this).form();
		studyDAO.save(study);
		result.redirectTo(StudyController.class).list();
	}

}
