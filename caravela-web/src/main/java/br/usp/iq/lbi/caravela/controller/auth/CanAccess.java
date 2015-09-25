package br.usp.iq.lbi.caravela.controller.auth;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.brutauth.auth.annotations.GlobalRule;
import br.com.caelum.brutauth.auth.annotations.HandledBy;
import br.com.caelum.brutauth.auth.rules.CustomBrutauthRule;

@RequestScoped @GlobalRule
@HandledBy(LoggedHandler.class)
public class CanAccess implements CustomBrutauthRule {
	
	private WebUser loggedUser;
	
	protected CanAccess() {
		this(null);
	}
	
	@Inject
	public CanAccess(WebUser loggedUser) {
		this.loggedUser = loggedUser; 
	}


	public boolean isAllowed() {
		return loggedUser.isLogged();
	}

}
