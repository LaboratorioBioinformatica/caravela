package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.ClassifiedReadByContex;

public class ClassifiedReadByContextDAOImpl extends DAOImpl<ClassifiedReadByContex> implements ClassifiedReadByContextDAO {

	@Inject
	public ClassifiedReadByContextDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Integer removeBySample(Long sampleId){
		Query query = entityManager.createQuery("delete from ClassifiedReadByContex crbyc where crbyc.sample.id=:sampleId");
		query.setParameter("sampleId", sampleId);
		return query.executeUpdate();
	}

}
