package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;

public class FeatureDAOImpl extends DAOImpl<Feature> implements FeatureDAO {

	@Inject
	public FeatureDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Feature> loadAllFeaturesByContig(Contig contig) {
		Query query = entityManager.createQuery("from Feature f where f.contig=:contig", Feature.class);
		query.setParameter("contig", contig);
		return query.getResultList();
	}
	

}
