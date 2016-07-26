package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.TaxonOnContig;

public class TaxonOnContigDAOImpl extends DAOImpl<TaxonOnContig> implements TaxonOnContigDAO {

	@Inject
	public TaxonOnContigDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Integer removeBySample(Long sampleId){
		Query query = entityManager.createQuery("delete from TaxonOnContig tonc where tonc.sample.id=:sampleId");
		query.setParameter("sampleId", sampleId);
		return query.executeUpdate();
	}
	
	

	
}
