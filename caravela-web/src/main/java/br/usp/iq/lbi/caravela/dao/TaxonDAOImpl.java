package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.Taxon;

public class TaxonDAOImpl extends DAOImpl<Taxon> implements TaxonDAO {

	@Inject
	public TaxonDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
