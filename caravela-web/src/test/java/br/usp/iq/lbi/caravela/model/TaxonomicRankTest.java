package br.usp.iq.lbi.caravela.model;

import org.junit.Assert;
import org.junit.Test;


public class TaxonomicRankTest {
	
	@Test
	public void test() throws Exception {
		Assert.assertEquals("species", TaxonomicRank.SPECIES.getValue());
		
	}
}
