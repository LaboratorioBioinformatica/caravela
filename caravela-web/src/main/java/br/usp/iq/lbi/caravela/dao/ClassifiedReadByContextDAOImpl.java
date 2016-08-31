package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.usp.iq.lbi.caravela.model.ClassifiedReadByContex;
import br.usp.iq.lbi.caravela.model.Sample;

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
	
	public List<ClassifiedReadByContex> findBySample(Sample sample){
		TypedQuery<ClassifiedReadByContex> query = entityManager.createQuery("SELECT c FROM ClassifiedReadByContex c WHERE c.sample=:sample", ClassifiedReadByContex.class);
		List<ClassifiedReadByContex> classifiedReadByContexList = query.setParameter("sample", sample).getResultList();
		return classifiedReadByContexList;
		
		
	}
	
	
}
