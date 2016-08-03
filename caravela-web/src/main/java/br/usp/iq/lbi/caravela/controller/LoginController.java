package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.brutauth.auth.annotations.Public;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.UserDAO;
import br.usp.iq.lbi.caravela.model.User;

@Controller 
@Public
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private Result result;
	private WebUser webUser;
	private UserDAO userDAO;
	private Validator validator;
	
	protected LoginController(){
		this(null, null, null, null);
	}
	
	@Inject	
	public LoginController(Result result, WebUser webUser,Validator validator, UserDAO userDAO){
		this.result = result;
		this.webUser = webUser;
		this.validator = validator;
		this.userDAO = userDAO;
	}
	
	public void form(){
		result.include("pageTitle", "Caravela - Login");
		
	}
	
	@Post
	public void login(String userName, String password){
		try {
			User userFromDB = userDAO.searchUserByUserNameAndPassword(userName, password);
			webUser.login(userFromDB);
			result.redirectTo(HomeController.class).home();
		} catch (NoResultException nre){
			validator.add(new SimpleMessage("login.user", "Invalid user name - " + userName + " -  or password!", Severity.WARN));
			result.redirectTo(LoginController.class).form();
		}
		catch (Exception e) {
			logger.error("ERROR TO LOGIN", e);
			validator.add(new SimpleMessage("login", "Sorry, some thing is worng! ", Severity.ERROR));
		}
		validator.onErrorForwardTo(LoginController.class).form();
		
	}
	
	public void logout(){
		webUser.logout();
		result.redirectTo(LoginController.class).form();
	}

}
