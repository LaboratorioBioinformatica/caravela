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

import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.model.Read;

@RequestScoped
public class ContigControllerHelper {
	
	private static final String NO_TAXON = "no taxon";
	private static final Long NO_TAXON_ID = 0L;

	public Map<String, List<FeatureViewerDataTO>> createReadsFeatureViwer(List<Read> readsOnContig, String rank){
		
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		for (Read read : readsOnContig) {
			if(read.isMapping()){
				if(read.hasTaxon()){
					
					String scientificName = read.getScientificNameByRank(rank);
					if(scientificName == null){
						
						scientificName = read.getScientificName() + "[" + read.getTaxon().getRank() + "]";
					}
					
					List<FeatureViewerDataTO> taxonList = featureViewerDataMap.get(scientificName);
					if(taxonList != null){
						taxonList.add(createFeatureViewerDataTO(read, scientificName));
					} else {
						taxonList = new ArrayList<FeatureViewerDataTO>();
						taxonList.add(createFeatureViewerDataTO(read, scientificName));
						featureViewerDataMap.put(scientificName, taxonList);
					}
					
				} else {
					List<FeatureViewerDataTO> noTaxonList = featureViewerDataMap.get(NO_TAXON);
					if(noTaxonList != null){
						noTaxonList.add(createFeatureViewerDataTO(read, NO_TAXON));
					} else {
						noTaxonList = new ArrayList<FeatureViewerDataTO>();
						noTaxonList.add(createFeatureViewerDataTO(read, NO_TAXON));
						featureViewerDataMap.put(NO_TAXON, noTaxonList);
					}
				}
			}
			
		}
	
		return sortMap(featureViewerDataMap);
		
	}
	private Map<String, List<FeatureViewerDataTO>> sortMap(Map<String, List<FeatureViewerDataTO>> unsortMap){
		
		List<Map.Entry<String, List<FeatureViewerDataTO>>> list = new LinkedList<Map.Entry<String,List<FeatureViewerDataTO>>>(unsortMap.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, List<FeatureViewerDataTO>>>() {
			public int compare(Entry<String, List<FeatureViewerDataTO>> o1, Entry<String, List<FeatureViewerDataTO>> o2) {
				//ORDER DESC
				return (o2.getValue().size() - o1.getValue().size());
			}
			
		});
		
		Map<String, List<FeatureViewerDataTO>> sortedMap = new LinkedHashMap<String, List<FeatureViewerDataTO>>();
		
		for (Iterator<Map.Entry<String, List<FeatureViewerDataTO>>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, List<FeatureViewerDataTO>> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}
	
	public Map<String, List<FeatureViewerDataTO>> createConsensusFeatureViwer(List<Read> readsOnContig, String rank){
		
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		Set<Entry<String,List<FeatureViewerDataTO>>> featureViewerDataSet = createReadsFeatureViwer(readsOnContig, rank).entrySet();
		
		for (Entry<String, List<FeatureViewerDataTO>> featureViewerDataEntity : featureViewerDataSet) {
			String key = featureViewerDataEntity.getKey();
			List<FeatureViewerDataTO> list = featureViewerDataEntity.getValue();
			Collections.sort(list);
			
			
			List<FeatureViewerDataTO> listConsensus = new ArrayList<FeatureViewerDataTO>();
			FeatureViewerDataTO current = null;
			
			
			Iterator<FeatureViewerDataTO> itList = list.iterator();
			
			while(itList.hasNext()){
				
				FeatureViewerDataTO next = itList.next();
				
				if(current == null){
					current = next;
					continue;
				}
				
				if(current.intersects(next)){
					current = new FeatureViewerDataTO(current.getX(), next.getY(), current.getDescription(), current.getId());
					
					//não existe mais elementos na lista! 
					if( ! itList.hasNext()) {
						listConsensus.add(current);
					}
					
				}  else {
					listConsensus.add(current);
					
					//não existe mais elementos na lista! 
					if( ! itList.hasNext()) {
						listConsensus.add(next);
					} else {
						current = next;
					}
					
				}
			}
			
			featureViewerConsensusDataMap.put(key, listConsensus);
		}
		
		return sortMap(featureViewerConsensusDataMap);
		
	}
	
public Map<String, List<FeatureViewerDataTO>> createBoundariesFeatureViwer(List<Read> readsOnContig, String rank){
		
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		Set<Entry<String,List<FeatureViewerDataTO>>> featureViewerDataSet = createReadsFeatureViwer(readsOnContig, rank).entrySet();
		
		for (Entry<String, List<FeatureViewerDataTO>> featureViewerDataEntity : featureViewerDataSet) {
			String key = featureViewerDataEntity.getKey();
			List<FeatureViewerDataTO> list = featureViewerDataEntity.getValue();
			Collections.sort(list);
			
			
			List<FeatureViewerDataTO> listConsensus = new ArrayList<FeatureViewerDataTO>();
			FeatureViewerDataTO current = null;
			
			
			Iterator<FeatureViewerDataTO> itList = list.iterator();
			
			while(itList.hasNext()){
				
				FeatureViewerDataTO next = itList.next();
				
				if(current == null){
					current = next;
					continue;
				}
				
				if(current.intersects(next)){
					current = new FeatureViewerDataTO(current.getX(), next.getY(), current.getDescription(), current.getId());
					
					//não existe mais elementos na lista! 
					if( ! itList.hasNext()) {
						listConsensus.add(current);
					}
					
				}  else {
					listConsensus.add(current);
					
					//não existe mais elementos na lista! 
					if( ! itList.hasNext()) {
						listConsensus.add(next);
					} else {
						current = next;
					}
					
				}
			}
			
			featureViewerConsensusDataMap.put(key, listConsensus);
		}
		
		return featureViewerConsensusDataMap;
		
	}
	
	public Map<Long, List<FeatureViewerDataTO>> createFeatureViwerDataTaxonomyKeyMapTO(List<Read> readsOnContig){
		
		Map<Long, List<FeatureViewerDataTO>> featureViewerDataMap = new HashMap<Long, List<FeatureViewerDataTO>>();
		for (Read read : readsOnContig) {
			if(read.hasTaxon()){
				
				String scientificName = read.getScientificName();
				Long taxonomyId = read.getTaxon().getTaxonomyId();
				
				List<FeatureViewerDataTO> taxonList = featureViewerDataMap.get(taxonomyId);
				if(taxonList != null){
					taxonList.add(createFeatureViewerDataTO(read, scientificName));
				} else {
					taxonList = new ArrayList<FeatureViewerDataTO>();
					taxonList.add(createFeatureViewerDataTO(read, scientificName));
					featureViewerDataMap.put(taxonomyId, taxonList);
				}
				
			} else {
				List<FeatureViewerDataTO> noTaxonList = featureViewerDataMap.get(NO_TAXON_ID);
				if(noTaxonList != null){
					noTaxonList.add(createFeatureViewerDataTO(read, NO_TAXON));
				} else {
					noTaxonList = new ArrayList<FeatureViewerDataTO>();
					noTaxonList.add(createFeatureViewerDataTO(read, NO_TAXON));
					featureViewerDataMap.put(NO_TAXON_ID, noTaxonList);
				}
			}
		}
		return featureViewerDataMap;
		
	}
	
	
	
	private FeatureViewerDataTO createFeatureViewerDataTO(Read read, String description) {
		return new FeatureViewerDataTO(read.getStartAlignment(), read.getEndAlignment(), description, read.getId().toString());
	}
	
	
	
	

}
