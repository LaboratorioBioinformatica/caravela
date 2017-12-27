package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.Study;


public class StudyDAOImpl extends DAOImpl<Study> implements StudyDAO {
	
	
	@Inject
	public StudyDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	
	public List<Study> listAll() {
		return entityManager.createQuery("select t from Study t", Study.class).getResultList();
	}

	public Study load(Study study) {
		return entityManager.find(Study.class, study.getId());
	}

}
