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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.controller.ContigControllerHelper;
import br.usp.iq.lbi.caravela.dao.ClassifiedReadByContextDAO;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.ContigStatisticByTiiDAO;
import br.usp.iq.lbi.caravela.dao.TaxonOnContigDAO;
import br.usp.iq.lbi.caravela.intervalTree.IntervalTree;
import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.ClassifiedReadByContex;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.ContigStatisticByTii;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.model.TaxonOnContig;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

@RequestScoped
public class SampleReporterImpl implements SampleReporter {

	private static final Double ZERO = 0d;

	private static final Logger logger = LoggerFactory.getLogger(SampleReporterImpl.class);
	
	private static final Long MAX_RECORD_PER_PAGE = 10000l;

	@Inject private ContigDAO contigDAO;
	@Inject private ReadWrapper readWrapper;
	@Inject private ConsensusBuilding consensusBuilding;
	@Inject private SegmentsCalculator segmentsCalculator;
	@Inject private ContigStatisticByTiiDAO contigStatisticByTiiDAO;
	@Inject private TaxonOnContigDAO taxonOnContigDAO;
	@Inject private Paginator paginator;
	@Inject private ContigControllerHelper contigControllerHelper;
	@Inject private ClassifiedReadByContextDAO classifiedReadByContextDAO;
	@Inject private VerticalTaxonomiConsistencyCalculator vtcc;
	
	

