package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.domain.ColorPicker;
import br.usp.iq.lbi.caravela.domain.ConsensusBuilding;
import br.usp.iq.lbi.caravela.domain.ReadWrapper;
import br.usp.iq.lbi.caravela.domain.SegmentsCalculator;
import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.intervalTree.IntervalTree;
import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.FeatureAnnotation;
import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class ContigControllerHelper {
	
	private static final int FIRST_POSITION = 0;
	private static final String UNCLASSIFIED_REGIONS = "Unclassified";
	private static final String UNDEFINED_REGION_KEY = "Undefined";
	private static final String OVERLAP_TAXA_KEY = "Overlap taxa";
	private static final String TAXA = "Taxa";
	private static final String FEATURE = "Feature";
	private static final double ZERO = 0;
	

	private ReadWrapper readWrapper; 
	private ConsensusBuilding consensusBuilding;
	private ColorPicker colorPicker;
	private SegmentsCalculator segmentsCalculator;
	
	public ContigControllerHelper() {
	}
	
	@Inject
	public ContigControllerHelper(ReadWrapper readWrapper, ConsensusBuilding consensusBuilding, ColorPicker colorPicker, SegmentsCalculator segmentsCalculator) {
		this.readWrapper = readWrapper;
		this.consensusBuilding = consensusBuilding;
		this.colorPicker = colorPicker;
		this.segmentsCalculator = segmentsCalculator;
	}
	
	
	
	public Map<String, List<FeatureViewerDataTO>> createFeatureViwerByFeatures(List<Feature> features){
		
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		List<FeatureViewerDataTO> featureViewerToFeatureDataTOList = new ArrayList<>();
		
		for (Feature feature : features) {
			
			List<FeatureAnnotation> featureAnnotations = feature.getFeatureAnnotations();
			if(featureAnnotations != null && ! featureAnnotations.isEmpty()){
				for (FeatureAnnotation fa : featureAnnotations) {
					FeatureViewerDataTO featureViewerDataTO = new FeatureViewerDataTO(feature.getStart() + (fa.getQueryStart() -1), feature.getStart() + (fa.getQueryEnd() -1), fa.getName(), String.valueOf(fa.getId()));
					
					List<FeatureViewerDataTO> featureViewerToFeatureAnnotationDataTO = featureViewerDataMap.get(fa.getType().toString());
					if(featureViewerToFeatureAnnotationDataTO != null){
						featureViewerToFeatureAnnotationDataTO.add(featureViewerDataTO);
					} else {
						List<FeatureViewerDataTO> newfeatureViewerToFeatureAnnotationDataTO = new ArrayList<>();
						newfeatureViewerToFeatureAnnotationDataTO.add(featureViewerDataTO);
						featureViewerDataMap.put(fa.getType().toString(), newfeatureViewerToFeatureAnnotationDataTO);
					}
					
				}
			}
			
			featureViewerToFeatureDataTOList.add(new FeatureViewerDataTO(feature.getStart(), feature.getEnd(), createFeatureViewerToFeature(feature), String.valueOf(feature.getId())));
		}
		
		
		featureViewerDataMap.put(FEATURE, featureViewerToFeatureDataTOList);
		
		return featureViewerDataMap;
	}

	private String createFeatureViewerToFeature(Feature feature) {
		StringBuilder descriptionBuilder = new StringBuilder();
		descriptionBuilder.append("feature: ");
		descriptionBuilder.append(feature.getType());
		GeneProduct geneProduct = feature.getGeneProduct();
		if(geneProduct != null){
			descriptionBuilder.append(" - ").append(geneProduct.getProduct());
		}
		return descriptionBuilder.toString();
	}

	public Map<String, List<FeatureViewerDataTO>> createReadsFeatureViwer(List<Read> readsOnContig, String rank){

		Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank).entrySet();
		List<FeatureViewerDataTO> featureViewerDataTOList = new ArrayList<FeatureViewerDataTO>();
		
		for (Entry<Taxon, List<Read>> readGroup : readsGroupedByTaxon) {
			Taxon taxon = readGroup.getKey();
			String scientificName = taxon.getScientificName();
			String color = colorPicker.generateColorByTaxon(taxon);
			featureViewerDataTOList.addAll(createFeatureViewerDataTO(readGroup, scientificName, color));
			
		}
		
		
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		featureViewerDataMap.put(TAXA, featureViewerDataTOList);
		return featureViewerDataMap;
		
	}
	

	public Map<Taxon, List<Read>> searchbyUnclassifiedReadThatCouldBeClassified(List<Read> readsOnContig, String rank){
		
		Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank).entrySet();
		
		Map<Taxon, List<Segment<Taxon>>> segmentsConsensusMap = new HashMap<Taxon, List<Segment<Taxon>>>();
		
		IntervalTree<Taxon> taxonsIntervalTree = new IntervalTree<Taxon>();
		List<Read> readsGroupedByNoTaxon = new ArrayList<Read>();
		List<Read> allTaxons = new ArrayList<Read>(); 
		
		for (Entry<Taxon, List<Read>> readsGroupedByTaxonEntry : readsGroupedByTaxon) {
			Taxon taxonKey = readsGroupedByTaxonEntry.getKey();
			List<Read> readListValue = readsGroupedByTaxonEntry.getValue();
			
			if(taxonKey.equals(Taxon.getNOTaxon())){
				readsGroupedByNoTaxon.addAll(readListValue);
				
			} else {
				allTaxons.addAll(readListValue);
				List<Segment<Taxon>> taxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(readListValue, rank);
				segmentsConsensusMap.put(taxonKey, taxonSegmentsConsensus);
				
				//create taxa segments consensus tree
				for (Segment<Taxon> segment : taxonSegmentsConsensus) {
					taxonsIntervalTree.addInterval(segment.getX(), segment.getY(), taxonKey);
				}
			}
			
		}
		
