package br.usp.iq.lbi.caravela.controller.rest;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;

import javax.inject.Inject;

@Controller
public class SampleController {

    private final Result result;
    private final WebUser webUser;

    protected SampleController(){
        this.result = null;
        this.webUser = null;
    }

    @Inject
    public SampleController(Result result, WebUser webUser){
        this.result = result;
        this.webUser = webUser;
    }

    // example to request that endpoint at local: http://localhost:8080/caravela/rest/samples/1
    @Get
    @Path("/rest/samples/{studyId}")
    public void view(Long studyId){
        if( studyId == null){
            studyId = 0l;
        }
        result.use(Results.json()).withoutRoot().from(studyId).serialize();
    }


}