	public void reportChimericPotentialFromContig(Sample sample, Double tii, String rank) {

		Long totalNumberOfContig = contigDAO.CountByContigBySampleAndTiiGreatherThan(sample, tii);
		
		List<IntervalPage> pages = paginator.getPages(totalNumberOfContig, MAX_RECORD_PER_PAGE);
		
		System.out.println("TOTAL NUMBER OF CONTIGS: " + totalNumberOfContig);
		long startTime = System.currentTimeMillis();
		for (IntervalPage intervalPage : pages) {
			Integer start = intervalPage.getStart();
			System.out.println("START: " + start + " MAX RESULT: " + MAX_RECORD_PER_PAGE);
			List<Contig> contigs = contigDAO.FindByContigBySampleAndTiiGreatherThan(sample, tii, start, MAX_RECORD_PER_PAGE.intValue());
			report(sample, rank, contigs);
			
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("END OF REPORT");
		System.out.println("Total time to report: " + (endTime - startTime));
		
	}
				
				
		
	private void report(Sample sample, String rank, List<Contig> contigs) {
		for (Contig contig : contigs) {
//			System.out.println("################################################################################################");
//			System.out.print("contig: " + contig.getId());
			createReportByContig(sample, rank, contig);
			classifiedReadsByContex(sample, rank, contig);

		}
	}



	public void createReportByContig(Sample sample, String rank, Contig contig) {
		logger.info("Staring Cotig report: " + contig.getReference());
		logger.info("Number of reads on contig: " + contig.getReads().size());
		
		List<Read> readsOnContig = contig.getReads();

		IntervalTree<Taxon> intervalTree = new IntervalTree<Taxon>();
		List<Segment<Taxon>> segmentsCandidatesToBeBoundaries = new ArrayList<Segment<Taxon>>();

		Map<Taxon, List<Read>> readsGroupedByTaxonMap = readWrapper.groupBy(readsOnContig, rank);
		Set<Entry<Taxon, List<Read>>> readsGroupedByTaxon = readsGroupedByTaxonMap.entrySet();

		Map<Taxon, List<Segment<Taxon>>> segmentsConsensusMap = new HashMap<Taxon, List<Segment<Taxon>>>();

		Map<Taxon, Double> taxonCovarageMap = new HashMap<Taxon, Double>();
		Map<Taxon, Integer> numberOfReadsByTaxon = new HashMap<Taxon, Integer>();
		Double greaterNumberOfHitByTaxon = ZERO;
		
		
		for (Entry<Taxon, List<Read>> readsGroupedByTaxonEntry : readsGroupedByTaxon) {
			Double numberOfBaseOfPairAssignedToTaxon = 0d;
			Taxon taxonKey = readsGroupedByTaxonEntry.getKey();
			List<Read> readListValue = readsGroupedByTaxonEntry.getValue();
			
			
			if( ! taxonKey.equals(Taxon.getNOTaxon())){
				double numberOfReads =  readListValue.size();
				numberOfReadsByTaxon.put(taxonKey, readListValue.size());
				if(numberOfReads > greaterNumberOfHitByTaxon){
					greaterNumberOfHitByTaxon = numberOfReads;
				}
			}
			
			
			List<Segment<Taxon>> taxonSegmentsConsensus = consensusBuilding.buildSegmentsConsensus(readListValue, rank);

			if (!taxonKey.equals(Taxon.getNOTaxon())) {

				for (Segment<Taxon> segment : taxonSegmentsConsensus) {
					Integer x = segment.getX();
					Integer y = segment.getY();
					numberOfBaseOfPairAssignedToTaxon = (y - x) + numberOfBaseOfPairAssignedToTaxon;
					intervalTree.addInterval(x, y, taxonKey);
				}

				Double taxonCovarage = (numberOfBaseOfPairAssignedToTaxon / contig.getSize());
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

		Double percentageOfContingAssignedToUndefined = (numberOfBasePairAssignedToUndefined / contig.getSize());

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

		Double percentageOfContingAssignedToUnclassified = (numberOfBasePairAssignedToUnclassified / contig.getSize());

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
		
		Double indexOfConsistencyTaxonomicByCountReads = (greaterNumberOfHitByTaxon / contig.getNumberOfReads());
		
		if(indexOfConsistencyTaxonomicByCountReads.isNaN()){
			indexOfConsistencyTaxonomicByCountReads = 0d;
		}
		
//		logger.info("ivct" + indexOfVerticalConsistencyTaxonomic.toString());
//		logger.info("ictcr" + indexOfConsistencyTaxonomicByCountReads.toString());

		Map<Taxon, Integer> buildUniqueTaxonConsensus = consensusBuilding.buildUniqueTaxonConsensus(readsOnContig, rank);
		Double ctv = vtcc.calculateVTCByList(buildUniqueTaxonConsensus.values(), contig.getSize());
		
		ContigStatisticByTii reportContig = new ContigStatisticByTii(sample, contig, TaxonomicRank.valueOf(rank.toUpperCase()), boundariesSegments.size(), percentageOfContingAssignedToUnclassified, percentageOfContingAssignedToUndefined, indexOfConsistencyTaxonomicByCountReads, ctv);

		contigStatisticByTiiDAO.save(reportContig);


		
		Set<Taxon> keySet = taxonCovarageMap.keySet();
		int numberOfTaxonOnContig = keySet.size();
		for (Taxon taxon : keySet) {
			Integer numberOfUniqueBasesByTaxon = buildUniqueTaxonConsensus.get(taxon);
			Double coverageUniqueBasesByTaxon = ((double)numberOfUniqueBasesByTaxon / contig.getSize());
			// adicinoar número de reads associado a cada táxon (isso posibilita calcular outras coisa, pensar sobre isso!) Também precisa ser por rank!
			TaxonOnContig reportTaxonOnContig = new TaxonOnContig(sample, contig, TaxonomicRank.valueOf(rank.toUpperCase()), taxon, taxonCovarageMap.get(taxon), numberOfReadsByTaxon.get(taxon), numberOfUniqueBasesByTaxon, coverageUniqueBasesByTaxon);
			taxonOnContigDAO.addBatch(reportTaxonOnContig, numberOfTaxonOnContig); 
		}
	}

	public List<ClassifiedReadByContex> classifiedReadsByContex(Sample sample, String rank, Contig contig) {
		List<Read> readsOnContig = contig.getReads();
		final Map<Taxon, List<Read>> unclassifiedReadThatCouldBeClassified = contigControllerHelper.searchbyUnclassifiedReadThatCouldBeClassified(readsOnContig, rank);
		final Set<Taxon> taxons = unclassifiedReadThatCouldBeClassified.keySet();
		final int size = unclassifiedReadThatCouldBeClassified.values().size();
		
		List<ClassifiedReadByContex> readClassiedByContex = new ArrayList<>();
		
		for (final Taxon taxon : taxons) {
			final List<Read> list = unclassifiedReadThatCouldBeClassified.get(taxon);
			for (final Read read : list) {
				final ClassifiedReadByContex reportClassifiedReadByContex = new ClassifiedReadByContex(sample, contig, read, taxon);
				readClassiedByContex.add(reportClassifiedReadByContex);
				classifiedReadByContextDAO.addBatch(reportClassifiedReadByContex, size);
			}
		}
		return readClassiedByContex;
	}

}
