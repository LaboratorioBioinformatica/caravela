package br.usp.iq.lbi.caravela.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import br.usp.iq.lbi.caravela.dao.FeatureDAO;
import br.usp.iq.lbi.caravela.dto.FeatureAnnotationTO;
import br.usp.iq.lbi.caravela.dto.FeatureAnnotationTypeTO;
import br.usp.iq.lbi.caravela.dto.FeatureTO;
import br.usp.iq.lbi.caravela.dto.GeneProductTO;
import br.usp.iq.lbi.caravela.dto.PhiloDistTO;
import br.usp.iq.lbi.caravela.dto.TaxonTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.FeatureAnnotation;
import br.usp.iq.lbi.caravela.model.FeatureAnnotationType;
import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Philodist;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.model.Treatment;

public class FeatureCreatorImplTest {

	FeatureCreator target;

	
	@Before
	public void setup() {
		target = new FeatureCreatorImpl();
	}
	
	
	@After
	public void teardown() {
		target = null;
	}
	
	@Test
	public void testName() throws Exception {
		
		Treatment ZC4Treatment = new Treatment("ZC4", "Zoo composting 4");
		Sample ZC4Day01Sample = new Sample(ZC4Treatment, SampleStatus.UPLOADED, "ZC4-DAY-01", "day 01 from zc4");
		Contig contig = new Contig(ZC4Day01Sample, "ASD12F1AS2D1F2AS1DF", "ATGTGGTCGTAGTGCTAGCAAGTCGTAGTGA", 31, 1, 1, 0, 0.5d);
		
		GeneProductTO geneProductTO = new GeneProductTO("gene product", "source gene Product");
		PhiloDistTO philoDistTO = new PhiloDistTO(12333l, 3321l, 85d, "lineagem;a;x;y;z");
		
		FeatureAnnotationTO faTOCOG = createFeatureAnnotatioTO(FeatureAnnotationTypeTO.COG, "COG0666", 95d, 100, 10, 110, 15, 115, 0.00001d, 325d);
		FeatureAnnotationTO faTOKO = createFeatureAnnotatioTO(FeatureAnnotationTypeTO.KO, "KO0666", 90d, 90, 1, 90, 15, 105, 0.00001d, 225d);
		List<FeatureAnnotationTO> annotationTOList = Arrays.asList(faTOCOG, faTOKO);
		Long taxonomyId = 123456l;
		TaxonTO taxonTO = new TaxonTO.Builder().setTaxonomyId(taxonomyId).build();
		
		
		
		FeatureTO featureTOCDS = createFeatureTO("CDS", 1, 150, -1, taxonTO, annotationTOList, geneProductTO, philoDistTO);
		FeatureTO featureTOtRNA = createFeatureTO("tRNA", 63, 137, 1, null, null, null, null);
		
		Taxon taxon = new Taxon(taxonomyId, 6544l, "Scientific name", "Genus");
		
		Feature featureCDS = createFeature(contig, "CDS", 1, 150, -1);
		Feature featuretRNA = createFeature(contig, "tRNA", 63, 137, 1);

		GeneProduct geneProduct = new GeneProduct(featureCDS, "gene product", "source gene Product");
		Philodist philodist = new Philodist(featureCDS, 12333l, 3321l, 85d, "lineagem;a;x;y;z");
		
		FeatureAnnotation featureAnnotationCOG = new FeatureAnnotation(featureCDS, FeatureAnnotationType.COG, "COG0666", 95d, 100, 10, 110, 15, 115, 0.00001d, 325d);
		FeatureAnnotation featureAnnotationKO = new FeatureAnnotation(featureCDS, FeatureAnnotationType.KO, "KO0666", 90d, 90, 1, 90, 15, 105, 0.00001d, 225d);
		
		
		NCBITaxonFinder mockNCBITaxonFinder = createMockNCBITaxonFinder(taxonomyId, taxon);
		FeatureDAO mockFeatureDAO = createMockFeatureDAO();
		
		
		target = new FeatureCreatorImpl(mockFeatureDAO, mockNCBITaxonFinder);
		
		List<Feature> resultList = target.createList(contig , Arrays.asList(featureTOCDS, featureTOtRNA));
		
		List<Feature> expectedResultList = Arrays.asList(featureCDS, featuretRNA);
		
		Assert.assertEquals(expectedResultList, resultList);
		
		for (Feature feature : resultList) {
			if(feature.getType().equals("CDS")){	
				Assert.assertEquals(geneProduct, feature.getGeneProduct());
				Assert.assertEquals(philodist, feature.getPhilodist());
				Assert.assertEquals(Arrays.asList(featureAnnotationCOG, featureAnnotationKO), feature.getFeatureAnnotations());
			}
		}

		Mockito.verify(mockNCBITaxonFinder, new Times(1)).searchTaxonByNCBITaxonomyId(taxonomyId);
		Mockito.verify(mockFeatureDAO).addBatch(featureCDS, expectedResultList.size());
		Mockito.verify(mockFeatureDAO).addBatch(featuretRNA, expectedResultList.size());

		
	}
	
	
	@Test
	public void testCreateAndSaveFeatureListWhenFeatureTOListHasJustOnlyRequiredFileds() throws Exception {
		Treatment ZC4Treatment = new Treatment("ZC4", "Zoo composting 4");
		Sample ZC4Day01Sample = new Sample(ZC4Treatment, SampleStatus.UPLOADED, "ZC4-DAY-01", "day 01 from zc4");
		Contig contig = new Contig(ZC4Day01Sample, "ASD12F1AS2D1F2AS1DF", "ATGTGGTCGTAGTGCTAGCAAGTCGTAGTGA", 31, 1, 1, 0, 0.5d);
		
		FeatureTO featureTOCDS = createFeatureTO("CDS", 1, 150, -1, null, null, null, null);
		FeatureTO featureTOtRNA = createFeatureTO("tRNA", 63, 137, 1, null, null, null, null);
		
		Feature featureCDS = createFeature(contig, "CDS", 1, 150, -1);
		Feature featuretRNA = createFeature(contig, "tRNA", 63, 137, 1);
		
		FeatureDAO mockFeatureDAO = createMockFeatureDAO();
		NCBITaxonFinder mockNCBITaxonFinder = Mockito.mock(NCBITaxonFinder.class);
		
		
		target = new FeatureCreatorImpl(mockFeatureDAO, mockNCBITaxonFinder);
		
		List<Feature> resultList = target.createList(contig , Arrays.asList(featureTOCDS, featureTOtRNA));
		
		List<Feature> expectedResultList = Arrays.asList(featureCDS, featuretRNA);
		
		
		Assert.assertEquals(expectedResultList, resultList);
		
		Mockito.verify(mockFeatureDAO).addBatch(featureCDS, expectedResultList.size());
		Mockito.verify(mockFeatureDAO).addBatch(featuretRNA, expectedResultList.size());
		Mockito.verifyZeroInteractions(mockNCBITaxonFinder);
		
	}
	
	
	private NCBITaxonFinder createMockNCBITaxonFinder(Long ncbiTaxonomyId, Taxon Taxon) {
		NCBITaxonFinder mock = Mockito.mock(NCBITaxonFinder.class);
		Mockito.when(mock.searchTaxonByNCBITaxonomyId(ncbiTaxonomyId)).thenReturn(Taxon);
		return mock;
	}


