package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.ReportClassifiedReadByContex;

public class ReportClassifiedReadByContextDAOImpl extends DAOImpl<ReportClassifiedReadByContex> implements ReportClassifiedReadByContextDAO {

	@Inject
	public ReportClassifiedReadByContextDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
