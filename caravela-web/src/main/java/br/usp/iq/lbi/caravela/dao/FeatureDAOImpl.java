package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Sample;

public class FeatureDAOImpl extends DAOImpl<Feature> implements FeatureDAO {

	@Inject
	public FeatureDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Feature> loadAllFeaturesByContig(Contig contig) {
		Query query = entityManager.createQuery("from Feature f where f.contig=:contig", Feature.class);
		query.setParameter("contig", contig);
		return query.getResultList();
	}
	
	public List<Contig> FindBySampleAndGeneProductSource(Sample sample, String geneProductSource) {
		TypedQuery<Contig> query = entityManager.createQuery("SELECT DISTINCT c FROM Feature f INNER JOIN f.contig c  WHERE c.sample=:sample and f.geneProduct.source=:geneProductSource", Contig.class);
		List<Contig> contigList = query.setParameter("sample", sample)
				.setParameter("geneProductSource", geneProductSource).getResultList();
		return contigList;
	}
	

}