// find by undefined regions
		IntervalTree<String> undefinedIntervalTree = new IntervalTree<String>();
		List<Segment<Taxon>> undfinedSegmentsByTaxon = segmentsCalculator.buildUndfinedSegmentsByTaxon(segmentsConsensusMap);
		List<Segment<Taxon>> undefinedSegmentsByTaxonConsensus = consensusBuilding.buildSegmentsConsensus(undfinedSegmentsByTaxon);
		
		//create undefined segments consensus tree
		for (Segment<Taxon> undefinedSegment : undefinedSegmentsByTaxonConsensus) {
			undefinedIntervalTree.addInterval(undefinedSegment.getX(), undefinedSegment.getY(), UNDEFINED_REGION_KEY);
		}
		
//find by unknow regions 
		IntervalTree<String> unknowIntervalTree = new IntervalTree<String>();
		
		List<Segment<Taxon>> allTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(allTaxons, rank);
		List<Segment<Taxon>> noTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(readsGroupedByNoTaxon, rank);
		List<Segment<Taxon>> justOnlyNoTaxonSegmentConsensus = segmentsCalculator.subtract(noTaxonSegmentsConsensus, allTaxonSegmentsConsensus);
		
		for (Segment<Taxon> segment : justOnlyNoTaxonSegmentConsensus) {
			unknowIntervalTree.addInterval(segment.getX(), segment.getY(), UNCLASSIFIED_REGIONS);
		}
		
		
		
		Map<Taxon, List<Read>> mapResult = new HashMap<Taxon, List<Read>>();
		
		
		for (Read noTaxonRead : readsGroupedByNoTaxon) {
			Integer startAlignment = noTaxonRead.getStartAlignment();
			Integer endAlignment = noTaxonRead.getEndAlignment();
			
			if(noIntersectUndefinedAndUnknowSegments(undefinedIntervalTree, unknowIntervalTree, startAlignment, endAlignment)){
				List<Taxon> taxonList = taxonsIntervalTree.get(startAlignment, endAlignment);
				if( ! taxonList.isEmpty()){
					Taxon taxon = taxonList.get(FIRST_POSITION);
					List<Read> readListWouldBeClassified = mapResult.get(taxon);
					if(readListWouldBeClassified != null){
						readListWouldBeClassified.add(noTaxonRead);
					} else {
						List<Read> newReadListWouldBeClassified = new ArrayList<Read>();
						newReadListWouldBeClassified.add(noTaxonRead);
						mapResult.put(taxon, newReadListWouldBeClassified);
					}
					
				}
				
			}
			
			
		}
		

		
		
		return mapResult;
	}

	private boolean noIntersectUndefinedAndUnknowSegments(IntervalTree<String> undefinedIntervalTree, IntervalTree<String> unknowIntervalTree, Integer startAlignment, Integer endAlignment) {
		return undefinedIntervalTree.get(startAlignment, endAlignment).isEmpty() && unknowIntervalTree.get(startAlignment, endAlignment).isEmpty();
	}
	
	public Double calculateIndexOfVerticalConsistencyTaxonomic(Contig contig, List<Read> readsOnContig, String rank){
		return 1 - (calculateIndexOfNOTaxon(contig, readsOnContig, rank) + calculateIndexOfConfusionTaxonomic(contig, readsOnContig, rank));
	}
	
	public Double calculateIndexOfConfusionTaxonomic(Contig contig, List<Read> readsOnContig, String rank){
		List<Segment<Taxon>> undefinedSegments  = searchOverlap(readsOnContig, rank);
		List<Segment<Taxon>> undefinedSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(undefinedSegments);
		
		double confusionTaxonomySize = 0d;
		for (Segment<Taxon> segment : undefinedSegmentsConsensus) {
			confusionTaxonomySize = (segment.getSize() + confusionTaxonomySize);
		}
		
		Double confusionTaxonomyIndex = confusionTaxonomySize / contig.getSize();
		
		return confusionTaxonomyIndex;
	}
	
	public Double calculateIndexOfConsistencyTaxonomic(Contig contig, List<Read> readsOnContig, String rank) {
		return (calculateIndexOfVerticalConsistencyTaxonomic(contig, readsOnContig, rank) * calculateIndexOfConsistencyTaxonomicByCountReads(readsOnContig, rank)) ;
	}
	
	public Double calculateIndexOfConsistencyTaxonomicByCountReads(List<Read> readsOnContig, String rank){
		double totalReadsOnContig = readsOnContig.size();
		double greaterNumberOfHitByTaxon = ZERO;
		
		Map<Taxon, List<Read>> readsOnContigMap = readWrapper.groupBy(readsOnContig, rank);
		Set<Taxon> keySet = readsOnContigMap.keySet();
		
		for (Taxon taxon : keySet) {
			if( ! Taxon.getNOTaxon().equals(taxon)){
				double numberOfHitsCurrentTaxon = readsOnContigMap.get(taxon).size();
				if(numberOfHitsCurrentTaxon > greaterNumberOfHitByTaxon){
					greaterNumberOfHitByTaxon = numberOfHitsCurrentTaxon;
				}
				
			}
		}
//		System.out.println("total of reads on contig: " + totalReadsOnContig);
//		System.out.println("greater number of hit by taxon: " + greaterNumberOfHitByTaxon);
		
		return (greaterNumberOfHitByTaxon / totalReadsOnContig);
		
		
	}
	

	public Double calculateIndexOfNOTaxon(Contig contig, List<Read> readsOnContig, String rank){
		Map<Taxon, List<Read>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank);
		
		List<Read> noTaxonReadList = readsGroupedByTaxon.remove(Taxon.getNOTaxon());
		
		Collection<List<Read>> AllTaxonsCollections = readsGroupedByTaxon.values();
		List<Read> allTaxonReadList = new ArrayList<Read>();
		for (List<Read> list : AllTaxonsCollections) {
			allTaxonReadList.addAll(list);
		}
		
		List<Segment<Taxon>> allTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(allTaxonReadList, rank);
		List<Segment<Taxon>> noTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(noTaxonReadList, rank);
		List<Segment<Taxon>> justOnlyNoTaxonSegmentConsensus = segmentsCalculator.subtract(noTaxonSegmentsConsensus, allTaxonSegmentsConsensus);
		
		double noTaxonSize = 0d;
		for (Segment<Taxon> segment : justOnlyNoTaxonSegmentConsensus) {
			noTaxonSize = (segment.getSize() + noTaxonSize);
		}
		Double noTaxonIndex = noTaxonSize / contig.getSize();
		
		return noTaxonIndex;
	}
	
	
	
	
	public Map<String, List<FeatureViewerDataTO>> createUnknowRegions(List<Read> readsOnContig, String rank){
		Map<Taxon, List<Read>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank);
		
		List<Read> noTaxonReadList = readsGroupedByTaxon.remove(Taxon.getNOTaxon());
		
		Collection<List<Read>> AllTaxonsCollections = readsGroupedByTaxon.values();
		List<Read> allTaxonReadList = new ArrayList<Read>();
		for (List<Read> list : AllTaxonsCollections) {
			allTaxonReadList.addAll(list);
		}
		
		List<Segment<Taxon>> allTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(allTaxonReadList, rank);
		List<Segment<Taxon>> noTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(noTaxonReadList, rank);
		List<Segment<Taxon>> justOnlyNoTaxonSegmentConsensus = segmentsCalculator.subtract(noTaxonSegmentsConsensus, allTaxonSegmentsConsensus);
		
		List<FeatureViewerDataTO> unknowViwerDataToListConsensus = createFeatureViewerDataTOListConsensus(justOnlyNoTaxonSegmentConsensus);
		
		Map<String, List<FeatureViewerDataTO>> allTaxonfeatureViewerConsensusDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		allTaxonfeatureViewerConsensusDataMap.put(UNCLASSIFIED_REGIONS, unknowViwerDataToListConsensus);
		return allTaxonfeatureViewerConsensusDataMap;
	}
	
	
	public Map<String, List<FeatureViewerDataTO>> createConsensusFeatureViwer(List<Read> readsOnContig, String rank){
		
		
		Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank).entrySet();
		List<FeatureViewerDataTO> featureViewerDataTOListConsensus = new ArrayList<FeatureViewerDataTO>();
		for (Entry<Taxon, List<Read>> readsGroupedByTaxonEntry : readsGroupedByTaxon) {
			Taxon taxonKey = readsGroupedByTaxonEntry.getKey();
			String color = colorPicker.generateColorByTaxon(taxonKey);
			List<Read> readListValue = readsGroupedByTaxonEntry.getValue();
			List<Segment<Taxon>> taxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(readListValue, rank);
			featureViewerDataTOListConsensus.addAll(createFeatureViewerDataTOListConsensus(taxonSegmentsConsensus,color)) ;
			
		}
		
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		featureViewerConsensusDataMap.put(TAXA, featureViewerDataTOListConsensus);
		return featureViewerConsensusDataMap;
		
	}
	
	public Map<Taxon, Integer> createUniqueTaxonConsensus(Contig contig, List<Read> readsOnContig, String rank){
		
		List<Segment<Taxon>> undefinedSegments  = searchOverlap(readsOnContig, rank);
		List<Segment<Taxon>> undefinedSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(undefinedSegments);
		
		Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readWrapper.groupBy(readsOnContig, rank).entrySet();
		Map<Taxon, Integer> taxonSegmentListMap = new HashMap<Taxon, Integer>();
		for (Entry<Taxon, List<Read>> readsGroupedByTaxonEntry : readsGroupedByTaxon) {
			Taxon taxonKey = readsGroupedByTaxonEntry.getKey();
			
			if( ! Taxon.getNOTaxon().equals(taxonKey)){
				List<Read> readListValue = readsGroupedByTaxonEntry.getValue();
				List<Segment<Taxon>> taxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(readListValue, rank);
				
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
	
	private List<FeatureViewerDataTO> createFeatureViewerDataTOListConsensus(List<Segment<Taxon>> taxonSegmentsConsensus) {
		List<FeatureViewerDataTO> featureViewerDataTOListConsensus = new ArrayList<FeatureViewerDataTO>();
		for (Segment<Taxon> segment : taxonSegmentsConsensus) {
			FeatureViewerDataTO featureViewerDataTO = new FeatureViewerDataTO(segment.getX(), segment.getY(), createFeatureViewerDescription(segment.getData()), createFeatureViewerId(segment.getData()));
			featureViewerDataTOListConsensus.add(featureViewerDataTO);
		}
		return featureViewerDataTOListConsensus;
	}
	
	private List<FeatureViewerDataTO> createFeatureViewerDataTOListConsensus(List<Segment<Taxon>> taxonSegmentsConsensus, String color) {
		List<FeatureViewerDataTO> featureViewerDataTOListConsensus = new ArrayList<FeatureViewerDataTO>();
		for (Segment<Taxon> segment : taxonSegmentsConsensus) {
			FeatureViewerDataTO featureViewerDataTO = new FeatureViewerDataTO(segment.getX(), segment.getY(), createFeatureViewerDescription(segment.getData()), createFeatureViewerId(segment.getData()), color);
			featureViewerDataTOListConsensus.add(featureViewerDataTO);
		}
		return featureViewerDataTOListConsensus;
	}


	public Map<String, List<FeatureViewerDataTO>> undefinedRegions(List<Read> readsOnContig, String rank){
		
		List<Segment<Taxon>> undefinedSegments  = searchOverlap(readsOnContig, rank);
		
		List<Segment<Taxon>> undefinedSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(undefinedSegments);
		
		List<FeatureViewerDataTO> undefinedFeatureViewerDataTOConsensus = createFeatureViewerDataTOListConsensus(undefinedSegmentsConsensus);
		
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		featureViewerConsensusDataMap.put(UNDEFINED_REGION_KEY, undefinedFeatureViewerDataTOConsensus);
		return featureViewerConsensusDataMap;
		
	}
	
	public Map<String, List<FeatureViewerDataTO>> boundariesRegions(List<Read> readsOnContig, String rank){
		IntervalTree<Taxon> taxonsIntervalTree = new IntervalTree<Taxon>();
		List<Segment<Taxon>> segmentsCandidatesToBeBoundaries = new ArrayList<Segment<Taxon>>(); 
		
		Map<Taxon, List<Read>> readsGroupedByTaxonMap = readWrapper.groupBy(readsOnContig, rank);
		Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readsGroupedByTaxonMap.entrySet();
		
		Map<Taxon, List<Segment<Taxon>>> segmentsConsensusMap = new HashMap<Taxon, List<Segment<Taxon>>>();
		for (Entry<Taxon, List<Read>> readsGroupedByTaxonEntry : readsGroupedByTaxon) {
			Taxon taxonKey = readsGroupedByTaxonEntry.getKey();
			List<Read> readListValue = readsGroupedByTaxonEntry.getValue();
			List<Segment<Taxon>> taxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(readListValue, rank);
			
			if( ! taxonKey.equals(Taxon.getNOTaxon())){
				
				for (Segment<Taxon> segment : taxonSegmentsConsensus) {
					taxonsIntervalTree.addInterval(segment.getX(), segment.getY(), taxonKey);
				}
			}
			
			segmentsConsensusMap.put(taxonKey, taxonSegmentsConsensus);
		}
		
		//No taxon should not participate of undefine segments building.  
		segmentsConsensusMap.remove(Taxon.getNOTaxon());
		
		List<Segment<Taxon>> undfinedSegmentsByTaxon = segmentsCalculator.buildUndfinedSegmentsByTaxon(segmentsConsensusMap);
		List<Segment<Taxon>> undfinedSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(undfinedSegmentsByTaxon);
		
		segmentsCandidatesToBeBoundaries.addAll(undfinedSegmentsConsensus);
	
		List<Read> noTaxonReadList = readsGroupedByTaxonMap.remove(Taxon.getNOTaxon());
		
		Collection<List<Read>> AllTaxonsCollections = readsGroupedByTaxonMap.values();
		List<Read> allTaxonReadList = new ArrayList<Read>();
		for (List<Read> list : AllTaxonsCollections) {
			allTaxonReadList.addAll(list);
		}
		
		List<Segment<Taxon>> allTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(allTaxonReadList, rank);
		List<Segment<Taxon>> noTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(noTaxonReadList, rank);
		List<Segment<Taxon>> unclussifiedRegions = segmentsCalculator.subtract(noTaxonSegmentsConsensus, allTaxonSegmentsConsensus);
		segmentsCandidatesToBeBoundaries.addAll(unclussifiedRegions);
		
		List<FeatureViewerDataTO> featureViewerDataTOList = new ArrayList<FeatureViewerDataTO>();
		
		
		for (Segment<Taxon> segmentCandidatesToBeBoundary: segmentsCandidatesToBeBoundaries) {
			int coordinateStartToQuery = segmentCandidatesToBeBoundary.getX()-1;
			int coordinateEndToQuery = segmentCandidatesToBeBoundary.getY()+1;
			List<Taxon> intervalsOnLeftOfSegment = taxonsIntervalTree.get(coordinateStartToQuery);
			List<Taxon> intervalsOnRightOfSegment = taxonsIntervalTree.get(coordinateEndToQuery);
			
			if(intervalsOnLeftOfSegment.isEmpty() || intervalsOnRightOfSegment.isEmpty()){
				continue;
			}
			
			if( ! intervalsOnLeftOfSegment.equals(intervalsOnRightOfSegment)){
				featureViewerDataTOList.add(new FeatureViewerDataTO(segmentCandidatesToBeBoundary.getX(), segmentCandidatesToBeBoundary.getY(), intervalsOnLeftOfSegment +":"+intervalsOnRightOfSegment, "boundary"));
			}
			
		}
		
		Map<String, List<FeatureViewerDataTO>> featureViewerBoundariesDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		featureViewerBoundariesDataMap.put("boundaries", featureViewerDataTOList);

		return featureViewerBoundariesDataMap;
		
	}

	
	public Map<String, List<FeatureViewerDataTO>> searchOverlapTaxaOnContig(List<Read> readsOnContig, String rank){
		List<Segment<Taxon>> overlapSegmentsByTaxon = searchOverlap(readsOnContig, rank);
		List<FeatureViewerDataTO> overlapFeatureViewerDataTOConsensus = createFeatureViewerDataTOListConsensus(overlapSegmentsByTaxon);
		
		Map<String, List<FeatureViewerDataTO>> overlapFeatureViewerDataToConsensusMap = new HashMap<String, List<FeatureViewerDataTO>>();
		overlapFeatureViewerDataToConsensusMap.put(OVERLAP_TAXA_KEY, overlapFeatureViewerDataTOConsensus);
		
		return overlapFeatureViewerDataToConsensusMap;

	}
	
	

	private List<Segment<Taxon>> searchOverlap(List<Read> readsOnContig, String rank) {
		
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
	
	
	private List<FeatureViewerDataTO> createFeatureViewerDataTO(Entry<Taxon, List<Read>> readsGrouped, String scientificName, String color) {
		List<FeatureViewerDataTO> list = new ArrayList<FeatureViewerDataTO>();
		List<Read> reads = readsGrouped.getValue();
		 
		for (Read read : reads) {
			list.add(new FeatureViewerDataTO(read.getStartAlignment(), read.getEndAlignment(), createReadDescriptionTOViewer(scientificName), read.getId().toString(), color));
		}
		return list;
	}

	private String createReadDescriptionTOViewer(String scientificName) {
		// read on description is usage to viewer..
		return "read: " + scientificName;
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
	
	private String createFeatureViewerId(List<Taxon> taxonList) {
		String id = "no id";
		if(taxonList.size() == 1){
			Taxon taxon = taxonList.get(0);
			if(taxon != null && taxon.getId() != null){
				id = taxon.getId().toString();
			}
		}
		return id;
		
	}


	private String createFeatureViewerDescription(List<Taxon> taxonList) {
		String description = "";
		Set<String> descriptions = new HashSet<String>();
		for (Taxon taxon : taxonList) {
			descriptions.add(taxon.getScientificName());
		}
		for (String string : descriptions) {
			description = description + " | " + string;
		}
		return description;
	}

	
	

}
