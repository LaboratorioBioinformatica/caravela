package br.usp.iq.lbi.caravela.dao;


import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.Contig;


public class ContigDAOImpl extends DAOImpl<Contig> implements ContigDAO {

	@Inject
	public ContigDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}


}
