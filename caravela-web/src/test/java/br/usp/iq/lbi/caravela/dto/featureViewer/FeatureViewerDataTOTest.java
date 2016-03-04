package br.usp.iq.lbi.caravela.dto.featureViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class FeatureViewerDataTOTest {

	@Test
	public void testSort() {
		List<FeatureViewerDataTO> list = new ArrayList<FeatureViewerDataTO>();
		list.add(new FeatureViewerDataTO(1, 10, "description", "1"));
		list.add(new FeatureViewerDataTO(7, 15, "description", "1"));
		list.add(new FeatureViewerDataTO(18, 23, "description", "1"));
		list.add(new FeatureViewerDataTO(7, 13, "description", "1"));
		list.add(new FeatureViewerDataTO(1, 10, "description", "1"));
		list.add(new FeatureViewerDataTO(20, 22, "description", "1"));
		
		Collections.sort(list);
		
		List<FeatureViewerDataTO> sortedlist = new ArrayList<FeatureViewerDataTO>();
		sortedlist.add(new FeatureViewerDataTO(1, 10, "description", "1"));
		sortedlist.add(new FeatureViewerDataTO(1, 10, "description", "1"));
		sortedlist.add(new FeatureViewerDataTO(7, 13, "description", "1"));
		sortedlist.add(new FeatureViewerDataTO(7, 15, "description", "1"));
		sortedlist.add(new FeatureViewerDataTO(18, 23, "description", "1"));
		sortedlist.add(new FeatureViewerDataTO(20, 22, "description", "1"));
		
		Iterator<FeatureViewerDataTO> targetIt = list.iterator();
		
		for (FeatureViewerDataTO featureViewerDataTO : sortedlist) {
			Assert.assertEquals(featureViewerDataTO, targetIt.next());
			
		}
		
	}
	
	@Test
	public void testColor() throws Exception {
		String color = "#ffffff";
		FeatureViewerDataTO target = new FeatureViewerDataTO(1, 100, "description", "1", color);
		Assert.assertEquals(color, target.getColor());
		
	}
	
	@Test
	public void testIntersects() throws Exception {
		
		FeatureViewerDataTO interval1 = new FeatureViewerDataTO(1, 100, "description", "1");
		FeatureViewerDataTO interval2 = new FeatureViewerDataTO(1, 100, "description", "1");
		FeatureViewerDataTO interval3 = new FeatureViewerDataTO(30, 50, "description", "1");
		FeatureViewerDataTO interval4 = new FeatureViewerDataTO(1, 29, "description", "1");
		FeatureViewerDataTO interval5 = new FeatureViewerDataTO(51, 90, "description", "1");
		
		FeatureViewerDataTO interval6 = new FeatureViewerDataTO(1, 30, "description", "1");
		FeatureViewerDataTO interval7 = new FeatureViewerDataTO(50, 90, "description", "1");
		
		
		
		Assert.assertTrue(interval1.intersects(interval2));
		Assert.assertTrue(interval2.intersects(interval1));
		
		Assert.assertFalse(interval3.intersects(interval4));
		Assert.assertFalse(interval3.intersects(interval5));
		
		Assert.assertTrue(interval3.intersects(interval6));
		Assert.assertTrue(interval3.intersects(interval7));
		
	}
	
	
	
	@Test
	public void testCotains() throws Exception {
		
		FeatureViewerDataTO reference = new FeatureViewerDataTO(10, 100, "description", "1");
		FeatureViewerDataTO sameReference = new FeatureViewerDataTO(10, 100, "description", "1");
		FeatureViewerDataTO LimitsInsedeReference = new FeatureViewerDataTO(11, 99, "description", "1");
		FeatureViewerDataTO insedeReference = new FeatureViewerDataTO(20, 29, "description", "1");
		
		FeatureViewerDataTO onLeftOfReferenceLimit = new FeatureViewerDataTO(1, 9, "description", "1");
		FeatureViewerDataTO onLeftOfReference = new FeatureViewerDataTO(2, 7, "description", "1");
		
		FeatureViewerDataTO onRigthtOfReferenceLimit = new FeatureViewerDataTO(101, 110, "description", "1");
		FeatureViewerDataTO onRigthOfReference = new FeatureViewerDataTO(105, 115, "description", "1");
		
		Assert.assertTrue(reference.contains((LimitsInsedeReference)));
		Assert.assertFalse(LimitsInsedeReference.contains((reference)));
		
		Assert.assertTrue(reference.contains((sameReference)));
		Assert.assertTrue(sameReference.contains((reference)));
		
		Assert.assertTrue(reference.contains((insedeReference)));
		
		Assert.assertFalse(reference.contains((onLeftOfReferenceLimit)));
		Assert.assertFalse(reference.contains((onLeftOfReference)));
		Assert.assertFalse(reference.contains((onRigthtOfReferenceLimit)));
		Assert.assertFalse(reference.contains((onRigthOfReference)));
		
	}
	
	
	@Test
	public void testIsOnLeft() throws Exception {
		
		FeatureViewerDataTO reference = new FeatureViewerDataTO(10, 100, "description", "1");
		FeatureViewerDataTO sameReference = new FeatureViewerDataTO(10, 100, "description", "1");
		FeatureViewerDataTO LimitsInsedeReference = new FeatureViewerDataTO(11, 99, "description", "1");
		FeatureViewerDataTO insedeReference = new FeatureViewerDataTO(20, 29, "description", "1");
		
		FeatureViewerDataTO onLeftOfReferenceLimit = new FeatureViewerDataTO(1, 9, "description", "1");
		FeatureViewerDataTO onLeftOfReference = new FeatureViewerDataTO(2, 7, "description", "1");
		
		FeatureViewerDataTO onRigthtOfReferenceLimit = new FeatureViewerDataTO(101, 110, "description", "1");
		FeatureViewerDataTO onRigthOfReference = new FeatureViewerDataTO(105, 115, "description", "1");
		
		Assert.assertFalse(reference.isOnLeft((LimitsInsedeReference)));
		
		Assert.assertFalse(LimitsInsedeReference.isOnLeft((reference)));
		
		Assert.assertFalse(reference.isOnLeft((sameReference)));
		Assert.assertFalse(sameReference.isOnLeft((reference)));
		
		
		Assert.assertFalse(reference.isOnLeft((insedeReference)));

		Assert.assertTrue(reference.isOnLeft((onLeftOfReferenceLimit)));
		Assert.assertTrue(reference.isOnLeft((onLeftOfReference)));
		
		Assert.assertFalse(reference.isOnLeft((onRigthtOfReferenceLimit)));
		Assert.assertFalse(reference.isOnLeft((onRigthOfReference)));
		
		
	}
	
	@Test
	public void testIsOnRigth() throws Exception {
		
		FeatureViewerDataTO reference = new FeatureViewerDataTO(10, 100, "description", "1");
		FeatureViewerDataTO sameReference = new FeatureViewerDataTO(10, 100, "description", "1");
		FeatureViewerDataTO LimitsInsedeReference = new FeatureViewerDataTO(11, 99, "description", "1");
		FeatureViewerDataTO insedeReference = new FeatureViewerDataTO(20, 29, "description", "1");
		
		FeatureViewerDataTO onLeftOfReferenceLimit = new FeatureViewerDataTO(1, 9, "description", "1");
		FeatureViewerDataTO onLeftOfReference = new FeatureViewerDataTO(2, 7, "description", "1");
		
		FeatureViewerDataTO onRigthtOfReferenceLimit = new FeatureViewerDataTO(101, 110, "description", "1");
		FeatureViewerDataTO onRigthOfReference = new FeatureViewerDataTO(105, 115, "description", "1");
		
		Assert.assertFalse(reference.isOnRigth((LimitsInsedeReference)));
		
		Assert.assertFalse(LimitsInsedeReference.isOnRigth((reference)));
		
		Assert.assertFalse(reference.isOnRigth((sameReference)));
		Assert.assertFalse(sameReference.isOnRigth((reference)));
		
		
		Assert.assertFalse(reference.isOnRigth((insedeReference)));

		Assert.assertFalse(reference.isOnRigth((onLeftOfReferenceLimit)));
		Assert.assertFalse(reference.isOnRigth((onLeftOfReference)));
		
		Assert.assertTrue(reference.isOnRigth((onRigthtOfReferenceLimit)));
		Assert.assertTrue(reference.isOnRigth((onRigthOfReference)));
		
		
	}
	
	
	@Test
	public void testName() throws Exception {
		FeatureViewerDataTO interval1 = new FeatureViewerDataTO(5, 18, "description", "1");
		FeatureViewerDataTO interval2 = new FeatureViewerDataTO(30, 55, "description", "1");
		FeatureViewerDataTO interval3 = new FeatureViewerDataTO(70, 80, "description", "1");
		FeatureViewerDataTO interval4 = new FeatureViewerDataTO(82, 99, "description", "1");
		
		FeatureViewerDataTO intervalA = new FeatureViewerDataTO(1, 20, "description", "1");
		FeatureViewerDataTO intervalB = new FeatureViewerDataTO(35, 50, "description", "1");
		FeatureViewerDataTO intervalC = new FeatureViewerDataTO(60, 78, "description", "1");
		FeatureViewerDataTO intervalD = new FeatureViewerDataTO(90, 99, "description", "1");
		
		FeatureViewerDataTO intervalX = new FeatureViewerDataTO(99, 110, "description", "1");
		
		FeatureViewerDataTO intervalY = new FeatureViewerDataTO(100, 110, "description", "1");
		
		Assert.assertTrue(interval1.intersects(intervalA));
		Assert.assertTrue(interval2.intersects(intervalB));
		Assert.assertTrue(interval3.intersects(intervalC));
		Assert.assertTrue(interval4.intersects(intervalD));
		
		Assert.assertTrue(interval4.intersects(interval4));
		
		Assert.assertTrue(interval4.intersects(intervalX));
		Assert.assertFalse(interval4.intersects(intervalY));
		

		Assert.assertFalse(interval1.intersects(intervalB));
		
	}	

}
