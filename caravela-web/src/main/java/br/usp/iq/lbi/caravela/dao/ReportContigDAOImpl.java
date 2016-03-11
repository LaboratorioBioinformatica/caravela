package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.ReportContig;

public class ReportContigDAOImpl extends DAOImpl<ReportContig> implements ReportContigDAO {

	@Inject
	public ReportContigDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	
}
