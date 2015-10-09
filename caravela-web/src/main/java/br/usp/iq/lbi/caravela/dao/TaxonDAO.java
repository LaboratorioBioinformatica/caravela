package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import lbi.usp.br.caravela.dto.search.TaxonCounterTO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;

public interface TaxonDAO extends DAO<Taxon> {
	
	List<TaxonCounterTO> findTaxonsBySampleAndScientificName(Sample sample, String scientificName);
	Long count(Sample sample, String scientificName);
}
