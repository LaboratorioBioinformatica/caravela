package br.usp.iq.lbi.caravela.dto.featureViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;


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
