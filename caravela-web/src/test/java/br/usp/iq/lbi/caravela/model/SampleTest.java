package br.usp.iq.lbi.caravela.model;

import org.junit.Assert;
import org.junit.Test;

public class SampleTest {
	
	@Test
	public void test() {
		Sample sample = new Sample(new Study(), SampleStatus.CREATED, "Teste", "Teste");
		
		
		Assert.assertEquals(sample.getSampleStatus(), SampleStatus.CREATED);
		
		sample.toUploaded();
		Assert.assertEquals(sample.getSampleStatus(), SampleStatus.UPLOADED);
		
		sample.toProcessing();
		Assert.assertEquals(sample.getSampleStatus(), SampleStatus.PROCESSING);
		
		sample.toProcessed();
		Assert.assertEquals(sample.getSampleStatus(), SampleStatus.PROCESSED);
		

	}

}
