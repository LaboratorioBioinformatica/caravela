package br.usp.iq.lbi.caravela.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ReadTest {
	
	
	
	private Read read;
	
	@Before
	public void setup() {
		read = null;
	}

	@Test
	public void testWhenReferenceSampleAndPairAreSameShouldBeEquals() throws Exception {
		
		Study study = new Study("Composting 3b", "description composting 3d");
		
		Mapping mapping = createMapping();
		Sample sampleZC3bDay01 = new Sample(study, SampleStatus.PROCESSED, "zc3b-day-01", "description zc3b-day-01");
		
		Long taxonomyId = 1289127l;
		Long taxonomyParentId = 1289122l;
		Taxon taxonX = new Taxon(taxonomyId, taxonomyParentId, "Scientifica Name x", "genus");
		Taxon taxonY = new Taxon(taxonomyId, taxonomyParentId, "Scientifica Name y", "genus");
		
		Integer firstPair = 1;
		
		String sequence_01 = "ACTGGGTTAAGCATGAGA";
		String sequence_02 = "AGGGGGGGGGGGGGGGGGGGTTAAGCATGAGA";
		
		String reference = "asdfasdfsd";
	
		String contigReference = "asdfa5as56d4f65as4df65asdsdfsd";
		String contigSequence = "ATTTCCTGGTAGAGATAGATAGCACACAGCAGAGACATCTACTACAGAC";
		
		Integer numberOfReads = 10;
		Integer numberOfReadsClassified = 8;
		Integer numberOfFeatures = 2;
		
		List<Read> reads = new ArrayList<Read>();
		List<Feature> Features = new ArrayList<Feature>();
		Double tii = new Double(0.5);
		Contig contig = new Contig(sampleZC3bDay01, contigReference, contigSequence, contigSequence.length(), numberOfReads, numberOfReadsClassified, numberOfFeatures, tii);
		
		Read read = new Read(reference, sampleZC3bDay01, contig, sequence_01, sequence_01.length(), firstPair, mapping);
		Read readEq = new Read(reference, sampleZC3bDay01, contig, sequence_02, sequence_02.length(), firstPair, mapping);

		Assert.assertTrue(read.equals(readEq));
		
	}
	

	@Test
	public void testWhenOnlyPairIsDiferentSameShouldNotBeEquals() throws Exception {
		
		Study study = new Study("Composting 3b", "description composting 3d");
		Mapping mapping = createMapping();
		Sample sampleZC3bDay01 = new Sample(study, SampleStatus.PROCESSED, "zc3b-day-01", "description zc3b-day-01");
		
		Long taxonomyId = 1289127l;
		Long taxonomyParentId = 1289122l;
		Taxon taxonX = new Taxon(taxonomyId, taxonomyParentId, "Scientifica Name x", "genus");
		
		Integer firstPair = 1;
		Integer secPair = 2;
		
		String sequence_01 = "ACTGGGTTAAGCATGAGA";
		
		String reference = "asd8f79as7df:as8d7f8as";
		
		String contigReference = "asdfa5as56d4f65as4df65asdsdfsd";
		String contigSequence = "ATTTCCTGGTAGAGATAGATAGCACACAGCAGAGACATCTACTACAGAC";
		
		Integer numberOfReads = 10;
		Integer numberOfReadsClassified = 8;
		Integer numberOfFeatures = 2;
		
		List<Read> reads = new ArrayList<Read>();
		List<Feature> Features = new ArrayList<Feature>();
		Double tii = new Double(0.5);
		Contig contig = new Contig(sampleZC3bDay01, contigReference, contigSequence, contigSequence.length(), numberOfReads, numberOfReadsClassified, numberOfFeatures, tii);
		
		Read read = new Read(reference, sampleZC3bDay01, contig, sequence_01, sequence_01.length(), firstPair, mapping);
		Read readEq = new Read(reference, sampleZC3bDay01, contig, sequence_01, sequence_01.length(), secPair, mapping);

		Assert.assertFalse(read.equals(readEq));
		
	}
	
	@Test
	public void testWhenOnlySampleIsDiferentSameShouldNotBeEquals() throws Exception {
		
		Study study = new Study("Composting 3b", "description composting 3d");
		Mapping mapping = createMapping();
		Sample sampleZC3bDay01 = new Sample(study, SampleStatus.PROCESSED, "zc3b-day-01", "description zc3b-day-01");
		
		Sample sampleZC3bDay30 = new Sample(study, SampleStatus.PROCESSED, "zc3b-day-30", "description zc3b-day-30");
		
		Long taxonomyId = 1289127l;
		Long taxonomyParentId = 1289122l;
		Taxon taxonX = new Taxon(taxonomyId, taxonomyParentId, "Scientifica Name x", "genus");
		
		Integer firstPair = 1;
		
		String sequence_01 = "ACTGGGTTAAGCATGAGA";
		
		String reference = "asd8f79as7df:as8d7f8as";
		
		String contigReference = "asdfa5as56d4f65as4df65asdsdfsd";
		String contigSequence = "ATTTCCTGGTAGAGATAGATAGCACACAGCAGAGACATCTACTACAGAC";
		
		Integer numberOfReads = 10;
		Integer numberOfReadsClassified = 8;
		Integer numberOfFeatures = 2;
		
		List<Read> reads = new ArrayList<Read>();
		List<Feature> Features = new ArrayList<Feature>();
		Double tii = new Double(0.5);
		Contig contig = new Contig(sampleZC3bDay01, contigReference, contigSequence, contigSequence.length(), numberOfReads, numberOfReadsClassified, numberOfFeatures, tii);
		
		Read read = new Read(reference, sampleZC3bDay01, contig, sequence_01, sequence_01.length(), firstPair, mapping);
		Read readEq = new Read(reference, sampleZC3bDay30, contig, sequence_01, sequence_01.length(), firstPair, mapping);

		Assert.assertFalse(read.equals(readEq));
		
	}
	
	@Test
	public void testWhenOnlyReferenceIsDiferentSameShouldNotBeEquals() throws Exception {
		
		Study study = new Study("Composting 3b", "description composting 3d");
		Mapping mapping = createMapping();
		Sample sampleZC3bDay01 = new Sample(study, SampleStatus.PROCESSED, "zc3b-day-01", "description zc3b-day-01");
		
		
		Long taxonomyId = 1289127l;
		Long taxonomyParentId = 1289122l;
		Taxon taxonX = new Taxon(taxonomyId, taxonomyParentId, "Scientifica Name x", "genus");
		Integer firstPair = 1;
		String sequence_01 = "ACTGGGTTAAGCATGAGA";
		String reference = "asd8f79as7df:as8d7f8as";
		String otherReference = "HHHSDUI999as7df:as823f8as";
		
		String contigReference = "asdfa5as56d4f65as4df65asdsdfsd";
		String contigSequence = "ATTTCCTGGTAGAGATAGATAGCACACAGCAGAGACATCTACTACAGAC";
		
		Integer numberOfReads = 10;
		Integer numberOfReadsClassified = 8;
		Integer numberOfFeatures = 2;
		
		List<Read> reads = new ArrayList<Read>();
		List<Feature> Features = new ArrayList<Feature>();
		Double tii = new Double(0.5);
		Contig contig = new Contig(sampleZC3bDay01, contigReference, contigSequence, contigSequence.length(), numberOfReads, numberOfReadsClassified, numberOfFeatures, tii);
		
		Read read = new Read(reference, sampleZC3bDay01, contig, sequence_01, sequence_01.length(), firstPair, mapping);
		Read readEq = new Read(otherReference, sampleZC3bDay01, contig, sequence_01, sequence_01.length(), firstPair, mapping);

		Assert.assertFalse(read.equals(readEq));
		
	}
	
	private Mapping createMapping() {
		Mapping mapping = new Mapping(1, 100,"10M", 111);
		return mapping;
	}

}
