package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.FeatureAnnotation;

public class FeatureAnnotationDAOImpl extends DAOImpl<FeatureAnnotation> implements FeatureAnnotationDAO {
	
	@Inject
	public FeatureAnnotationDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<FeatureAnnotation> findFeatureAnnotationsByFeatureId(Long featureId) {
		Query query = entityManager.createQuery("from FeatureAnnotation fa where fa.feature.id=:featureId", FeatureAnnotation.class);
		query.setParameter("featureId", featureId);
		return query.getResultList();
	}
}
