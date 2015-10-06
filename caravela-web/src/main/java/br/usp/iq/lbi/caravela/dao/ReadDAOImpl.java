package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;

public class ReadDAOImpl extends DAOImpl<Read> implements ReadDAO {

	@Inject
	public ReadDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Read> loadAllReadsByContig(Contig contig) {
		Query query = entityManager.createQuery("from Read r where r.contig=:contig", Read.class);
		query.setParameter("contig", contig);
		return query.getResultList();
	}
	
	public List<Read> findReadsBySampleAndScientificName(Sample sample, String scientificName) {
		Query query = entityManager.createQuery("from Read r where r.sample=:sample and r.taxon.scientificName=:scientificName GROUP BY r.contig ORDER BY  length(r.contig.sequence) DESC, r.contig.taxonomicIdentificationIndex DESC", Read.class);
		query.setParameter("sample", sample);
		query.setParameter("scientificName", scientificName);
		return query.getResultList();
	}

}
