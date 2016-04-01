package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.ClassifiedReadByContex;

public class ClassifiedReadByContextDAOImpl extends DAOImpl<ClassifiedReadByContex> implements ClassifiedReadByContextDAO {

	@Inject
	public ClassifiedReadByContextDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
