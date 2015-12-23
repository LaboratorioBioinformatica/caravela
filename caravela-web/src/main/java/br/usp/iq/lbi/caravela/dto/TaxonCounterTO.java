package br.usp.iq.lbi.caravela.dto;

import br.usp.iq.lbi.caravela.model.Taxon;

public class TaxonCounterTO implements Comparable<TaxonCounterTO> {

	private Taxon taxon;
	private Integer total;

	public TaxonCounterTO(Taxon taxon) {
		this.taxon = taxon;
		this.total = 1;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public Integer getTotal() {
		return total;
	}

	public void addOne() {
		this.total++;
	}

	public int compareTo(TaxonCounterTO o) {
		if (this.total > o.getTotal()) {
			return -1;
		}
		if (this.total < o.getTotal()) {
			return 1;
		}
		return 0;
	}

}
