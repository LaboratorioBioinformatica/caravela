package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import lbi.usp.br.caravela.dto.search.TaxonCounterTO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;

public class TaxonDAOImpl extends DAOImpl<Taxon> implements TaxonDAO {

	@Inject
	public TaxonDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	
	
	public List<TaxonCounterTO> findTaxonsBySampleAndScientificName(Sample sample, String scientificName) {
		Query query = entityManager.createQuery(" SELECT NEW lbi.usp.br.caravela.dto.search.TaxonCounterTO(t, COUNT(t.id)) FROM Taxon t WHERE t.read.sample=:sample and t.scientificName LIKE:scientificName GROUP BY t.scientificName ORDER BY COUNT(t.id) DESC", TaxonCounterTO.class);
		query.setParameter("sample", sample);
		query.setParameter("scientificName", "%"+scientificName+"%");
		return query.getResultList();
	}
}
