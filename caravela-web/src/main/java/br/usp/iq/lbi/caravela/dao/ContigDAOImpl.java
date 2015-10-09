package br.usp.iq.lbi.caravela.dao;


import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;


public class ContigDAOImpl extends DAOImpl<Contig> implements ContigDAO {

	@Inject
	public ContigDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Contig> FindByContigBySample(Sample sample, Integer maxResult) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT c FROM Contig c WHERE c.sample=:sample ORDER by length(c.sequence) DESC,  c.taxonomicIdentificationIndex DESC", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample).setMaxResults(maxResult).getResultList();
		return contigList;
	}
	
	public List<Contig> FindByContigBySampleAndGeneProductSource(Sample sample, String geneProductSource) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT c FROM Contig c WHERE c.sample=:sample ORDER by length(c.sequence) DESC,  c.taxonomicIdentificationIndex DESC", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample).getResultList();
		return contigList;
	}
	
	public List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer maxResult) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT c FROM Contig c WHERE c.sample=:sample AND c.taxonomicIdentificationIndex >:tii ORDER by length(c.sequence) DESC,  c.taxonomicIdentificationIndex DESC", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample)
				.setParameter("tii", tii)
				.setMaxResults(maxResult).getResultList();
		return contigList;
	}
}
