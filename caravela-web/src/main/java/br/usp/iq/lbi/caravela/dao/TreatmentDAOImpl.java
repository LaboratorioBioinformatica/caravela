package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.Treatment;


public class TreatmentDAOImpl extends DAOImpl<Treatment> implements TreatmentDAO {
	
	
	@Inject
	public TreatmentDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	
	public List<Treatment> listAll() {
		return entityManager.createQuery("select t from Treatment t", Treatment.class).getResultList();
	}

	public Treatment load(Treatment treatment) {
		return entityManager.find(Treatment.class, treatment.getId());
	}

}
