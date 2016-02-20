package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.RequestScoped;

import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class SegmentsCalculatorImpl implements SegmentsCalculator {

	public List<Segment<Taxon>> buildUndfinedSegmentsByTaxon(Map<Taxon, List<Segment<Taxon>>> segmentsConsensusList) {

		Map<Taxon, List<Segment<Taxon>>> segmentsConsensusListCopy = new HashMap<Taxon, List<Segment<Taxon>>>();

		segmentsConsensusListCopy.putAll(segmentsConsensusList);

		List<Segment<Taxon>> segments = new ArrayList<Segment<Taxon>>();

		Set<Entry<Taxon, List<Segment<Taxon>>>> allFeatureViewerDataSet = segmentsConsensusList.entrySet();

		for (Entry<Taxon, List<Segment<Taxon>>> currentSegmentConsensusEntry : allFeatureViewerDataSet) {
			List<Segment<Taxon>> currentList = currentSegmentConsensusEntry.getValue();
			Taxon currentKey = currentSegmentConsensusEntry.getKey();

			segmentsConsensusListCopy.remove(currentKey);

			Set<Entry<Taxon, List<Segment<Taxon>>>> targetEntrySet = segmentsConsensusListCopy.entrySet();

			for (Entry<Taxon, List<Segment<Taxon>>> targetEntry : targetEntrySet) {
				List<Segment<Taxon>> targetList = targetEntry.getValue();
				for (Segment<Taxon> currentSegment : currentList) {
					for (Segment<Taxon> targetFeature : targetList) {
						Segment<Taxon> segment = currentSegment.getIntersect(targetFeature);
						if (segment != null) {
							segments.add(segment);
						}
					}
				}
			}
			currentKey = currentSegmentConsensusEntry.getKey();
			currentList = currentSegmentConsensusEntry.getValue();
		}

		Collections.sort(segments);
		return segments;

	}

	public List<Segment<Taxon>> subtract(List<Segment<Taxon>> minuendList, List<Segment<Taxon>> subtrahendList) {
		List<Segment<Taxon>> resultList = new ArrayList<Segment<Taxon>>();
		
		for (Segment<Taxon> minuend : minuendList) {
			List<Segment<Taxon>> subtractResult = subtract(minuend, subtrahendList);
			resultList.addAll(subtractResult);
		}

		return resultList;
	}
	
	public List<Segment<Taxon>> subtract(Segment<Taxon> minuend, List<Segment<Taxon>> subtrahendList) {
		
		List<Segment<Taxon>> resultList = new ArrayList<Segment<Taxon>>();
		
		if(subtrahendList.isEmpty()){
			resultList.add(minuend);
		}
		
		for (int i = 0; i < subtrahendList.size(); i++) {
			Segment<Taxon> subtrahend = subtrahendList.get(i);
			
			if(subtrahend.contains(minuend)){
				break;
			}
			
			List<Segment<Taxon>> subtractResult = minuend.subtract(subtrahend);
			
			if( subtractResult.isEmpty() && lastElement(subtrahendList, i)) {
				resultList.add(minuend);
			} 
			
			if( ! subtractResult.isEmpty()) {
				for (Segment<Taxon> segment : subtractResult) {
					List<Segment<Taxon>> subtract = subtract(segment, subtrahendList);
					resultList.addAll(subtract);
				}
				break;
			}
			
		}
		return resultList;
		
	}

	private boolean lastElement(List<Segment<Taxon>> subtrahendList, int i) {
		return ! (i+1 < subtrahendList.size());
	}


}
