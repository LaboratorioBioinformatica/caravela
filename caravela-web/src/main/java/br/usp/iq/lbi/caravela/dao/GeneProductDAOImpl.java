package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.GeneProduct;

public class GeneProductDAOImpl extends DAOImpl<GeneProduct> implements GeneProductDAO {

	@Inject
	public GeneProductDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	
}
