package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
public class ConsensusBuildingImpl implements ConsensusBuilding {
	
	@Inject private OverlapBuilder overlapBuilder;
	@Inject private ReadWrapper readWrapper;
	@Inject private SegmentsCalculator segmentsCalculator;
	
	private static final int NUMBER_ELEMENT_ENOUGH_TO_BUILD_CONSENSUS = 2;

	public List<Segment<Taxon>> buildSegmentsConsensus(List<Read> readList, String rank) {
		
		List<Segment<Taxon>> segmentTaxonList = createSegmentTaxonList(readList, rank);
		
		return buildSegmentsConsensus(segmentTaxonList);
	}

	public List<Segment<Taxon>> buildSegmentsConsensus(List<Segment<Taxon>> segmentTaxonList) {
		
		if(hasEnoughElementsToBuildConsensus(segmentTaxonList)){
			return segmentTaxonList;
		}
		
		Collections.sort(segmentTaxonList);
	
		List<Segment<Taxon>> listConsensus = new ArrayList<Segment<Taxon>>();
		Segment<Taxon> current = null;
		
		Iterator<Segment<Taxon>> segmentIt = segmentTaxonList.iterator();
		
		while(segmentIt.hasNext()){
	
			Segment<Taxon> next = segmentIt.next();
			
			if(current == null){
				current = next;
				continue;
			}
			
			Segment<Taxon> joinedSegments = current.union(next);
			
			if(joinedSegments != null ){
				current = joinedSegments;
			} else {
				listConsensus.add(current);
				current = next;
			}
			//não existe mais elementos na lista! 
			if( ! segmentIt.hasNext()) {
				listConsensus.add(current);
			}
		}
		Collections.sort(listConsensus);
		return listConsensus;
	}

	private boolean hasEnoughElementsToBuildConsensus(List<Segment<Taxon>> segmentTaxonList) {
		return segmentTaxonList.size() < NUMBER_ELEMENT_ENOUGH_TO_BUILD_CONSENSUS;
	}

	private List<Segment<Taxon>> createSegmentTaxonList(List<Read> readList, String rank) {
		List<Segment<Taxon>> segmentTaxonList = new ArrayList<Segment<Taxon>>();
		
		if(readList != null){
			for (Read read : readList) {
				Taxon taxon = read.getTaxonByRank(rank);
				if(taxon == null) {
					taxon = read.getTaxon();
					if(taxon == null){
						taxon = Taxon.getNOTaxon();
					}
				} 
				
				List<Taxon> dataList = new ArrayList<Taxon>();
				dataList.add(taxon);
				
				segmentTaxonList.add(new Segment<Taxon>(read.getStartAlignment(), read.getEndAlignment(), dataList));
				
			}
		}
		
		return segmentTaxonList;
	}
	
	public Map<Taxon, Integer> buildUniqueTaxonConsensus(List<Read> readsOnContig, String rank){
		
		List<Segment<Taxon>> undefinedSegments  = overlapBuilder.searchOverlap(readsOnContig, rank);
		List<Segment<Taxon>> undefinedSegmentsConsensus = buildSegmentsConsensus(undefinedSegments);
		
		Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank).entrySet();
		Map<Taxon, Integer> taxonSegmentListMap = new HashMap<Taxon, Integer>();
		for (Entry<Taxon, List<Read>> readsGroupedByTaxonEntry : readsGroupedByTaxon) {
			Taxon taxonKey = readsGroupedByTaxonEntry.getKey();
			
			if( ! Taxon.getNOTaxon().equals(taxonKey)){
				List<Read> readListValue = readsGroupedByTaxonEntry.getValue();
				List<Segment<Taxon>> taxonSegmentsConsensus = buildSegmentsConsensus(readListValue, rank);
				
				List<Segment<Taxon>> subtract = segmentsCalculator.subtract(taxonSegmentsConsensus, undefinedSegmentsConsensus);
				Integer totalSegmentSize = 0;
				for (Segment<Taxon> segment : subtract) {
					totalSegmentSize = (segment.getSize() + totalSegmentSize);
				}
				
				
				taxonSegmentListMap.put(taxonKey, totalSegmentSize);
			
			}
		}
		return taxonSegmentListMap;
		
	}	

}
