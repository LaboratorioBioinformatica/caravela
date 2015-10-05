package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;

public interface TaxonomySearch {
	List<Contig> searchListOfContigBySampleAndScientificName(Sample sample, String scientificName);

}
