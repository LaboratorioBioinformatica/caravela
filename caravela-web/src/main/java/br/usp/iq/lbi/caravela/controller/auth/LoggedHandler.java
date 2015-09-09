package br.usp.iq.lbi.caravela.controller.auth;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.brutauth.auth.handlers.RuleHandler;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.LoginController;

@RequestScoped
public class LoggedHandler implements RuleHandler {

	private final Result result;
	
	protected LoggedHandler() {
		this(null);
	}
	
	@Inject
	public LoggedHandler(Result result) {
		this.result = result;
	}
	
	public void handle() {
		result.redirectTo(LoginController.class).form();
	}

}
