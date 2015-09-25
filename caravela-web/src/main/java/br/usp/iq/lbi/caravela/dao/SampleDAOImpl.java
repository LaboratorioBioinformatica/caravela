package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Treatment;



public class SampleDAOImpl extends DAOImpl<Sample> implements SampleDAO {
	

	@Inject
	public SampleDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	public List<Sample> listAll() {
		return entityManager.createQuery("select s from sample s", Sample.class).getResultList();
	}

	public Sample load(Sample sample) {
		return entityManager.find(Sample.class, sample.getId());
	}

	public List<Sample> listAllByTreatment(Treatment treatment) {
		Query query = entityManager.createQuery("from Sample s where s.treatment=:treatment", Sample.class);
		query.setParameter("treatment", treatment);
		return query.getResultList();
	}
	
	



	

}
