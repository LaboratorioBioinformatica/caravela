package br.usp.iq.lbi.caravela.model;

import junit.framework.Assert;

import org.junit.Test;


public class TaxonTest {
	@Test
	public void testEquals() throws Exception {
		Taxon taxon = new Taxon(1, "scientificName", "hank", new Double(0.5));
		Taxon otherTaxon = new Taxon(1, "scientificName", "hnk", new Double(0.6));
		
		Assert.assertTrue(taxon.equals(otherTaxon));
	}


	@Test
	public void testNotEquals() throws Exception {
		Taxon taxon = new Taxon(1, "scientificName", "hank", new Double(0.5));
		Taxon otherTaxon = new Taxon(1, "scientific", "hnk", new Double(0.6));
		
		Assert.assertFalse(taxon.equals(otherTaxon));
	}


}
