package br.usp.iq.lbi.caravela.domain;

import br.usp.iq.lbi.caravela.model.Taxon;

public interface NCBITaxonFinder {
	
	Taxon searchTaxonByNCBITaxonomyId(Long ncbiTaxonomyId);

}
