package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.Read;

public class ReadDAOImpl extends DAOImpl<Read> implements ReadDAO {

	@Inject
	public ReadDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
