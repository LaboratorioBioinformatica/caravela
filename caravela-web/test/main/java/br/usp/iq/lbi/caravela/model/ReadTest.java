package br.usp.iq.lbi.caravela.model;

import org.junit.Assert;
import org.junit.Test;



public class ReadTest {
	
	@Test
	public void testWhenReferenceSampleAndPairAreSameShouldBeEquals() throws Exception {
		
		Treatment treatment = new Treatment("Composting 3b", "description composting 3d");
		
		Sample sampleZC3bDay01 = new Sample(treatment, SampleStatus.PROCESSED, "zc3b-day-01", "description zc3b-day-01");
		
		Taxon taxonX = new Taxon(1289127, "Scientifica Name x", "genus", new Double(0.6));
		Taxon taxonY = new Taxon(1289127, "Scientifica Name y", "genus", new Double(0.6));
		
		Integer firstPair = 1;
		
		String sequence_01 = "ACTGGGTTAAGCATGAGA";
		String sequence_02 = "AGGGGGGGGGGGGGGGGGGGTTAAGCATGAGA";
		
		String reference = "asd8f79as7df:as8d7f8as";
		
		Read read = new Read(reference, sampleZC3bDay01 , sequence_01, firstPair, taxonX);
		Read readEq = new Read(reference, sampleZC3bDay01 , sequence_02, firstPair, taxonY);

		Assert.assertTrue(read.equals(readEq));
		
	}
	
	@Test
	public void testWhenOnlyPairIsDiferentSameShouldNotBeEquals() throws Exception {
		
		Treatment treatment = new Treatment("Composting 3b", "description composting 3d");
		
		Sample sampleZC3bDay01 = new Sample(treatment, SampleStatus.PROCESSED, "zc3b-day-01", "description zc3b-day-01");
		
		Taxon taxonX = new Taxon(1289127, "Scientifica Name x", "genus", new Double(0.6));
		
		Integer firstPair = 1;
		Integer secPair = 2;
		
		String sequence_01 = "ACTGGGTTAAGCATGAGA";
		
		String reference = "asd8f79as7df:as8d7f8as";
		
		Read read = new Read(reference, sampleZC3bDay01 , sequence_01, firstPair, taxonX);
		Read readEq = new Read(reference, sampleZC3bDay01 , sequence_01, secPair, taxonX);

		Assert.assertFalse(read.equals(readEq));
		
	}
	
	@Test
	public void testWhenOnlySampleIsDiferentSameShouldNotBeEquals() throws Exception {
		
		Treatment treatment = new Treatment("Composting 3b", "description composting 3d");
		
		Sample sampleZC3bDay01 = new Sample(treatment, SampleStatus.PROCESSED, "zc3b-day-01", "description zc3b-day-01");
		
		Sample sampleZC3bDay30 = new Sample(treatment, SampleStatus.PROCESSED, "zc3b-day-30", "description zc3b-day-30");
		
		Taxon taxonX = new Taxon(1289127, "Scientifica Name x", "genus", new Double(0.6));
		
		Integer firstPair = 1;
		
		String sequence_01 = "ACTGGGTTAAGCATGAGA";
		
		String reference = "asd8f79as7df:as8d7f8as";
		
		Read read = new Read(reference, sampleZC3bDay01 , sequence_01, firstPair, taxonX);
		Read readEq = new Read(reference, sampleZC3bDay30 , sequence_01, firstPair, taxonX);

		Assert.assertFalse(read.equals(readEq));
		
	}
	
	@Test
	public void testWhenOnlyReferenceIsDiferentSameShouldNotBeEquals() throws Exception {
		
		Treatment treatment = new Treatment("Composting 3b", "description composting 3d");
		
		Sample sampleZC3bDay01 = new Sample(treatment, SampleStatus.PROCESSED, "zc3b-day-01", "description zc3b-day-01");
		
		
		Taxon taxonX = new Taxon(1289127, "Scientifica Name x", "genus", new Double(0.6));
		Integer firstPair = 1;
		String sequence_01 = "ACTGGGTTAAGCATGAGA";
		String reference = "asd8f79as7df:as8d7f8as";
		String otherReference = "HHHSDUI999as7df:as823f8as";
		
		Read read = new Read(reference, sampleZC3bDay01 , sequence_01, firstPair, taxonX);
		Read readEq = new Read(otherReference, sampleZC3bDay01 , sequence_01, firstPair, taxonX);

		Assert.assertFalse(read.equals(readEq));
		
	}


}
