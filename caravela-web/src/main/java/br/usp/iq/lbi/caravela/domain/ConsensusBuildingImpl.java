package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class ConsensusBuildingImpl implements ConsensusBuilding {
	
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
		
		return listConsensus;
	}

	private boolean hasEnoughElementsToBuildConsensus(List<Segment<Taxon>> segmentTaxonList) {
		return segmentTaxonList.size() < NUMBER_ELEMENT_ENOUGH_TO_BUILD_CONSENSUS;
	}

	private List<Segment<Taxon>> createSegmentTaxonList(List<Read> readList, String rank) {
		List<Segment<Taxon>> segmentTaxonList = new ArrayList<Segment<Taxon>>();
		
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
		return segmentTaxonList;
	}

}
