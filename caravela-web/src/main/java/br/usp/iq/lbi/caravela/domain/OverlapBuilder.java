package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

public interface OverlapBuilder {
	
	List<Segment<Taxon>> searchOverlap(List<Read> readsOnContig, String rank);

}
