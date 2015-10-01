package br.usp.iq.lbi.caravela.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TaxonTest {
	
	
	private String reference;
	private Sample sample;
	private Contig contig;
	private String sequence;
	private Integer pair;
	private Taxon taxon;
	private Mapping mapping;
	private Read read;

	
	@Before
	public void setup() {
		
	}
	

	@Test
	public void testEquals() throws Exception {
		new Read(reference, sample , contig , sequence, pair, mapping);
		
		Taxon taxon = new Taxon(read, 1, "scientificName", "Rank", new Double(0.5));
		Taxon otherTaxon = new Taxon(read, 1, "scientificName", "hnk", new Double(0.6));
		
		Assert.assertTrue(taxon.equals(otherTaxon));
	}


	@Test
	public void testNotEquals() throws Exception {
		Taxon taxon = new Taxon(read, 1, "scientificName", "rank", new Double(0.5));
		Taxon otherTaxon = new Taxon(read, 1, "scientific", "hnk", new Double(0.6));
		
		Assert.assertFalse(taxon.equals(otherTaxon));
	}
	

}
