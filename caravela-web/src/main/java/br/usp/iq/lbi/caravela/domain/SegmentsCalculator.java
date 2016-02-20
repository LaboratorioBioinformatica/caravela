package br.usp.iq.lbi.caravela.domain;

import java.util.List;
import java.util.Map;

import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Taxon;

public interface SegmentsCalculator {
	
	List<Segment<Taxon>> buildUndfinedSegmentsByTaxon (Map<Taxon, List<Segment<Taxon>>> segmentsConsensusList);
	List<Segment<Taxon>> subtract(List<Segment<Taxon>> minuendList, List<Segment<Taxon>> subtrahendList);
	List<Segment<Taxon>> subtract(Segment<Taxon> minuend, List<Segment<Taxon>> subtrahendList);
	
	

}
