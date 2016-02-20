package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.usp.iq.lbi.caravela.intervalTree.Segment;
import br.usp.iq.lbi.caravela.model.Taxon;

public class SegmentsCalculatorImplTest {
	
	@Test
	public void testSubtractionBetweenSegmentLists () throws Exception {
		
		List<Segment<Taxon>> taxonsSegmentList = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> noTaxonSegmentList = new ArrayList<Segment<Taxon>>();
		
		
		Taxon taxonA = new Taxon(1l,1l,"Taxon A", "genus");
		Taxon noTaxon = Taxon.getNOTaxon();
		
		List<Taxon> taxonListA = createTaxonList(taxonA);
		List<Taxon> NotaxonList = createTaxonList(noTaxon);
		
		noTaxonSegmentList.add(new Segment<Taxon>(50, 120, NotaxonList));
		noTaxonSegmentList.add(new Segment<Taxon>(280, 350, NotaxonList));
		
		taxonsSegmentList.add(new Segment<Taxon>(30, 100, taxonListA));
		taxonsSegmentList.add(new Segment<Taxon>(150, 300, taxonListA));
		taxonsSegmentList.add(new Segment<Taxon>(320, 330, taxonListA));
		

		List<Segment<Taxon>> noTaxonSubtractedSegmentList = new ArrayList<Segment<Taxon>>();
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(101, 120, NotaxonList));
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(301, 319, NotaxonList));
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(331, 350, NotaxonList));
		
		SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		Assert.assertEquals(noTaxonSubtractedSegmentList, target.subtract(noTaxonSegmentList, taxonsSegmentList));
		
	}
	
	@Test
	public void testSubtractionBetweenMinuendAndSubtrahendListWithJustOnlyOneElementWhenElementIsOnStartOfMinuend () throws Exception {
		
		List<Segment<Taxon>> taxonsSegmentList = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> noTaxonSegmentList = new ArrayList<Segment<Taxon>>();
		
		
		Taxon taxonA = new Taxon(1l,1l,"Taxon A", "genus");
		Taxon noTaxon = Taxon.getNOTaxon();
		
		List<Taxon> taxonListA = createTaxonList(taxonA);
		List<Taxon> NotaxonList = createTaxonList(noTaxon);
		
		
		Segment<Taxon> noTaxonSegment = new Segment<Taxon>(1, 60, NotaxonList);
		noTaxonSegmentList.add(noTaxonSegment);
		
		taxonsSegmentList.add(new Segment<Taxon>(1, 10, taxonListA));
		

		List<Segment<Taxon>> noTaxonSubtractedSegmentList = new ArrayList<Segment<Taxon>>();
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(11, 60, NotaxonList));
		
		SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		Assert.assertEquals(noTaxonSubtractedSegmentList, target.subtract(noTaxonSegment, taxonsSegmentList));
		
	}
	
	@Test
	public void testSubtractionBetweenMinuendAndSubtrahendListWithJustOnlyOneElementWhenElementIsOnEndOfMinuend () throws Exception {
		
		List<Segment<Taxon>> taxonsSegmentList = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> noTaxonSegmentList = new ArrayList<Segment<Taxon>>();
		
		
		Taxon taxonA = new Taxon(1l,1l,"Taxon A", "genus");
		Taxon noTaxon = Taxon.getNOTaxon();
		
		List<Taxon> taxonListA = createTaxonList(taxonA);
		List<Taxon> NotaxonList = createTaxonList(noTaxon);
		
		Segment<Taxon> noTaxonSegment = new Segment<Taxon>(1, 60, NotaxonList);
		noTaxonSegmentList.add(noTaxonSegment);
		
		taxonsSegmentList.add(new Segment<Taxon>(50, 60, taxonListA));
		

		List<Segment<Taxon>> noTaxonSubtractedSegmentList = new ArrayList<Segment<Taxon>>();
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(1, 49, NotaxonList));
		
		SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		Assert.assertEquals(noTaxonSubtractedSegmentList, target.subtract(noTaxonSegment, taxonsSegmentList));
		
	}
	
	@Test
	public void testSubtractionBetweenMinuendAndSubtrahendListWithJustOnlyOneElementWhenElementIsOnMidleOfMinuend () throws Exception {
		
		List<Segment<Taxon>> taxonsSegmentList = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> noTaxonSegmentList = new ArrayList<Segment<Taxon>>();
		
		
		Taxon taxonA = new Taxon(1l,1l,"Taxon A", "genus");
		Taxon noTaxon = Taxon.getNOTaxon();
		
		List<Taxon> taxonListA = createTaxonList(taxonA);
		List<Taxon> NotaxonList = createTaxonList(noTaxon);
		
		Segment<Taxon> noTaxonSegment = new Segment<Taxon>(1, 60, NotaxonList);
		noTaxonSegmentList.add(noTaxonSegment);
		
		taxonsSegmentList.add(new Segment<Taxon>(20, 30, taxonListA));
		

		List<Segment<Taxon>> noTaxonSubtractedSegmentList = new ArrayList<Segment<Taxon>>();
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(1, 19, NotaxonList));
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(31, 60, NotaxonList));
		
		SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		Assert.assertEquals(noTaxonSubtractedSegmentList, target.subtract(noTaxonSegment, taxonsSegmentList));
		
	}
	
	@Test
	public void testSubtractionBetweenMinuendAndSubtrahendListWithJustOnlyOneElementWhenElementIsOnMidleOfMinuendAndSubtrahendGreaterThanMinuend() throws Exception {
		
		List<Segment<Taxon>> taxonsSegmentList = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> noTaxonSegmentList = new ArrayList<Segment<Taxon>>();
		
		
		Taxon taxonA = new Taxon(1l,1l,"Taxon A", "genus");
		Taxon noTaxon = Taxon.getNOTaxon();
		
		List<Taxon> taxonListA = createTaxonList(taxonA);
		List<Taxon> NotaxonList = createTaxonList(noTaxon);
		
		noTaxonSegmentList.add(new Segment<Taxon>(57, 258, NotaxonList));
		noTaxonSegmentList.add(new Segment<Taxon>(396, 558, NotaxonList));
		
		
		taxonsSegmentList.add(new Segment<Taxon>(1, 444, taxonListA));
		taxonsSegmentList.add(new Segment<Taxon>(464, 1604, taxonListA));
		

		List<Segment<Taxon>> noTaxonSubtractedSegmentList = new ArrayList<Segment<Taxon>>();
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(445, 463, NotaxonList));
		
		SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		Assert.assertEquals(noTaxonSubtractedSegmentList, target.subtract(noTaxonSegmentList, taxonsSegmentList));
		
	}
	
	@Test
	public void testSubtractionBetweenMinuendAndSubtrahendListWithJustOnlyOneElementWhenElementIsOnMidleOfMinuend2 () throws Exception {
		
		List<Segment<Taxon>> taxonsSegmentList = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> noTaxonSegmentList = new ArrayList<Segment<Taxon>>();
		
		
		Taxon taxonA = new Taxon(1l,1l,"Taxon A", "genus");
		Taxon noTaxon = Taxon.getNOTaxon();
		
		List<Taxon> taxonListA = createTaxonList(taxonA);
		List<Taxon> NotaxonList = createTaxonList(noTaxon);
		
		Segment<Taxon> noTaxonSegment = new Segment<Taxon>(1, 60, NotaxonList);
		noTaxonSegmentList.add(noTaxonSegment);
		
		taxonsSegmentList.add(new Segment<Taxon>(10, 20, taxonListA));
		taxonsSegmentList.add(new Segment<Taxon>(30, 40, taxonListA));
		

		List<Segment<Taxon>> noTaxonSubtractedSegmentList = new ArrayList<Segment<Taxon>>();
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(1, 9, NotaxonList));
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(21, 29, NotaxonList));
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(41, 60, NotaxonList));
		
		SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		Assert.assertEquals(noTaxonSubtractedSegmentList, target.subtract(noTaxonSegment, taxonsSegmentList));
		
	}

	
	@Test
	public void testSubtractionBetweenMinuendAndSubtrahendListSegmentsWhenSubtrahendListIsEmpty() throws Exception {
		
		List<Segment<Taxon>> noTaxonSegmentList = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> taxonsSegmentList = new ArrayList<Segment<Taxon>>();
		
		Taxon noTaxon = Taxon.getNOTaxon();
		
		List<Taxon> NotaxonList = createTaxonList(noTaxon);
		
		Segment<Taxon> noTaxonSegment = new Segment<Taxon>(1, 60, NotaxonList);
		noTaxonSegmentList.add(noTaxonSegment);
		

		List<Segment<Taxon>> noTaxonSubtractedSegmentList = new ArrayList<Segment<Taxon>>();
		noTaxonSubtractedSegmentList.add(new Segment<Taxon>(1, 60, NotaxonList));
		
		
		SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		Assert.assertEquals(noTaxonSubtractedSegmentList, target.subtract(noTaxonSegment, taxonsSegmentList));
		
	}
	
	
	@Test
	public void teste1() throws Exception {
	SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		List<Segment<Taxon>> segmentListA = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> segmentListB = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> segmentListC = new ArrayList<Segment<Taxon>>();
		
		Taxon taxonA = new Taxon(1l,1l,"Arcobacter", "genus");
		Taxon taxonB = new Taxon(2l,1l,"Streptococcus", "genus");
		Taxon taxonC = Taxon.getNOTaxon();
		
		List<Taxon> taxonListA = createTaxonList(taxonA);
		List<Taxon> taxonListB = createTaxonList(taxonB);
		List<Taxon> taxonListC = createTaxonList(taxonC);
		
		
		segmentListA.add(new Segment<Taxon>(25, 1321, taxonListA));
		segmentListA.add(new Segment<Taxon>(1326, 1835, taxonListA));

		segmentListB.add(new Segment<Taxon>(878, 1128, taxonListB));
		segmentListB.add(new Segment<Taxon>(1182, 1425, taxonListB));
		
		segmentListC.add(new Segment<Taxon>(1405, 1477, taxonListC));
		segmentListC.add(new Segment<Taxon>(1696, 1758, taxonListC));
		
		Map<Taxon, List<Segment<Taxon>>> segmentsMap = new HashMap<Taxon, List<Segment<Taxon>>>();
		
		segmentsMap.put(taxonA, segmentListA);
		segmentsMap.put(taxonB, segmentListB);
		segmentsMap.put(taxonC, segmentListC);
		
		
		List<Segment<Taxon>> expectedUndefinedSegmentsByTaxon = new ArrayList<Segment<Taxon>>();
		
		List<Taxon> segmentTaxonListAB = new ArrayList<Taxon>();
		segmentTaxonListAB.add(taxonA);
		segmentTaxonListAB.add(taxonB);
		
		Segment<Taxon> s1 = new Segment<Taxon>(878, 1128, segmentTaxonListAB);
		Segment<Taxon> s2 = new Segment<Taxon>(1182, 1321, segmentTaxonListAB);
		Segment<Taxon> s3 = new Segment<Taxon>(1326, 1425, segmentTaxonListAB);
		
		List<Taxon> segmentTaxonListAC = new ArrayList<Taxon>();
		segmentTaxonListAC.add(taxonA);
		segmentTaxonListAC.add(taxonC);
		Segment<Taxon> s4 = new Segment<Taxon>(1405, 1477, segmentTaxonListAC);
		Segment<Taxon> s5 = new Segment<Taxon>(1696, 1758, segmentTaxonListAC);

		List<Taxon> sl6 = new ArrayList<Taxon>();
		sl6.add(taxonB);
		sl6.add(taxonC);
		Segment<Taxon> s6 = new Segment<Taxon>(1405, 1425, sl6);
		
		expectedUndefinedSegmentsByTaxon.add(s1);
		expectedUndefinedSegmentsByTaxon.add(s2);
		expectedUndefinedSegmentsByTaxon.add(s3);
		expectedUndefinedSegmentsByTaxon.add(s4);
		expectedUndefinedSegmentsByTaxon.add(s5);
		expectedUndefinedSegmentsByTaxon.add(s6);
		
		List<Segment<Taxon>> buildSegmentsByTaxon = target.buildUndfinedSegmentsByTaxon(segmentsMap);
	
		Collections.sort(expectedUndefinedSegmentsByTaxon);
		
		Assert.assertEquals(expectedUndefinedSegmentsByTaxon, buildSegmentsByTaxon);
		
		
	}

	@Test
	public void teste2() throws Exception {
	SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		List<Segment<Taxon>> featureViewerDataA = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> featureViewerDataB = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> featureViewerDataC = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> featureViewerDataD = new ArrayList<Segment<Taxon>>();
		List<Segment<Taxon>> featureViewerDataE = new ArrayList<Segment<Taxon>>();
		
		
		Taxon taxonA = new Taxon(1l,1l,"Arcobacter", "genus");
		Taxon taxonB = new Taxon(2l,1l,"Streptococcus", "genus");
		Taxon taxonC = Taxon.getNOTaxon();
		Taxon taxonD = new Taxon(3l,1l,"Fusca", "genus");
		Taxon taxonE = new Taxon(4l,1l,"Bispora", "genus");
		
		List<Taxon> taxonListA = createTaxonList(taxonA);
		List<Taxon> taxonListB = createTaxonList(taxonB);
		List<Taxon> taxonListC = createTaxonList(taxonC);
		List<Taxon> taxonListD = createTaxonList(taxonD);
		List<Taxon> taxonListE = createTaxonList(taxonE);
		
		featureViewerDataA.add(new Segment<Taxon>(1, 100, taxonListA));
		featureViewerDataB.add(new Segment<Taxon>(10, 30, taxonListB));
		featureViewerDataC.add(new Segment<Taxon>(40, 120, taxonListC));
		featureViewerDataD.add(new Segment<Taxon>(60, 90, taxonListD));
		featureViewerDataE.add(new Segment<Taxon>(80, 150, taxonListE));
		
		Map<Taxon, List<Segment<Taxon>>> segmentsMap = new HashMap<Taxon, List<Segment<Taxon>>>();
		
		segmentsMap.put(taxonA, featureViewerDataA);
		segmentsMap.put(taxonB, featureViewerDataB);
		segmentsMap.put(taxonC, featureViewerDataC);
		segmentsMap.put(taxonD, featureViewerDataD);
		segmentsMap.put(taxonE, featureViewerDataE);
		
		
		List<Segment<Taxon>> expectedUndefinedSegmentsByTaxon = new ArrayList<Segment<Taxon>>();
		
		List<Taxon> segmentTaxonListAB = new ArrayList<Taxon>();
		
		segmentTaxonListAB.add(taxonA);
		segmentTaxonListAB.add(taxonB);
		Segment<Taxon> s1AB = new Segment<Taxon>(10, 30, segmentTaxonListAB);
		
		List<Taxon> segmentTaxonListAC = new ArrayList<Taxon>();
		segmentTaxonListAC.add(taxonA);
		segmentTaxonListAC.add(taxonC);
		Segment<Taxon> s1AC = new Segment<Taxon>(40, 100, segmentTaxonListAC);
		
		List<Taxon> segmentTaxonListAD = new ArrayList<Taxon>();
		segmentTaxonListAD.add(taxonA);
		segmentTaxonListAD.add(taxonD);
		Segment<Taxon> s1AD = new Segment<Taxon>(60, 90, segmentTaxonListAD);
		
		List<Taxon> segmentTaxonListAE = new ArrayList<Taxon>();
		segmentTaxonListAE.add(taxonA);
		segmentTaxonListAE.add(taxonE);
		Segment<Taxon> s1AE = new Segment<Taxon>(80, 100, segmentTaxonListAE);
		
		List<Taxon> segmentTaxonListCD = new ArrayList<Taxon>();
		segmentTaxonListCD.add(taxonC);
		segmentTaxonListCD.add(taxonD);
		Segment<Taxon> s1CD = new Segment<Taxon>(60, 90, segmentTaxonListCD);
		
		List<Taxon> segmentTaxonListCE = new ArrayList<Taxon>();
		segmentTaxonListCE.add(taxonC);
		segmentTaxonListCE.add(taxonE);
		Segment<Taxon> s1CE = new Segment<Taxon>(80, 120, segmentTaxonListCE);
		
		List<Taxon> segmentTaxonListDE = new ArrayList<Taxon>();
		segmentTaxonListDE.add(taxonD);
		segmentTaxonListDE.add(taxonE);
		Segment<Taxon> s1DE = new Segment<Taxon>(80, 90, segmentTaxonListDE);

		
		expectedUndefinedSegmentsByTaxon.add(s1AB);
		expectedUndefinedSegmentsByTaxon.add(s1AC);
		expectedUndefinedSegmentsByTaxon.add(s1AE);
		expectedUndefinedSegmentsByTaxon.add(s1CD);
		expectedUndefinedSegmentsByTaxon.add(s1AD);
		expectedUndefinedSegmentsByTaxon.add(s1CE);
		expectedUndefinedSegmentsByTaxon.add(s1DE);
		
		Collections.sort(expectedUndefinedSegmentsByTaxon);
		
		List<Segment<Taxon>> buildSegmentsByTaxon = target.buildUndfinedSegmentsByTaxon(segmentsMap);
		
		Assert.assertEquals(expectedUndefinedSegmentsByTaxon, buildSegmentsByTaxon);
		
		
	}
	
	private List<Taxon> createTaxonList(Taxon... taxons) {
		List<Taxon> taxonList = new ArrayList<Taxon>();
		for (Taxon taxon : taxons) {
			taxonList.add(taxon);
		}
		return taxonList;
	}
	


}
