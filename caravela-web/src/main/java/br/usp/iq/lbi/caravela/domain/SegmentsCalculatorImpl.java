package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.RequestScoped;

import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.dto.featureViewer.Segment;

@RequestScoped
public class SegmentsCalculatorImpl implements SegmentsCalculator {

	

	public List<Segment> buildUndfinedSegmentsByTaxon(Map<String, List<FeatureViewerDataTO>> featureViewerData) {

		Map<String, List<FeatureViewerDataTO>> featureViewerDatacopy = new HashMap<String, List<FeatureViewerDataTO>>();
		featureViewerDatacopy.putAll(featureViewerData);
		
		List<Segment> segments = new ArrayList<Segment>();
		
		Set<Entry<String,List<FeatureViewerDataTO>>> allFeatureViewerDataSet = featureViewerData.entrySet();
		
		
		for (Entry<String, List<FeatureViewerDataTO>> currentFeatureViewerEntry : allFeatureViewerDataSet) {
			List<FeatureViewerDataTO> currentList = currentFeatureViewerEntry.getValue();
			String currentKey = currentFeatureViewerEntry.getKey(); 
				
			featureViewerDatacopy.remove(currentKey);
			
			Set<Entry<String,List<FeatureViewerDataTO>>> targetEntrySet = featureViewerDatacopy.entrySet();
			
			for (Entry<String, List<FeatureViewerDataTO>> targetEntry : targetEntrySet) {
				List<FeatureViewerDataTO> targetList = targetEntry.getValue();
				for (FeatureViewerDataTO currentFeature : currentList) {
					for (FeatureViewerDataTO targetFeature : targetList) {
						Segment segment = currentFeature.getIntersect(targetFeature);
						if(segment != null){
							segments.add(segment);
						}
					}
				}
			}
			currentKey = currentFeatureViewerEntry.getKey();
			currentList = currentFeatureViewerEntry.getValue();
		}
		
		Collections.sort(segments);
		return segments;

	}

}
