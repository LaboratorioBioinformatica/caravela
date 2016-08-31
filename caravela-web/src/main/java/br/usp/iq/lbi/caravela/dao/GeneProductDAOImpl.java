package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Sample;

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
	
	public List<GeneProduct> findBySampleAndGeneProductName(Sample sample, String geneProductName) {
		TypedQuery<GeneProduct> query = entityManager.createQuery("SELECT gp FROM GeneProduct gp WHERE gp.feature.contig.sample=:sample and gp.product LIKE:geneProductName", GeneProduct.class);
		List<GeneProduct> geneProductList = query
				.setParameter("sample", sample)
				.setParameter("geneProductName", "%"+geneProductName+"%").getResultList();
		
		return geneProductList;
	}

}