	private FeatureDAO createMockFeatureDAO() {
		FeatureDAO mock = Mockito.mock(FeatureDAO.class);
		return mock;
	}


	private Feature createFeature(Contig contig, String type, Integer start, Integer end, Integer strand) {
		return new Feature(contig, type, start, end, strand);
	}


	@Test
	public void testCreateListWhenFeatureTOListIsEmptyShouldBeReturnEmptyList() throws Exception {
		Contig contig = new Contig();
		List<Feature> resultList = target.createList(contig , Collections.emptyList());
		Assert.assertEquals(Collections.emptyList(), resultList);
	}


	private FeatureAnnotationTO createFeatureAnnotatioTO(FeatureAnnotationTypeTO annotationType, String annotationName, Double identity, Integer alignLength, Integer queryStart, Integer queryEnd, Integer subjectStart, Integer subjectEnd, Double evalue, Double bitScore) {
		return new FeatureAnnotationTO(annotationType, annotationName, identity, alignLength, queryStart, queryEnd, subjectStart, subjectEnd, evalue, bitScore);
	}


	private FeatureTO createFeatureTO(String type, Integer start, Integer end, Integer strand, TaxonTO taxonTO, List<FeatureAnnotationTO> annotationTOList, GeneProductTO geneProduct, PhiloDistTO philoDist) {
		return new FeatureTO(null, type, start, end, strand, taxonTO, annotationTOList, geneProduct, philoDist);
	}

}
