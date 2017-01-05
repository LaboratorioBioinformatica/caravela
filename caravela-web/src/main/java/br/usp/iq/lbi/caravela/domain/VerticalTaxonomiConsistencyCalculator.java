package br.usp.iq.lbi.caravela.domain;

import java.util.Collection;

public interface VerticalTaxonomiConsistencyCalculator {
	Double calculateVTCByList(Collection<Integer> list, Integer size); 

}
