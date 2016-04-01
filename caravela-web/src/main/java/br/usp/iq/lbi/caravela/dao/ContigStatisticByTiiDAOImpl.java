package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.ContigStatisticByTii;

public class ContigStatisticByTiiDAOImpl extends DAOImpl<ContigStatisticByTii> implements ContigStatisticByTiiDAO {

	@Inject
	public ContigStatisticByTiiDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	
}
