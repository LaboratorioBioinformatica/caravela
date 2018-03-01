package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.User;

public class UserDAOImpl extends DAOImpl<User> implements UserDAO {
	
	@Inject
	public UserDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public User searchUserByUserNameAndPassword(String userName, String password){
		 Query query = entityManager.createQuery("from User u where u.userName=:userName and u.password=:password", User.class);
		 query.setParameter("userName", userName);
		 query.setParameter("password", password);
		 return (User) query.getSingleResult();
	}

	public User searchUserByUserName(String userName){
		Query query = entityManager.createQuery("from User u where u.userName=:userName", User.class);
		query.setParameter("userName", userName);
		return (User) query.getSingleResult();
	}
	

	
}
