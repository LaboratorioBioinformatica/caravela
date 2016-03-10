package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.intervalTree.IntervalTree;
import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class SampleReporterImpl implements SampleReporter {

	private static final int _100 = 100;
	@Inject private ContigDAO contigDAO;
	@Inject private ReadWrapper readWrapper;
	@Inject private ConsensusBuilding consensusBuilding;
	@Inject private SegmentsCalculator segmentsCalculator;
	
	public void reportChimericPotentialFromContig(Sample sample, Double tii, String rank) {
		
		List<Contig> contigs = contigDAO.FindByContigBySampleAndTiiGreatherThan(sample, tii, 10000);
		
		for (Contig contig : contigs) {
			System.out.println("################################################################################################");
			System.out.print("contig: " + contig.getId());
			
			List<Read> readsOnContig = contig.getReads();
			
			IntervalTree<Taxon> intervalTree = new IntervalTree<Taxon>();
			List<Segment<Taxon>> segmentsCandidatesToBeBoundaries = new ArrayList<Segment<Taxon>>(); 
			
			Map<Taxon, List<Read>> readsGroupedByTaxonMap = readWrapper.groupBy(readsOnContig, rank);
			Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readsGroupedByTaxonMap.entrySet();
			
			Map<Taxon, List<Segment<Taxon>>> segmentsConsensusMap = new HashMap<Taxon, List<Segment<Taxon>>>();
			
			Map<Taxon, Double> taxonCovarageMap = new HashMap<Taxon, Double>(); 
			
			for (Entry<Taxon, List<Read>> readsGroupedByTaxonEntry : readsGroupedByTaxon) {
				Double numberOfBaseOfPairAssignedToTaxon = 0d;
				Taxon taxonKey = readsGroupedByTaxonEntry.getKey();
				List<Read> readListValue = readsGroupedByTaxonEntry.getValue();
				List<Segment<Taxon>> taxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(readListValue, rank);
				
				if( ! taxonKey.equals(Taxon.getNOTaxon())){
					
					for (Segment<Taxon> segment : taxonSegmentsConsensus) {
						Integer x = segment.getX();
						Integer y = segment.getY();
						numberOfBaseOfPairAssignedToTaxon = (y - x) + numberOfBaseOfPairAssignedToTaxon;
						intervalTree.addInterval(x, y, taxonKey);
					}
					
					Double taxonCovarage = (numberOfBaseOfPairAssignedToTaxon * _100) / contig.getSize();
					taxonCovarageMap.put(taxonKey, taxonCovarage);
					
				}
				
				
				segmentsConsensusMap.put(taxonKey, taxonSegmentsConsensus);
			}
			
			//No taxon should not participate of undefine segments building.  
			segmentsConsensusMap.remove(Taxon.getNOTaxon());
			
			List<Segment<Taxon>> undfinedSegmentsByTaxon = segmentsCalculator.buildUndfinedSegmentsByTaxon(segmentsConsensusMap);
			List<Segment<Taxon>> undfinedSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(undfinedSegmentsByTaxon);
			
			Double numberOfBasePairAssignedToUndefined = new Double(0);
			for (Segment<Taxon> segment : undfinedSegmentsConsensus) {
				numberOfBasePairAssignedToUndefined = (segment.getY() - segment.getX()) + numberOfBasePairAssignedToUndefined; 
			}
			
			Double percentageOfContingAssignedToUndefined =  (numberOfBasePairAssignedToUndefined * 100) / contig.getSize();
			
			
			segmentsCandidatesToBeBoundaries.addAll(undfinedSegmentsConsensus);
		
			List<Read> noTaxonReadList = readsGroupedByTaxonMap.remove(Taxon.getNOTaxon());
			
			Collection<List<Read>> AllTaxonsCollections = readsGroupedByTaxonMap.values();
			List<Read> allTaxonReadList = new ArrayList<Read>();
			for (List<Read> list : AllTaxonsCollections) {
				allTaxonReadList.addAll(list);
			}
			
			List<Segment<Taxon>> allTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(allTaxonReadList, rank);
			List<Segment<Taxon>> noTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(noTaxonReadList, rank);
			List<Segment<Taxon>> unclassifiedRegions = segmentsCalculator.subtract(noTaxonSegmentsConsensus, allTaxonSegmentsConsensus);
			
			Double numberOfBasePairAssignedToUnclassified = new Double(0);
			for (Segment<Taxon> segment : unclassifiedRegions) {
				numberOfBasePairAssignedToUnclassified = (segment.getY() - segment.getX()) + numberOfBasePairAssignedToUnclassified; 
			}
			
			Double percentageOfContingAssignedToUnclassified =  (numberOfBasePairAssignedToUnclassified * 100) / contig.getSize();
			
			
			segmentsCandidatesToBeBoundaries.addAll(unclassifiedRegions);
			
			List<Segment<Taxon>> boundariesSegments = new ArrayList<Segment<Taxon>>();
			
			for (Segment<Taxon> segmentCandidatesToBeBoundary: segmentsCandidatesToBeBoundaries) {
				int coordinateStartToQuery = segmentCandidatesToBeBoundary.getX()-1;
				int coordinateEndToQuery = segmentCandidatesToBeBoundary.getY()+1;
				List<Taxon> intervalsOnLeftOfSegment = intervalTree.get(coordinateStartToQuery);
				List<Taxon> intervalsOnRightOfSegment = intervalTree.get(coordinateEndToQuery);
				
				if(intervalsOnLeftOfSegment.isEmpty() || intervalsOnRightOfSegment.isEmpty()){
					continue;
				}
				
				if( ! intervalsOnLeftOfSegment.equals(intervalsOnRightOfSegment)){
					boundariesSegments.add(segmentCandidatesToBeBoundary);
				}
				
			}
			
			System.out.println(" tii: " + contig.getTaxonomicIdentificationIndex() + 
					" boundaries: "+ boundariesSegments.size() +
					" unclissified: " + percentageOfContingAssignedToUnclassified +
					
					" undefined: " + percentageOfContingAssignedToUndefined 
					
					);
			
			Set<Taxon> keySet = taxonCovarageMap.keySet();
			for (Taxon taxon : keySet) {
				System.out.println(taxon.getScientificName() + " : " + taxonCovarageMap.get(taxon));
			}
			
			
		}
		
		

		
	}

}
