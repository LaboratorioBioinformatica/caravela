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
	private Mapping mapping;

	
	@Before
	public void setup() {
		
	}
	

	@Test
	public void testEquals() throws Exception {
		new Read(reference, sample , contig , sequence, pair, mapping);
		
		Long taxonomyId = 1289127l;
		Long taxonomyParentId = 1289122l;
		
		Taxon taxon = new Taxon(taxonomyId, taxonomyParentId, "scientificName", "Rank");
		Taxon otherTaxon = new Taxon(taxonomyId, taxonomyParentId, "scientificName", "hnk");
		
		Assert.assertTrue(taxon.equals(otherTaxon));
	}


	@Test
	public void testNotEquals() throws Exception {
		
		Long taxonomyId = 1289127l;
		Long taxonomyParentId = 1289122l;
		
		Taxon taxon = new Taxon(taxonomyId, taxonomyParentId, "scientificName", "rank");
		Taxon otherTaxon = new Taxon(taxonomyId, taxonomyParentId, "scientific", "hnk");
		
		Assert.assertFalse(taxon.equals(otherTaxon));
	}
	

}
