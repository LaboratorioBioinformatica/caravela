package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.ReportSample;

public class ReportSampleDAOImpl extends DAOImpl<ReportSample> implements ReportSampleDAO{

	@Inject
	public ReportSampleDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
