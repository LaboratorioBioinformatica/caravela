package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.FeatureAnnotation;

public class FeatureAnnotationDAOImpl extends DAOImpl<FeatureAnnotation> implements FeatureAnnotationDAO {
	
	@Inject
	public FeatureAnnotationDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
}
