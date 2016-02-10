package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.dto.featureViewer.Segment;

public class SegmentsCalculatorImplTest {
	
	
	
	@Test
	public void teste1() throws Exception {
	SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		List<FeatureViewerDataTO> featureViewerDataA = new ArrayList<FeatureViewerDataTO>();
		List<FeatureViewerDataTO> featureViewerDataB = new ArrayList<FeatureViewerDataTO>();
		List<FeatureViewerDataTO> featureViewerDataC = new ArrayList<FeatureViewerDataTO>();
		
		String taxonA = "Arcobacter";
		String taxonB = "Streptococcus";
		String taxonC = "no taxon";
		
		featureViewerDataA.add(new FeatureViewerDataTO(25, 1321, taxonA, "1635638"));
		featureViewerDataA.add(new FeatureViewerDataTO(1326, 1835, taxonA, "1635668"));

		featureViewerDataB.add(new FeatureViewerDataTO(878, 1128, taxonB, "1635660"));
		featureViewerDataB.add(new FeatureViewerDataTO(1182, 1425, taxonB, "1635667"));
		
		featureViewerDataC.add(new FeatureViewerDataTO(1405, 1477, taxonC, "1635670"));
		featureViewerDataC.add(new FeatureViewerDataTO(1696, 1758, taxonC, "1635684"));
		
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		featureViewerDataMap.put(taxonA, featureViewerDataA);
		featureViewerDataMap.put(taxonB, featureViewerDataB);
		featureViewerDataMap.put(taxonC, featureViewerDataC);
		
		
		List<Segment> expectedUndefinedSegmentsByTaxon = new ArrayList<Segment>();
		
		List<String> segmentTaxonListAB = new ArrayList<String>();
		segmentTaxonListAB.add(taxonA);
		segmentTaxonListAB.add(taxonB);
		Segment s1 = new Segment(878, 1128, segmentTaxonListAB);
		Segment s2 = new Segment(1182, 1321, segmentTaxonListAB);
		Segment s3 = new Segment(1326, 1425, segmentTaxonListAB);
		
		List<String> segmentTaxonListAC = new ArrayList<String>();
		segmentTaxonListAC.add(taxonA);
		segmentTaxonListAC.add(taxonC);
		Segment s4 = new Segment(1405, 1477, segmentTaxonListAC);
		Segment s5 = new Segment(1696, 1758, segmentTaxonListAC);

		List<String> sl6 = new ArrayList<String>();
		sl6.add(taxonB);
		sl6.add(taxonC);
		Segment s6 = new Segment(1405, 1425, sl6);
		
		expectedUndefinedSegmentsByTaxon.add(s1);
		expectedUndefinedSegmentsByTaxon.add(s2);
		expectedUndefinedSegmentsByTaxon.add(s3);
		expectedUndefinedSegmentsByTaxon.add(s4);
		expectedUndefinedSegmentsByTaxon.add(s5);
		expectedUndefinedSegmentsByTaxon.add(s6);
		
		Collections.sort(expectedUndefinedSegmentsByTaxon);
		
		List<Segment> buildSegmentsByTaxon = target.buildUndfinedSegmentsByTaxon(featureViewerDataMap);
		
		Assert.assertEquals(expectedUndefinedSegmentsByTaxon, buildSegmentsByTaxon);
		
		
	}
	
