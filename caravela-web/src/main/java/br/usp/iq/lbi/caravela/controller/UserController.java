package br.usp.iq.lbi.caravela.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.UserDAO;
import br.usp.iq.lbi.caravela.model.User;

import javax.inject.Inject;

@Controller
public class UserController {

    private static final int NAME_MIN_SIZE = 5;
    private static final int EMAIL_MIN_SIZE = 5;
    private static final int PASSWORD_MIN_SIZE = 6;

    private final Result result;
    private final WebUser webUser;
    private final Validator validator;
    private final UserDAO userDAO;


    public UserController() {
        this(null, null, null, null);
    }

    @Inject
    public UserController(Result result, WebUser webUser, Validator validator, UserDAO userDAO) {
        this.result = result;
        this.webUser = webUser;
        this.validator = validator;
        this.userDAO = userDAO;
    }

    public void view(){

    }

    public void form(){

    }

    public void add(String name, String email, String password, String confirmPassword){
        validadeUserParameters(name, email, password, confirmPassword);
        validator.onErrorForwardTo(this).form();
        userDAO.searchUserByUserName(email);

        new User(email, name, password);



    }

    private void validadeUserParameters(String name, String email, String password, String confirmPassword) {
        validator.addIf(name == null || name.length() < NAME_MIN_SIZE,
                new SimpleMessage("name", "can not be null or less than " + NAME_MIN_SIZE, Severity.ERROR));

        validator.addIf(email == null || email.length() < EMAIL_MIN_SIZE,
                new SimpleMessage("email", "can not be null or less than " + EMAIL_MIN_SIZE, Severity.ERROR));

        validator.addIf(password == null || password.length() < PASSWORD_MIN_SIZE,
                new SimpleMessage("password", "can not be null or less than " + PASSWORD_MIN_SIZE, Severity.ERROR));

        validator.addIf(password == null || ! password.equals(confirmPassword),
                new SimpleMessage("confirm-password", "can not be null or different from password"));

    }

}
