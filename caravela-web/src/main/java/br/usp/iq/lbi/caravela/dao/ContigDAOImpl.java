package br.usp.iq.lbi.caravela.dao;


import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;


public class ContigDAOImpl extends DAOImpl<Contig> implements ContigDAO {

	@Inject
	public ContigDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Contig> FindByContigBySample(Sample sample, Double tii, Integer numberOfFeatures, TaxonomicRank taxonomicRank, Integer numberOfBoundaries, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic, Integer firstResult, Integer maxResult) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT c FROM  ContigStatisticByTii cs INNER JOIN cs.contig c  WHERE c.sample = :sample and cs.rank = :taxonomicRank "
				+ "AND c.taxonomicIdentificationIndex >= :tii "
				+ "AND c.numberOfFeatures >= :NOF "
				+ "AND cs.boundary <= :NOB "
				+ "AND cs.indexOfConsistencyTaxonomicByCountReads >= :ictcr "
				+ "AND cs.indexOfVerticalConsistencyTaxonomic >= :ivct "
				+ "ORDER by c.size DESC", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample)
				.setParameter("tii", tii)
				.setParameter("taxonomicRank", taxonomicRank)
				.setParameter("NOF", numberOfFeatures)
				.setParameter("NOB", numberOfBoundaries)
				.setParameter("ictcr", indexOfConsistencyTaxonomicByCountReads)
				.setParameter("ivct", indexOfVerticalConsistencyTaxonomic)
				.setFirstResult(firstResult)
				.setMaxResults(maxResult).getResultList();
		return contigList;
	}
	public List<Contig> FindByContigBySample(Sample sample, Double tii, Integer numberOfFeatures, TaxonomicRank taxonomicRank, Integer numberOfBoundaries, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT c FROM  ContigStatisticByTii cs INNER JOIN cs.contig c  WHERE c.sample = :sample and cs.rank = :taxonomicRank "
				+ "AND c.taxonomicIdentificationIndex >= :tii "
				+ "AND c.numberOfFeatures >= :NOF "
				+ "AND cs.boundary <= :NOB "
				+ "AND cs.indexOfConsistencyTaxonomicByCountReads >= :ictcr "
				+ "AND cs.indexOfVerticalConsistencyTaxonomic >= :ivct "
				+ "ORDER by c.size DESC", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample)
				.setParameter("tii", tii)
				.setParameter("taxonomicRank", taxonomicRank)
				.setParameter("NOF", numberOfFeatures)
				.setParameter("NOB", numberOfBoundaries)
				.setParameter("ictcr", indexOfConsistencyTaxonomicByCountReads)
				.setParameter("ivct", indexOfVerticalConsistencyTaxonomic).getResultList();
		return contigList;
	}
	
	public List<Contig> findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonExclusiveCovarageDesc(Sample sample, Long taxonomyId, Double taxonCoverage) {
		System.out.println("coverage: " + taxonCoverage);
		Query query = entityManager.createQuery(" SELECT toc.contig FROM TaxonOnContig toc JOIN toc.taxon t WHERE toc.sample=:sample and t.id =:taxonomyId and toc.uniqueCoverage >=:taxonCoverage ORDER BY toc.uniqueCoverage DESC", Contig.class);
		query.setParameter("sample", sample);
		query.setParameter("taxonCoverage", taxonCoverage);
		query.setParameter("taxonomyId", taxonomyId);
		return query.getResultList();
	}
	
	public List<Contig> findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonCovarageDesc(Sample sample, Long taxonomyId, Double taxonCoverage) {
		System.out.println("coverage: " + taxonCoverage);
		Query query = entityManager.createQuery(" SELECT toc.contig FROM TaxonOnContig toc JOIN toc.taxon t WHERE toc.sample=:sample and t.id =:taxonomyId and toc.coverage >=:taxonCoverage ORDER BY toc.coverage DESC", Contig.class);
		query.setParameter("sample", sample);
		query.setParameter("taxonCoverage", taxonCoverage);
		query.setParameter("taxonomyId", taxonomyId);
		return query.getResultList();
	}
	
	public Contig findContigBySampleAndContigReference(Sample sample, String contigReference) {
		Query query = entityManager.createQuery("SELECT c FROM Contig c WHERE c.sample=:sample AND c.reference=:contigReference", Contig.class);
		query.setParameter("sample", sample);
		query.setParameter("contigReference", contigReference);
		return (Contig) query.getSingleResult();
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
