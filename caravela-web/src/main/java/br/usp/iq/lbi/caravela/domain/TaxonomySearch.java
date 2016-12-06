package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.dto.search.TaxonCounterTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;

public interface TaxonomySearch {
	List<Contig> SearchContigBySampleTaxonomyIdAndTaxonCovarage(Sample sample, Long taxonomyId, Double taxonCovarage);
	List<Contig> SearchContigBySampleTaxonomyIdAndExclusiveTaxonCovarage(Sample sample, Long taxonomyId, Double taxonCoverage);
	List<TaxonCounterTO> searchTaxonCounterTOBySampleAndScientificName(Sample sample, String scientificName, Double taxonCovarage);
	List<TaxonCounterTO> searchTaxonCounterTOBySampleScientificNameAndExclusiveTaxonCoverage(Sample sample, String scientificName, Double taxonCoverage);
	

}
