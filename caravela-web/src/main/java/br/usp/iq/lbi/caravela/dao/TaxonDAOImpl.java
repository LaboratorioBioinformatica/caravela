package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.dto.search.TaxonCounterTO;

public class TaxonDAOImpl extends DAOImpl<Taxon> implements TaxonDAO {

	private String TAXON_TABLE_NAME = "taxon";



	@Inject
	public TaxonDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	
	
	public List<TaxonCounterTO> findTaxonsBySampleAndScientificName(Sample sample, String scientificName) {
		Query query = entityManager.createQuery(" SELECT NEW lbi.usp.br.caravela.dto.search.TaxonCounterTO(t, COUNT(t.id)) FROM Taxon t WHERE t.read.sample=:sample and t.scientificName LIKE:scientificName GROUP BY t.scientificName ORDER BY COUNT(t.id) DESC", TaxonCounterTO.class);
		query.setParameter("sample", sample);
		query.setParameter("scientificName", "%"+scientificName+"%");
		return query.getResultList();
	}
	
	//TODO essa contagem deve ser feita nos reads junto com taxon. 
	public Long count(Sample sample, String scientificName) {
		Query query = entityManager.createQuery("SELECT COUNT(t.id) FROM Taxon t WHERE t.read.sample=:sample AND t.scientificName=:scientificName", Long.class);
		query.setParameter("sample", sample)
		.setParameter("scientificName", scientificName);
		return (Long) query.getSingleResult();
	}
	
	
	public Taxon findByTaxonomicId(Long taxonomicId){
		Query query = entityManager.createQuery("SELECT t FROM Taxon t WHERE t.taxonomyId=:taxonomicId", Taxon.class);
		query.setParameter("taxonomicId", taxonomicId);
		return (Taxon) query.getSingleResult();
	}



	public void truncateTaxonTable() {
		entityManager.createNativeQuery("truncate table " + TAXON_TABLE_NAME).executeUpdate();
		
	}
	
	public List<Taxon> listAll() {
		return entityManager.createQuery("select t from taxon t", Taxon.class).getResultList();
	}


}
