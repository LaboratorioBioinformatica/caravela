package br.usp.iq.lbi.caravela.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class OverlapBuilderImpl implements OverlapBuilder {
	
	@Inject private ReadWrapper readWrapper;
	@Inject private ConsensusBuilding consensusBuilding;
	@Inject private SegmentsCalculator segmentsCalculator;

	@Override
	public List<Segment<Taxon>> searchOverlap(List<Read> readsOnContig, String rank) {
		Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank).entrySet();
		Map<Taxon, List<Segment<Taxon>>> segmentsConsensusMap = new HashMap<Taxon, List<Segment<Taxon>>>();
		
		for (Entry<Taxon, List<Read>> readsGroupedByTaxonEntry : readsGroupedByTaxon) {
			Taxon taxonKey = readsGroupedByTaxonEntry.getKey();
			List<Read> readListValue = readsGroupedByTaxonEntry.getValue();
			List<Segment<Taxon>> taxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(readListValue, rank);
			segmentsConsensusMap.put(taxonKey, taxonSegmentsConsensus);
		}
		
		//No taxon should not participate of undefine segments building.  
		segmentsConsensusMap.remove(Taxon.getNOTaxon());
		List<Segment<Taxon>> buildUndfinedSegmentsByTaxon = segmentsCalculator.buildUndfinedSegmentsByTaxon(segmentsConsensusMap);
		return buildUndfinedSegmentsByTaxon;
	}

}
