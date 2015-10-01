package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.Feature;

public class FeatureDAOImpl extends DAOImpl<Feature> implements FeatureDAO {

	@Inject
	public FeatureDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	

}
