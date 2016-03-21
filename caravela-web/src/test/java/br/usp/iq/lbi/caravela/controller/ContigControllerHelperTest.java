package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.usp.iq.lbi.caravela.domain.ConsensusBuilding;
import br.usp.iq.lbi.caravela.domain.ReadWrapper;
import br.usp.iq.lbi.caravela.domain.SegmentsCalculator;
import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Mapping;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.model.TaxonomicAssignment;
import br.usp.iq.lbi.caravela.model.Treatment;


public class ContigControllerHelperTest {
	
	private static final Integer FIRST = 1;
	private static final Integer FLAG_ALIGNMENT = 83;
	private static final Integer SECOND = 2;
	private static final Taxon ARCOBACTER = new Taxon(28196l, 72294l, "Arcobacter", "genus");
	
	private ContigControllerHelper target;
	
	@Mock private ReadWrapper readWrapperMock;
	@Mock private ConsensusBuilding consensusBuildingMock;
	@Mock private SegmentsCalculator segmentsCalculatorMock;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		target = new ContigControllerHelper(readWrapperMock, consensusBuildingMock, null, segmentsCalculatorMock);

	}

	@Test
		public void testSearchbyUnclassifiedReadThatCouldBeClassified() throws Exception {
			
			Treatment treatment = new Treatment("ZC4","Zoo Composting 4");
			Contig contig = new Contig();
			Sample sample = new Sample(treatment, SampleStatus.PROCCESSED, "zc4 day 01","day 01 from zc4");
			
			
			
			List<Read> readsOnContig = createListOfReadsOnContig(contig, sample);
			String rank = "genus";
			
			Map<Taxon, List<Read>> resultMapToGroupByFromReadWrapper = createResultMapToGroupByFromReadWrapper(sample, contig);
			
			List<Read> allTaxons = new ArrayList<Read>();
			allTaxons.addAll(createListOfReadsAssignedToArcobacter(sample, contig));
			
			List<Segment<Taxon>> allTaxonSegmentsConsensus = createResultListArcobacterSegmentConsensus();
			List<Segment<Taxon>> noTaxonSegmentsConsensus = createResultListNoTaxonSegmentConsensus();
			
			List<Segment<Taxon>> onlyNoTaxonSegmentConsensusResult = createResultListjustOnlyNoTaxonSegmentConsensus();
			
			List<Read> noTaxons = new ArrayList<Read>();
			noTaxons.addAll(createListOfReadsAssignedToNOTaxon(sample, contig));
			
			
			List<Segment<Taxon>> resultListArcobacterSegmentConsensus  = createResultListArcobacterSegmentConsensus();
			List<Segment<Taxon>> resultListAllTaxonSegmentConsensus = resultListArcobacterSegmentConsensus;
			List<Segment<Taxon>> resultListNoTaxonSegmentConsensus = createResultListNoTaxonSegmentConsensus();
	
			Map<Taxon, List<Segment<Taxon>>> taxonSegmentsConsensusResultList = new HashMap<Taxon, List<Segment<Taxon>>>();
			taxonSegmentsConsensusResultList.put(ARCOBACTER, resultListArcobacterSegmentConsensus);
			List<Segment<Taxon>> undfinedSegmentsByTaxonResult = new ArrayList<Segment<Taxon>>();
			
			
			Mockito.when(readWrapperMock.groupBy(readsOnContig, rank)).thenReturn(resultMapToGroupByFromReadWrapper);
			
			Mockito.when(consensusBuildingMock.buildSegmentsConsensus(createListOfReadsAssignedToArcobacter(sample, contig), rank)).thenReturn(resultListArcobacterSegmentConsensus);
			Mockito.when(consensusBuildingMock.buildSegmentsConsensus(createListOfReadsAssignedToNOTaxon(sample, contig), rank)).thenReturn(resultListNoTaxonSegmentConsensus);
			Mockito.when(segmentsCalculatorMock.buildUndfinedSegmentsByTaxon(taxonSegmentsConsensusResultList)).thenReturn(undfinedSegmentsByTaxonResult);
			Mockito.when(consensusBuildingMock.buildSegmentsConsensus(undfinedSegmentsByTaxonResult)).thenReturn(undfinedSegmentsByTaxonResult);
			Mockito.when(consensusBuildingMock.buildSegmentsConsensus(allTaxons, rank)).thenReturn(resultListAllTaxonSegmentConsensus);
			Mockito.when(consensusBuildingMock.buildSegmentsConsensus(noTaxons, rank)).thenReturn(resultListNoTaxonSegmentConsensus);
			Mockito.when(segmentsCalculatorMock.subtract(noTaxonSegmentsConsensus, allTaxonSegmentsConsensus)).thenReturn(onlyNoTaxonSegmentConsensusResult);
			
			
			Map<Taxon, List<Read>> expectedMapResult = createExpectedMapResult(contig, sample);
			
			Map<Taxon, List<Read>> MapResult = target.searchbyUnclassifiedReadThatCouldBeClassified(readsOnContig, rank);
			
			Assert.assertEquals(expectedMapResult, MapResult);
			
			Mockito.verify(readWrapperMock, Mockito.atLeastOnce()).groupBy(readsOnContig, rank);
			
			
		}


	private List<Segment<Taxon>> createResultListArcobacterSegmentConsensus() {
		ArrayList<Segment<Taxon>> list = new ArrayList<Segment<Taxon>>();
		ArrayList<Taxon> arcobacterTaxonList = new ArrayList<Taxon>();
		arcobacterTaxonList.add(ARCOBACTER);
		
		list.add(new Segment<Taxon>(1, 146, arcobacterTaxonList));
		list.add(new Segment<Taxon>(150, 728, arcobacterTaxonList));
		
		return list;
	}
	
	private List<Segment<Taxon>> createResultListNoTaxonSegmentConsensus() {
		ArrayList<Segment<Taxon>> list = new ArrayList<Segment<Taxon>>();
		
		ArrayList<Taxon> noTaxonList = new ArrayList<Taxon>();
		noTaxonList.add(Taxon.getNOTaxon());
		
		list.add(new Segment<Taxon>(83, 175, noTaxonList));
		list.add(new Segment<Taxon>(406, 595, noTaxonList));
		list.add(new Segment<Taxon>(646, 595, noTaxonList));
		
		return list;
	}

	
	private List<Segment<Taxon>> createResultListjustOnlyNoTaxonSegmentConsensus() {
		ArrayList<Segment<Taxon>> list = new ArrayList<Segment<Taxon>>();
		
		ArrayList<Taxon> noTaxonList = new ArrayList<Taxon>();
		noTaxonList.add(Taxon.getNOTaxon());
		
		list.add(new Segment<Taxon>(147, 149, noTaxonList));
		
		return list;
	}

	
	private HashMap<Taxon, List<Read>> createResultMapToGroupByFromReadWrapper(Sample sample, Contig contig) {
		HashMap<Taxon, List<Read>> result = new HashMap<Taxon, List<Read>>();
		
		List<Read> listOfReadsAssignedToArcobacter = createListOfReadsAssignedToArcobacter(sample, contig);
		List<Read> listOfReadsAssignedToNOTaxon = createListOfReadsAssignedToNOTaxon(sample, contig);
		
		result.put(ARCOBACTER, listOfReadsAssignedToArcobacter);
		result.put(Taxon.getNOTaxon(), listOfReadsAssignedToNOTaxon);
		return result;
	}

	private Map<Taxon, List<Read>> createExpectedMapResult(Contig contig, Sample sample) {
		List<Read>  reads = new ArrayList<Read>();
		
		TaxonomicAssignment arcobacterTaxonomicAssignment = new TaxonomicAssignment(ARCOBACTER, 1d);
		
		Read readFirstPair406To595NoTaxon = createRead("A41BV:1:1114:11512:26655", sample, contig, "AACCTGTAAACACTTAAAAAACCTTTAGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAA", FIRST, new Mapping(406, 595, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair406To595NoTaxon = createRead("A41BV:1:1114:11512:26655", sample, contig, "AACCTGTAAACACTTAAAAAACCTTTAGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAA", SECOND, new Mapping(406, 595, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readFirstPair408To574NoTaxon = createRead("A41BV:1:2101:11746:13368", sample, contig, "AGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAATACT", FIRST, new Mapping(408, 574, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair408To574NoTaxon = createRead("A41BV:1:2101:11746:13368", sample, contig, "AGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAATACT", SECOND, new Mapping(408, 574, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readFirstPair646To724NoTaxon = createRead("A41BV:1:2112:4536:19347", sample, contig, "GCTAGTTGTAATCCAGCAATTGGTGGTTTAGCAAAAGGACATCTTGTAAAAGAGCTTGATGCTCTTGG", FIRST, new Mapping(646, 724, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair646To724NoTaxon = createRead("A41BV:1:2112:4536:19347", sample, contig, "GCTAGTTGTAATCCAGCAATTGGTGGTTTAGCAAAAGGACATCTTGTAAAAGAGCTTGATGCTCTTGG", SECOND, new Mapping(646, 724, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		
		
		reads.add(readFirstPair406To595NoTaxon);
		reads.add(readSecPair406To595NoTaxon);
		reads.add(readFirstPair408To574NoTaxon);
		reads.add(readSecPair408To574NoTaxon);
		reads.add(readFirstPair646To724NoTaxon);
		reads.add(readSecPair646To724NoTaxon);
		
		HashMap<Taxon, List<Read>> expectedResultMap = new HashMap<Taxon, List<Read>>();
		expectedResultMap.put(ARCOBACTER, reads);
		return expectedResultMap;
	}

	private List<Read> createListOfReadsOnContig(Contig contig, Sample sample) {
		List<Read>  reads = new ArrayList<Read>();
		
		reads.addAll(createListOfReadsAssignedToArcobacter(sample, contig));
		reads.addAll(createListOfReadsAssignedToNOTaxon(sample, contig));
				
		return reads;
	}
	
	private List<Read> createListOfReadsAssignedToNOTaxon(Sample sample, Contig contig){
		
		List<Read>  reads = new ArrayList<Read>();
	
		Taxon noTaxon = Taxon.getNOTaxon();
		TaxonomicAssignment noTaxonTaxonomicAssignment = new TaxonomicAssignment(noTaxon, 1d);
		
		Read readFirstPair83To175NoTaxon = createRead("A41BV:1:1110:16554:9710", sample, contig, "ATCTATTGAATTACCATTTTTATCTATTTTTTCAACTACTCCAACACAATCAATATGT", FIRST, new Mapping(83, 175, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readSecPair83To175NoTaxon = createRead("A41BV:1:1110:16554:9710", sample, contig, "ATCTATTGAATTACCATTTTTATCTATTTTTTCAACTACTCCAACACAATCAATATGT", SECOND, new Mapping(83, 175, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);

		Read readFirstPair406To595NoTaxon = createRead("A41BV:1:1114:11512:26655", sample, contig, "AACCTGTAAACACTTAAAAAACCTTTAGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAA", FIRST, new Mapping(406, 595, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readSecPair406To595NoTaxon = createRead("A41BV:1:1114:11512:26655", sample, contig, "AACCTGTAAACACTTAAAAAACCTTTAGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAA", SECOND, new Mapping(406, 595, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readFirstPair408To574NoTaxon = createRead("A41BV:1:2101:11746:13368", sample, contig, "AGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAATACT", FIRST, new Mapping(408, 574, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readSecPair408To574NoTaxon = createRead("A41BV:1:2101:11746:13368", sample, contig, "AGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAATACT", SECOND, new Mapping(408, 574, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readFirstPair646To724NoTaxon = createRead("A41BV:1:2112:4536:19347", sample, contig, "GCTAGTTGTAATCCAGCAATTGGTGGTTTAGCAAAAGGACATCTTGTAAAAGAGCTTGATGCTCTTGG", FIRST, new Mapping(646, 724, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readSecPair646To724NoTaxon = createRead("A41BV:1:2112:4536:19347", sample, contig, "GCTAGTTGTAATCCAGCAATTGGTGGTTTAGCAAAAGGACATCTTGTAAAAGAGCTTGATGCTCTTGG", SECOND, new Mapping(646, 724, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		
		reads.add(readFirstPair83To175NoTaxon);
		reads.add(readSecPair83To175NoTaxon);
		reads.add(readFirstPair406To595NoTaxon);
		reads.add(readSecPair406To595NoTaxon);
		reads.add(readFirstPair408To574NoTaxon);
		reads.add(readSecPair408To574NoTaxon);
		reads.add(readFirstPair646To724NoTaxon);
		reads.add(readSecPair646To724NoTaxon);
		
		return reads;
	}
	
	private List<Read> createListOfReadsAssignedToArcobacter(Sample sample, Contig contig){
		
		List<Read>  reads = new ArrayList<Read>();
		Taxon arcobacter = new Taxon(28196l, 72294l, "Arcobacter", "genus");
		TaxonomicAssignment arcobacterTaxonomicAssignment = new TaxonomicAssignment(arcobacter, 1d);
		
		
		Read readFirstPair1To146Arcobacter = createRead("A41BV:1:1101:19732:11346", sample, contig, "GTCTAAAACTACTTTTTAAAACCCTTTGATATAAAAATCTATTGAATTACCAT", FIRST, new Mapping(1, 146, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair1To146Arcobacter = createRead("A41BV:1:1101:19732:11346", sample, contig, "GTCTAAAACTACTTTTTAAAACCCTTTGATATAAAAATCTATTGAATTACCAT", SECOND, new Mapping(1, 146, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair150To400Arcobacter = createRead("A41BV:1:2108:18435:14794", sample, contig, "AATATGTCCTTGAACAATATGACCTTCAAATCTATCGCCCATTTGCATTGCTGGTTCTATATGAACTT", SECOND, new Mapping(150, 400, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair344To594Arcobacter = createRead("A41BV:1:2112:17885:16663", sample, contig, "AAGCTCTCAACCTTTGCCATCTCTCTTATTAAACCTGTAAATACTTAAAAAACCTTTAGATA", SECOND, new Mapping(344, 594, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair404To654Arcobacter = createRead("A41BV:1:2108:18435:14794", sample, contig, "TAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCT", FIRST, new Mapping(404, 654, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readFirstPair555To669Arcobacter = createRead("A41BV:1:1108:9607:23773", sample, contig, "TTGAAGCAGCATTAGCAGGTGCAAGACTTGGTAAAAAAACACTATTAATAACAATGCTAGTTGAGCAAATTGGAGCGGCTAGT", FIRST, new Mapping(555, 669, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair555To669Arcobacter = createRead("A41BV:1:1108:9607:23773", sample, contig, "TTGAAGCAGCATTAGCAGGTGCAAGACTTGGTAAAAAAACACTATTAATAACAATGCTAGTTGAGCAAATTGGAGCGGCTAGT", SECOND, new Mapping(555, 669, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readFirstPair566To728Arcobacter = createRead("A41BV:1:2114:21626:18927", sample, contig, "TTGAAGCAGCATTAGCAGGTGCAAGACTTGGTAAAAAAACACTATTAATAACAATGCTAGTTGAGCAAATTGGAGCGGCTAGTTGTAATCCAGCAAT", FIRST, new Mapping(566, 728, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair566To728Arcobacter = createRead("A41BV:1:2114:21626:18927", sample, contig, "TTGAAGCAGCATTAGCAGGTGCAAGACTTGGTAAAAAAACACTATTAATAACAATGCTAGTTGAGCAAATTGGAGCGGCTAGTTGTAATCCAGCAAT", SECOND, new Mapping(566, 728, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		
		reads.add(readFirstPair1To146Arcobacter);
		reads.add(readSecPair1To146Arcobacter);
		reads.add(readSecPair150To400Arcobacter);
		reads.add(readSecPair344To594Arcobacter);
		reads.add(readSecPair404To654Arcobacter);
		reads.add(readFirstPair555To669Arcobacter);	
		reads.add(readSecPair555To669Arcobacter);
		reads.add(readFirstPair566To728Arcobacter);	
		reads.add(readSecPair566To728Arcobacter);
		
		return reads;

	}

	private Read createRead(String reference, Sample sample, Contig contig, String sequence, Integer pair, Mapping mapping, TaxonomicAssignment taxonomicAssignment) {
		 Read read = new Read(reference, sample, contig, sequence, pair, mapping);
		 read.toAssigTaxon(taxonomicAssignment);
		 return read;
	}

}
