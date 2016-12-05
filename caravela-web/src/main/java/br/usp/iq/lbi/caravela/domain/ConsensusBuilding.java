package br.usp.iq.lbi.caravela.domain;

import java.util.List;
import java.util.Map;

import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

public interface ConsensusBuilding {
	
	List<Segment<Taxon>> buildSegmentsConsensus(List<Read> readList, String rank);
	List<Segment<Taxon>> buildSegmentsConsensus(List<Segment<Taxon>> segmentTaxonList);
	public Map<Taxon, Integer> buildUniqueTaxonConsensus(List<Read> readsOnContig, String rank);

}
