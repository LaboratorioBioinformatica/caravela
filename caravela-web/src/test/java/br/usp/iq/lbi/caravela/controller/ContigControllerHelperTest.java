package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Mapping;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.model.TaxonomicAssignment;


public class ContigControllerHelperTest {
	
	private static final Integer FIRST = 1;
	private static final Integer FLAG_ALIGNMENT = 83;
	private static final Integer SECOND = 2;

	@Test
	public void testSearchbyUnclassifiedReadThatWouldBeClassified() throws Exception {
		
		ContigControllerHelper target = new ContigControllerHelper();
		
		Contig contig = new Contig();
		Sample sample = new Sample();
		
		List<Read> readsOnContig = createListOfReadsOnContig(contig, sample);
		String rank = "genus";
		
		Map<Taxon, List<Read>> expectedMapResult = createExpectedMapResult();
		
		Map<Taxon, List<Read>> MapResult = target.searchbyUnclassifiedReadThatWouldBeClassified(readsOnContig, rank);
		
		Assert.assertEquals(expectedMapResult, MapResult);
		
		
		
		
	}

	private Map<Taxon, List<Read>> createExpectedMapResult() {
		List<Read>  reads = new ArrayList<Read>();
		
		// TODO Auto-generated method stub
		return null;
	}

	private List<Read> createListOfReadsOnContig(Contig contig, Sample sample) {
		List<Read>  reads = new ArrayList<Read>();

		Taxon arcobacter = new Taxon(28196l, 72294l, "Arcobacter", "genus");
		Taxon noTaxon = Taxon.getNOTaxon();
		
		TaxonomicAssignment arcobacterTaxonomicAssignment = new TaxonomicAssignment(arcobacter, 1d);
		TaxonomicAssignment noTaxonTaxonomicAssignment = new TaxonomicAssignment(noTaxon, 1d);
		
		
		Read readFirstPair1To146Arcobacter = createRead("A41BV:1:1101:19732:11346", sample, contig, "GTCTAAAACTACTTTTTAAAACCCTTTGATATAAAAATCTATTGAATTACCAT", FIRST, new Mapping(1, 146, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair1To146Arcobacter = createRead("A41BV:1:1101:19732:11346", sample, contig, "GTCTAAAACTACTTTTTAAAACCCTTTGATATAAAAATCTATTGAATTACCAT", SECOND, new Mapping(1, 146, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair150To400Arcobacter = createRead("A41BV:1:2108:18435:14794", sample, contig, "AATATGTCCTTGAACAATATGACCTTCAAATCTATCGCCCATTTGCATTGCTGGTTCTATATGAACTT", SECOND, new Mapping(150, 400, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair344To594Arcobacter = createRead("A41BV:1:2112:17885:16663", sample, contig, "AAGCTCTCAACCTTTGCCATCTCTCTTATTAAACCTGTAAATACTTAAAAAACCTTTAGATA", SECOND, new Mapping(344, 594, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair404To654Arcobacter = createRead("A41BV:1:2108:18435:14794", sample, contig, "TAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCT", FIRST, new Mapping(404, 654, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readFirstPair555To669Arcobacter = createRead("A41BV:1:1108:9607:23773", sample, contig, "TTGAAGCAGCATTAGCAGGTGCAAGACTTGGTAAAAAAACACTATTAATAACAATGCTAGTTGAGCAAATTGGAGCGGCTAGT", FIRST, new Mapping(555, 669, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair555To669Arcobacter = createRead("A41BV:1:1108:9607:23773", sample, contig, "TTGAAGCAGCATTAGCAGGTGCAAGACTTGGTAAAAAAACACTATTAATAACAATGCTAGTTGAGCAAATTGGAGCGGCTAGT", SECOND, new Mapping(555, 669, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readFirstPair566To728Arcobacter = createRead("A41BV:1:2114:21626:18927", sample, contig, "TTGAAGCAGCATTAGCAGGTGCAAGACTTGGTAAAAAAACACTATTAATAACAATGCTAGTTGAGCAAATTGGAGCGGCTAGTTGTAATCCAGCAAT", FIRST, new Mapping(566, 728, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		Read readSecPair566To728Arcobacter = createRead("A41BV:1:2114:21626:18927", sample, contig, "TTGAAGCAGCATTAGCAGGTGCAAGACTTGGTAAAAAAACACTATTAATAACAATGCTAGTTGAGCAAATTGGAGCGGCTAGTTGTAATCCAGCAAT", SECOND, new Mapping(566, 728, FLAG_ALIGNMENT), arcobacterTaxonomicAssignment);
		
		Read readFirstPair83To175NoTaxon = createRead("A41BV:1:1110:16554:9710", sample, contig, "ATCTATTGAATTACCATTTTTATCTATTTTTTCAACTACTCCAACACAATCAATATGT", FIRST, new Mapping(83, 175, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readSecPair83To175NoTaxon = createRead("A41BV:1:1110:16554:9710", sample, contig, "ATCTATTGAATTACCATTTTTATCTATTTTTTCAACTACTCCAACACAATCAATATGT", SECOND, new Mapping(83, 175, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readFirstPair406To595NoTaxon = createRead("A41BV:1:1114:11512:26655", sample, contig, "AACCTGTAAACACTTAAAAAACCTTTAGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAA", FIRST, new Mapping(406, 595, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readSecPair406To595NoTaxon = createRead("A41BV:1:1114:11512:26655", sample, contig, "AACCTGTAAACACTTAAAAAACCTTTAGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAA", SECOND, new Mapping(406, 595, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readFirstPair408To574NoTaxon = createRead("A41BV:1:2101:11746:13368", sample, contig, "AGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAATACT", FIRST, new Mapping(408, 574, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readSecPair408To574NoTaxon = createRead("A41BV:1:2101:11746:13368", sample, contig, "AGATAAAATTTGGTTATAATAGCCAAAAATTCATAAGAAGCTAATTTTAACTCTTATTTAAAAATTCTAATACT", SECOND, new Mapping(408, 574, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readFirstPair646To724NoTaxon = createRead("A41BV:1:2112:4536:19347", sample, contig, "GCTAGTTGTAATCCAGCAATTGGTGGTTTAGCAAAAGGACATCTTGTAAAAGAGCTTGATGCTCTTGG", FIRST, new Mapping(646, 724, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		Read readSecPair646To724NoTaxon = createRead("A41BV:1:2112:4536:19347", sample, contig, "GCTAGTTGTAATCCAGCAATTGGTGGTTTAGCAAAAGGACATCTTGTAAAAGAGCTTGATGCTCTTGG", SECOND, new Mapping(646, 724, FLAG_ALIGNMENT), noTaxonTaxonomicAssignment);
		
		
		reads.add(readFirstPair1To146Arcobacter);
		reads.add(readSecPair1To146Arcobacter);
		reads.add(readSecPair150To400Arcobacter);
		reads.add(readSecPair344To594Arcobacter);
		reads.add(readSecPair404To654Arcobacter);
		reads.add(readFirstPair555To669Arcobacter);	
		reads.add(readSecPair555To669Arcobacter);
		reads.add(readFirstPair566To728Arcobacter);	
		reads.add(readSecPair566To728Arcobacter);

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

	private Read createRead(String reference, Sample sample, Contig contig, String sequence, Integer pair, Mapping mapping, TaxonomicAssignment taxonomicAssignment) {
		 Read read = new Read(reference, sample, contig, sequence, pair, mapping);
		 read.toAssigTaxon(taxonomicAssignment);
		 return read;
	}

}
