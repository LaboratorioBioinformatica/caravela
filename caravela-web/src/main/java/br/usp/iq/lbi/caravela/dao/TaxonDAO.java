package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.dto.search.TaxonCounterTO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;

public interface TaxonDAO extends DAO<Taxon> {
	
	List<TaxonCounterTO> findTaxonsBySampleAndScientificName(Sample sample, String scientificName, Double taxonCovarage);
	List<TaxonCounterTO> findTaxonsBySampleAndScientificNameAndExclusiveCoverage(Sample sample, String scientificName, Double taxonCoverage);
	Long count(Sample sample, String scientificName);
	Long count();
	void truncateTaxonTable();
	List<Taxon> listAll();
	Taxon findByTaxonomicId(Long taxonomicId);
}
