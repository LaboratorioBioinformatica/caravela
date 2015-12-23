
package br.usp.iq.lbi.caravela.dto.search;

import java.util.LinkedHashSet;
import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;

public class TaxonomomySearchTO {
	
	private final LinkedHashSet<Contig> contigs;
	private final List<GeneProductCounterTO> geneProductCounterTo;
	private final Long numberOfTaxonFound;
	private final Integer numberOfContigFound;
	
	public TaxonomomySearchTO(LinkedHashSet<Contig> contigs, List<GeneProductCounterTO> geneProductCounterTo, Long numberOfTaxonFound) {
		this.contigs = contigs;
		this.geneProductCounterTo = geneProductCounterTo;
		this.numberOfTaxonFound = numberOfTaxonFound;
		this.numberOfContigFound = contigs.size();
	}

	public LinkedHashSet<Contig> getContigs() {
		return contigs;
	}

	public List<GeneProductCounterTO> getGeneProductCounterTo() {
		return geneProductCounterTo;
	}

	public Long getNumberOfTaxonFound() {
		return numberOfTaxonFound;
	}

	public Integer getNumberOfContigFound() {
		return numberOfContigFound;
	}

}
