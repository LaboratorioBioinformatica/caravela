package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.domain.ConsensusBuilding;
import br.usp.iq.lbi.caravela.domain.ReadWrapper;
import br.usp.iq.lbi.caravela.domain.SegmentsCalculator;
import br.usp.iq.lbi.caravela.dto.featureViewer.Segment;
import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class ContigControllerHelper {
	
	@Inject
	private ReadWrapper readWrapper; 
	@Inject
	private ConsensusBuilding consensusBuilding;
	
	@Inject
	private SegmentsCalculator segmentsCalculator;

	public Map<String, List<FeatureViewerDataTO>> createReadsFeatureViwer(List<Read> readsOnContig, String rank){
		
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		Set<Entry<String, List<Read>>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank).entrySet();
		
		for (Entry<String, List<Read>> readGroup : readsGroupedByTaxon) {
			String scientificName = readGroup.getKey();
			featureViewerDataMap.put(scientificName, createFeatureViewerDataTO(readGroup, scientificName));
		}
		
		return sortMap(featureViewerDataMap, createComparatorByNumberOfSegments());
		
	}
	
	
	public Map<String, List<FeatureViewerDataTO>> createConsensusFeatureViwer(List<Read> readsOnContig, String rank){
		
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		Set<Entry<String,List<FeatureViewerDataTO>>> featureViewerDataSet = createReadsFeatureViwer(readsOnContig, rank).entrySet();
		
		for (Entry<String, List<FeatureViewerDataTO>> featureViewerDataEntity : featureViewerDataSet) {
			String key = featureViewerDataEntity.getKey();
			List<FeatureViewerDataTO> featureViewerDataTOListConsensus = consensusBuilding.buildConsensus(featureViewerDataEntity.getValue());
			featureViewerConsensusDataMap.put(key, featureViewerDataTOListConsensus);
		}
		
		return sortMap(featureViewerConsensusDataMap, createComparatorByNumberOfSegments());
		
	}
	
	public Map<String, List<FeatureViewerDataTO>> searchOverlapTaxaOnContig(List<Read> readsOnContig, String rank){

		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		Set<Entry<String,List<FeatureViewerDataTO>>> featureViewerDataSet = createReadsFeatureViwer(readsOnContig, rank).entrySet();
		
		for (Entry<String, List<FeatureViewerDataTO>> featureViewerDataEntity : featureViewerDataSet) {
			String key = featureViewerDataEntity.getKey();
			List<FeatureViewerDataTO> featureViewerDataTOListConsensus = consensusBuilding.buildConsensus(featureViewerDataEntity.getValue());
			featureViewerConsensusDataMap.put(key, featureViewerDataTOListConsensus);
		}
		
		//No taxon should not participate of undefine segments building.  
		featureViewerConsensusDataMap.remove(Taxon.NO_TAXON);
		
		List<Segment> buildUndfinedSegmentsByTaxon = segmentsCalculator.buildUndfinedSegmentsByTaxon(featureViewerConsensusDataMap);
		
		List<FeatureViewerDataTO> list = new ArrayList<FeatureViewerDataTO>();
		
		for (int i = 0; i < buildUndfinedSegmentsByTaxon.size(); i++) {
			Segment segment = buildUndfinedSegmentsByTaxon.get(i);
			String description = "";
			List<String> species = segment.getSpecies();
			if (species != null ) {
				for (String string : species) {
					description = description + "| " + string;
				}
			}
			
			list.add(new FeatureViewerDataTO(segment.getX(), segment.getY(), description, String.valueOf(i)));
		}
		
		Map<String, List<FeatureViewerDataTO>> mapResult = new HashMap<String, List<FeatureViewerDataTO>>();
		mapResult.put("Overlap taxa", list);
		
		
		return mapResult;

	}
	
	
	private List<FeatureViewerDataTO> createFeatureViewerDataTO(Entry<String, List<Read>> readsGrouped, String scientificName) {
		List<FeatureViewerDataTO> list = new ArrayList<FeatureViewerDataTO>();
		List<Read> reads = readsGrouped.getValue();
		
		for (Read read : reads) {
			list.add(new FeatureViewerDataTO(read.getStartAlignment(), read.getEndAlignment(), scientificName, read.getId().toString()));
		}
		return list;
	}
	
	
	private Map<String, List<FeatureViewerDataTO>> sortMap(Map<String, List<FeatureViewerDataTO>> unsortMap, Comparator<Map.Entry<String, List<FeatureViewerDataTO>>> comparator){
		
		List<Map.Entry<String, List<FeatureViewerDataTO>>> list = new LinkedList<Map.Entry<String,List<FeatureViewerDataTO>>>(unsortMap.entrySet());
		
		Collections.sort(list, comparator);
		
		Map<String, List<FeatureViewerDataTO>> sortedMap = new LinkedHashMap<String, List<FeatureViewerDataTO>>();
		
		for (Iterator<Map.Entry<String, List<FeatureViewerDataTO>>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, List<FeatureViewerDataTO>> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}
	
	private Comparator<Map.Entry<String, List<FeatureViewerDataTO>>> createComparatorByNumberOfSegments() {
		Comparator<Map.Entry<String, List<FeatureViewerDataTO>>> comparator = new Comparator<Map.Entry<String, List<FeatureViewerDataTO>>>() {
			public int compare(Entry<String, List<FeatureViewerDataTO>> o1, Entry<String, List<FeatureViewerDataTO>> o2) {
				//ORDER DESC
				return (o2.getValue().size() - o1.getValue().size());
			}
			
		};
		return comparator;
	}
	
	
	

}
