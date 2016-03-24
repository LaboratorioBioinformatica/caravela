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

import br.usp.iq.lbi.caravela.controller.ContigControllerHelper;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.ReportClassifiedReadByContextDAO;
import br.usp.iq.lbi.caravela.dao.ReportContigDAO;
import br.usp.iq.lbi.caravela.dao.ReportSampleDAO;
import br.usp.iq.lbi.caravela.dao.ReportTaxonContigDAO;
import br.usp.iq.lbi.caravela.intervalTree.IntervalTree;
import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.ReportClassifiedReadByContex;
import br.usp.iq.lbi.caravela.model.ReportContig;
import br.usp.iq.lbi.caravela.model.ReportSample;
import br.usp.iq.lbi.caravela.model.ReportTaxonOnContig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class SampleReporterImpl implements SampleReporter {

	private static final int _100 = 100;
	private static final Long MAX_RECORD_PER_PAGE = 10000l;
	
	@Inject private ContigDAO contigDAO;
	@Inject private ReadWrapper readWrapper;
	@Inject private ConsensusBuilding consensusBuilding;
	@Inject private SegmentsCalculator segmentsCalculator;
	@Inject private ReportContigDAO reportContigDAO;
	@Inject private ReportTaxonContigDAO reportTaxonContigDAO;
	@Inject private Paginator paginator;
	@Inject private ContigControllerHelper contigControllerHelper;
	@Inject private ReportClassifiedReadByContextDAO reportClassifiedReadByContextDAO;
	@Inject private ReportSampleDAO reportSampleDAO;
	
	

	public void reportChimericPotentialFromContig(Sample sample, Double tii, String rank) {

		
		
		ReportSample reportSample = new ReportSample(sample, tii, rank);
		reportSampleDAO.save(reportSample);
		
		Long totalNumberOfContig = contigDAO.CountByContigBySampleAndTiiGreatherThan(sample, tii);
		
		
		
		List<IntervalPage> pages = paginator.getPages(totalNumberOfContig, MAX_RECORD_PER_PAGE);
		
		System.out.println("TOTAL NUMBER OF CONTIGS: " + totalNumberOfContig);
		
		for (IntervalPage intervalPage : pages) {
			Integer start = intervalPage.getStart();
			System.out.println("START: " + start + " MAX RESULT: " + MAX_RECORD_PER_PAGE);
			List<Contig> contigs = contigDAO.FindByContigBySampleAndTiiGreatherThan(sample, tii, start, MAX_RECORD_PER_PAGE.intValue());
			report(reportSample, rank, contigs);
			
		}
		
		System.out.println("END OF REPORT");
		
	}
				
				
		
	private void report(ReportSample reportSample, String rank, List<Contig> contigs) {
		for (Contig contig : contigs) {
//			System.out.println("################################################################################################");
//			System.out.print("contig: " + contig.getId());

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

				if (!taxonKey.equals(Taxon.getNOTaxon())) {

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

			// No taxon should not participate of undefine segments building.
			segmentsConsensusMap.remove(Taxon.getNOTaxon());

			List<Segment<Taxon>> undfinedSegmentsByTaxon = segmentsCalculator.buildUndfinedSegmentsByTaxon(segmentsConsensusMap);
			List<Segment<Taxon>> undfinedSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(undfinedSegmentsByTaxon);

			Double numberOfBasePairAssignedToUndefined = new Double(0);
			for (Segment<Taxon> segment : undfinedSegmentsConsensus) {
				numberOfBasePairAssignedToUndefined = (segment.getY() - segment.getX()) + numberOfBasePairAssignedToUndefined;
			}

			Double percentageOfContingAssignedToUndefined = (numberOfBasePairAssignedToUndefined * 100) / contig.getSize();

			segmentsCandidatesToBeBoundaries.addAll(undfinedSegmentsConsensus);

			List<Read> noTaxonReadList = readsGroupedByTaxonMap.remove(Taxon.getNOTaxon());

			Collection<List<Read>> AllTaxonsCollections = readsGroupedByTaxonMap.values();
			List<Read> allTaxonReadList = new ArrayList<Read>();
			for (List<Read> list : AllTaxonsCollections) {
				allTaxonReadList.addAll(list);
			}

			List<Segment<Taxon>> allTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(allTaxonReadList, rank);
			List<Segment<Taxon>> noTaxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(noTaxonReadList, rank);
			List<Segment<Taxon>> unclassifiedRegions = segmentsCalculator.subtract(noTaxonSegmentsConsensus,
							allTaxonSegmentsConsensus);

			Double numberOfBasePairAssignedToUnclassified = new Double(0);
			for (Segment<Taxon> segment : unclassifiedRegions) {
				numberOfBasePairAssignedToUnclassified = (segment.getY() - segment.getX()) + numberOfBasePairAssignedToUnclassified;
			}

			Double percentageOfContingAssignedToUnclassified = (numberOfBasePairAssignedToUnclassified * 100) / contig.getSize();

			segmentsCandidatesToBeBoundaries.addAll(unclassifiedRegions);

			List<Segment<Taxon>> boundariesSegments = new ArrayList<Segment<Taxon>>();

			for (Segment<Taxon> segmentCandidatesToBeBoundary : segmentsCandidatesToBeBoundaries) {
				int coordinateStartToQuery = segmentCandidatesToBeBoundary.getX() - 1;
				int coordinateEndToQuery = segmentCandidatesToBeBoundary.getY() + 1;
				List<Taxon> intervalsOnLeftOfSegment = intervalTree.get(coordinateStartToQuery);
				List<Taxon> intervalsOnRightOfSegment = intervalTree.get(coordinateEndToQuery);

				if (intervalsOnLeftOfSegment.isEmpty() || intervalsOnRightOfSegment.isEmpty()) {
					continue;
				}

				if (!intervalsOnLeftOfSegment.equals(intervalsOnRightOfSegment)) {
					boundariesSegments.add(segmentCandidatesToBeBoundary);
				}

			}

			ReportContig reportContig = new ReportContig(reportSample, contig, boundariesSegments.size(), percentageOfContingAssignedToUnclassified, percentageOfContingAssignedToUndefined);

			reportContigDAO.save(reportContig);

//			System.out.println(" tii: "
//					+ contig.getTaxonomicIdentificationIndex()
//					+ " boundaries: " + boundariesSegments.size()
//					+ " unclissified: "
//					+ percentageOfContingAssignedToUnclassified +
//
//					" undefined: " + percentageOfContingAssignedToUndefined
//
//			);

			Set<Taxon> keySet = taxonCovarageMap.keySet();
			int numberOfTaxonOnContig = keySet.size();
			for (Taxon taxon : keySet) {
				ReportTaxonOnContig reportTaxonOnContig = new ReportTaxonOnContig(reportSample, contig, taxon, taxonCovarageMap.get(taxon));
				reportTaxonContigDAO.addBatch(reportTaxonOnContig, numberOfTaxonOnContig); 
//				System.out.println(taxon.getScientificName() + " : "+ taxonCovarageMap.get(taxon));
			}
			
			Map<Taxon, List<Read>> unclassifiedReadThatCouldBeClassified = contigControllerHelper.searchbyUnclassifiedReadThatCouldBeClassified(readsOnContig, rank);
			Set<Taxon> taxons = unclassifiedReadThatCouldBeClassified.keySet();
			int size = unclassifiedReadThatCouldBeClassified.values().size();
			for (Taxon taxon : taxons) {
				List<Read> list = unclassifiedReadThatCouldBeClassified.get(taxon);
				for (Read read : list) {
					ReportClassifiedReadByContex reportClassifiedReadByContex = new ReportClassifiedReadByContex(reportSample, contig, read, taxon);
					reportClassifiedReadByContextDAO.addBatch(reportClassifiedReadByContex, size);
				}
			}
			

		}
	}

}
