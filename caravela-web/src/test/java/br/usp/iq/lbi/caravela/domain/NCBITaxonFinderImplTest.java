package br.usp.iq.lbi.caravela.domain;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.AtMost;

import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.model.Taxon;


public class NCBITaxonFinderImplTest {
	
	
	
	private static final int ONE = 1;

	@Test
	public void testVerifyIfApplicationScopedAnnotationIsPresent() throws Exception {
		NCBITaxonFinderImpl ncbiTaxonFinderImpl = new NCBITaxonFinderImpl();
		ApplicationScoped[] ApplicationScopedAnnotations = ncbiTaxonFinderImpl.getClass().getAnnotationsByType(ApplicationScoped.class);
		Assert.assertEquals(ONE, ApplicationScopedAnnotations.length);
	}
	
	@Test
		public void testSearchTaxonByNCBITaxonomyIdWhenTaxonNoFoundShouldReturnNull() throws Exception {
			
			Taxon taxonToBeSearch = createTaxonWithTaxonomyId(3l);
			List<Taxon> allTaxonList = Arrays.asList(createTaxonWithTaxonomyId(1l), createTaxonWithTaxonomyId(2l), taxonToBeSearch, createTaxonWithTaxonomyId(4l), createTaxonWithTaxonomyId(8l));
			TaxonDAO mockTaxonDAO = Mockito.mock(TaxonDAO.class);
			Mockito.when(mockTaxonDAO.findAll()).thenReturn(allTaxonList);
			
			NCBITaxonFinderImpl ncbiTaxonFinderImpl = new NCBITaxonFinderImpl(mockTaxonDAO);
			Taxon taxonResultNull = ncbiTaxonFinderImpl.searchTaxonByNCBITaxonomyId(10l);
			
			Assert.assertNull(taxonResultNull);
			Assert.assertEquals(taxonToBeSearch, ncbiTaxonFinderImpl.searchTaxonByNCBITaxonomyId(3l));
			
			Mockito.verify(mockTaxonDAO, new AtMost(1)).findAll();
		}

	private Taxon createTaxonWithTaxonomyId(Long taxonomyId) {
		return new Taxon(taxonomyId, 1l, "scientifica Name", "genus");
	}

}
