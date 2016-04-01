package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.TaxonOnContig;

public class TaxonOnContigDAOImpl extends DAOImpl<TaxonOnContig> implements TaxonOnContigDAO {

	@Inject
	public TaxonOnContigDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	
}
