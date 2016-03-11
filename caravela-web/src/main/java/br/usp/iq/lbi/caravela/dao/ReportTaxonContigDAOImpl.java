package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.ReportTaxonOnContig;

public class ReportTaxonContigDAOImpl extends DAOImpl<ReportTaxonOnContig> implements ReportTaxonContigDAO {

	@Inject
	public ReportTaxonContigDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	
}
