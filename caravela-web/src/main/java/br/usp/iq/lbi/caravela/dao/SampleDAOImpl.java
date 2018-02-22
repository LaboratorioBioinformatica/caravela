package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.Study;



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

	public List<Sample> listAllByStudy(Study study) {
		Query query = entityManager.createQuery("from Sample s where s.study=:study", Sample.class);
		query.setParameter("study", study);
		return query.getResultList();
	}

	public List<Sample> listAllActiveSampleByStudy(Study study) {
		Query query = entityManager.createQuery("from Sample s where s.study=:study AND s.sampleStatus!=:toBeDelete", Sample.class);
		query.setParameter("study", study);
		query.setParameter("toBeDelete", SampleStatus.TO_BE_DELETE);
		return query.getResultList();
	}
	
	public List<Sample> listAllByStatus(SampleStatus status) {
		Query query = entityManager.createQuery("from Sample s where s.sampleStatus=:status", Sample.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

}
