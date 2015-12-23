package br.usp.iq.lbi.caravela.dto.search;

import br.usp.iq.lbi.caravela.model.Taxon;

public class TaxonCounterTO {
	
	private final Taxon taxon;
	private final Long total;
	
	public TaxonCounterTO(Taxon taxon, Long total) {
		this.taxon = taxon;
		this.total = total;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public Long getTotal() {
		return total;
	}
	
}
