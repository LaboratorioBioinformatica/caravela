package br.usp.iq.lbi.caravela.dao;


import java.util.List;

import javassist.compiler.NoFieldException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;


public class ContigDAOImpl extends DAOImpl<Contig> implements ContigDAO {

	@Inject
	public ContigDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Contig> FindByContigBySample(Sample sample, Double tii, Integer numberOfFeatures, Integer numberOfBoundaries, Double unclassified, Double undefined, Integer firstResult, Integer maxResult) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT c FROM  ReportContig rc INNER JOIN rc.contig c  WHERE c.sample = :sample "
				+ "AND c.taxonomicIdentificationIndex >= :tii "
				+ "AND c.numberOfFeatures >= :NOF "
				+ "AND rc.boundary <= :NOB "
				+ "AND rc.unclassified <= :unclassified "
				+ "AND rc.undefined <= :undefined "
				+ "ORDER by c.size DESC", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample)
				.setParameter("tii", tii)
				.setParameter("NOF", numberOfFeatures)
				.setParameter("NOB", numberOfBoundaries)
				.setParameter("unclassified", unclassified)
				.setParameter("undefined", undefined)
				.setFirstResult(firstResult)
				.setMaxResults(maxResult).getResultList();
		return contigList;
	}
	
	
	public List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer maxResult) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT c FROM Contig c WHERE c.sample=:sample AND c.taxonomicIdentificationIndex >:tii ORDER by length(c.sequence) DESC,  c.taxonomicIdentificationIndex DESC", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample)
				.setParameter("tii", tii)
				.setMaxResults(maxResult).getResultList();
		return contigList;
	}
	
	public List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer startPosition, Integer maxResult) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT c FROM Contig c WHERE c.sample=:sample AND c.taxonomicIdentificationIndex >:tii ORDER by c.id", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample)
				.setParameter("tii", tii)
				.setFirstResult(startPosition)
				.setMaxResults(maxResult).getResultList();
		return contigList;
	}
	
	public Long CountByContigBySampleAndTiiGreatherThan(Sample sample, Double tii) {
		Query query = entityManager.createQuery("SELECT COUNT(c.id) FROM Contig c WHERE c.sample=:sample AND c.taxonomicIdentificationIndex >:tii", Long.class);
		return (Long) query.setParameter("sample", sample).setParameter("tii", tii).getSingleResult();
	}
}
