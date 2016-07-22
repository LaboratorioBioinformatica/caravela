package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.GeneProduct;

public class GeneProductDAOImpl extends DAOImpl<GeneProduct> implements GeneProductDAO {

	@Inject
	public GeneProductDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public GeneProduct findGeneProductByFeatureId(Long featureId) {
		Query query = entityManager.createQuery("from GeneProduct gp where gp.feature.id=:featureId", GeneProduct.class);
		query.setParameter("featureId", featureId);
		return (GeneProduct) query.getSingleResult();
	}

}
