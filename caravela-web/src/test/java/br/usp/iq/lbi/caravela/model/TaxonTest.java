package br.usp.iq.lbi.caravela.model;


import org.junit.Assert;
import org.junit.Test;


public class TaxonTest {
	
	
	private static final Long NO_TAXON_ID = 0L;
	private static final Long NO_TAXON_PARENT_ID = 0L;
	private static final String NO_TAXON_SCIENTIFIC_NAME = "no taxon";
	private static final String NO_TAXON_RANK = "no taxon";
	

	@Test
	public void testEquals() throws Exception {
		
		Long taxonomyId = 1289127l;
		Long taxonomyParentId = 1289122l;
		
		Taxon taxon = new Taxon(taxonomyId, taxonomyParentId, "scientificName", "Rank");
		Taxon otherTaxon = new Taxon(taxonomyId, taxonomyParentId, "scientificName", "hnk");
		Taxon NOTaxon = new Taxon(NO_TAXON_ID, NO_TAXON_PARENT_ID, NO_TAXON_SCIENTIFIC_NAME, NO_TAXON_RANK);
		
		Assert.assertTrue(taxon.equals(otherTaxon));
		
		Assert.assertEquals(Taxon.getNOTaxon(), NOTaxon);
	}


	@Test
	public void testNotEquals() throws Exception {
		
		Long taxonomyId = 1289127l;
		Long otherTaxonomyId = 1289121l;
		Long taxonomyParentId = 1289122l;
		
		Taxon taxon = new Taxon(taxonomyId, taxonomyParentId, "scientificName", "rank");
		Taxon otherTaxon = new Taxon(otherTaxonomyId, taxonomyParentId, "scientificName", "rank");
		
		Assert.assertFalse(taxon.equals(otherTaxon));
	}
	

}
