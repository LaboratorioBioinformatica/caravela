package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.Philodist;

public class PhilodistDAOImpl extends DAOImpl<Philodist> implements PhilodistDAO {

	@Inject
	public PhilodistDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Philodist findPhilodistByFeatureId(Long featureId) {
		Query query = entityManager.createQuery("from Philodist p where p.feature.id=:featureId", Philodist.class);
		query.setParameter("featureId", featureId);
		return (Philodist) query.getSingleResult();
	}
	

	

}
