package br.usp.iq.lbi.caravela.dao;

import br.usp.iq.lbi.caravela.model.User;

public interface UserDAO extends DAO<User> {
	User searchUserByUserNameAndPassword(String userName, String password);
	User searchUserByUserName(String userName);


}