	@Test
	public void teste2() throws Exception {
	SegmentsCalculatorImpl target = new SegmentsCalculatorImpl();
		
		List<FeatureViewerDataTO> featureViewerDataA = new ArrayList<FeatureViewerDataTO>();
		List<FeatureViewerDataTO> featureViewerDataB = new ArrayList<FeatureViewerDataTO>();
		List<FeatureViewerDataTO> featureViewerDataC = new ArrayList<FeatureViewerDataTO>();
		List<FeatureViewerDataTO> featureViewerDataD = new ArrayList<FeatureViewerDataTO>();
		List<FeatureViewerDataTO> featureViewerDataE = new ArrayList<FeatureViewerDataTO>();
		
		String taxonA = "Arcobacter";
		String taxonB = "Streptococcus";
		String taxonC = "no taxon";
		String taxonD = "Fusca";
		String taxonE = "Bispora";
		
		featureViewerDataA.add(new FeatureViewerDataTO(1, 100, taxonA, "1635638"));
		featureViewerDataB.add(new FeatureViewerDataTO(10, 30, taxonB, "1635660"));
		featureViewerDataC.add(new FeatureViewerDataTO(40, 120, taxonC, "1635670"));
		featureViewerDataD.add(new FeatureViewerDataTO(60, 90, taxonD, "1855668"));
		featureViewerDataE.add(new FeatureViewerDataTO(80, 150, taxonE, "1637967"));
		
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = new HashMap<String, List<FeatureViewerDataTO>>();
		
		featureViewerDataMap.put(taxonA, featureViewerDataA);
		featureViewerDataMap.put(taxonB, featureViewerDataB);
		featureViewerDataMap.put(taxonC, featureViewerDataC);
		featureViewerDataMap.put(taxonD, featureViewerDataD);
		featureViewerDataMap.put(taxonE, featureViewerDataE);
		
		
		List<Segment> expectedUndefinedSegmentsByTaxon = new ArrayList<Segment>();
		
		List<String> segmentTaxonListAB = new ArrayList<String>();
		segmentTaxonListAB.add(taxonA);
		segmentTaxonListAB.add(taxonB);
		Segment s1AB = new Segment(10, 30, segmentTaxonListAB);
		
		List<String> segmentTaxonListAC = new ArrayList<String>();
		segmentTaxonListAC.add(taxonA);
		segmentTaxonListAC.add(taxonC);
		Segment s1AC = new Segment(40, 100, segmentTaxonListAC);
		
		List<String> segmentTaxonListAD = new ArrayList<String>();
		segmentTaxonListAD.add(taxonA);
		segmentTaxonListAD.add(taxonD);
		Segment s1AD = new Segment(60, 90, segmentTaxonListAD);
		
		List<String> segmentTaxonListAE = new ArrayList<String>();
		segmentTaxonListAE.add(taxonA);
		segmentTaxonListAE.add(taxonE);
		Segment s1AE = new Segment(80, 100, segmentTaxonListAE);
		
		List<String> segmentTaxonListCD = new ArrayList<String>();
		segmentTaxonListCD.add(taxonC);
		segmentTaxonListCD.add(taxonD);
		Segment s1CD = new Segment(60, 90, segmentTaxonListCD);
		
		List<String> segmentTaxonListCE = new ArrayList<String>();
		segmentTaxonListCE.add(taxonC);
		segmentTaxonListCE.add(taxonE);
		Segment s1CE = new Segment(80, 120, segmentTaxonListCE);
		
		List<String> segmentTaxonListDE = new ArrayList<String>();
		segmentTaxonListDE.add(taxonD);
		segmentTaxonListDE.add(taxonE);
		Segment s1DE = new Segment(80, 90, segmentTaxonListDE);

		
		expectedUndefinedSegmentsByTaxon.add(s1AB);
		expectedUndefinedSegmentsByTaxon.add(s1AC);
		expectedUndefinedSegmentsByTaxon.add(s1AE);
		expectedUndefinedSegmentsByTaxon.add(s1CD);
		expectedUndefinedSegmentsByTaxon.add(s1AD);
		expectedUndefinedSegmentsByTaxon.add(s1CE);
		expectedUndefinedSegmentsByTaxon.add(s1DE);
		
		Collections.sort(expectedUndefinedSegmentsByTaxon);
		
		List<Segment> buildSegmentsByTaxon = target.buildUndfinedSegmentsByTaxon(featureViewerDataMap);
		
		Assert.assertEquals(expectedUndefinedSegmentsByTaxon, buildSegmentsByTaxon);
		
		
	}


}
