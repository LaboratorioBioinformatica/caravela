package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.dto.search.TaxonCounterTO;
import br.usp.iq.lbi.caravela.dto.search.TaxonomomySearchTO;
import br.usp.iq.lbi.caravela.model.Sample;

public interface TaxonomySearch {
	TaxonomomySearchTO searchTaxonomicSearchTOBySampleAndScientificName(Sample sample, String scientificName);
	List<TaxonCounterTO> searchTaxonCounterTOBySampleAndScientificName(Sample sample, String scientificName);
	

}
