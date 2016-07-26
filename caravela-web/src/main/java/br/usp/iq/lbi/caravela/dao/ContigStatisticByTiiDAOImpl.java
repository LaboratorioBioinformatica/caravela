package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.ContigStatisticByTii;

public class ContigStatisticByTiiDAOImpl extends DAOImpl<ContigStatisticByTii> implements ContigStatisticByTiiDAO {

	@Inject
	public ContigStatisticByTiiDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	public Integer removeBySample(Long sampleId){
		Query query = entityManager.createQuery("delete from ContigStatisticByTii csbytii where csbytii.sample.id=:sampleId");
		query.setParameter("sampleId", sampleId);
		return query.executeUpdate();
	}
	
}
