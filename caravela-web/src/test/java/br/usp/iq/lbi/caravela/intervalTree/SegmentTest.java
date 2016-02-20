package br.usp.iq.lbi.caravela.intervalTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.usp.iq.lbi.caravela.model.Taxon;

public class SegmentTest {
	
	@Test
	public void testGettersWhenTypeIsTaxon() throws Exception {
		Integer x = 1;
		Integer y = 100;
		List<Taxon> dataList = createTaxonDataList(1l,1l,"taxon scientifc name", "taxon hank");
		
		Segment<Taxon> target = new Segment<Taxon>(x, y, dataList);
		Assert.assertEquals(x, target.getX());
		Assert.assertEquals(y, target.getY());
		Assert.assertEquals(dataList, target.getData());
		
	}
	
	@Test
	public void testSort() {
		List<Segment<Taxon>> list = new ArrayList<Segment<Taxon>>();
		List<Taxon> dataList = createTaxonDataList(1l,1l,"taxon scientifc name", "taxon hank");
		list.add(new Segment<Taxon>(1, 10, dataList));
		list.add(new Segment<Taxon>(7, 15, dataList));
		list.add(new Segment<Taxon>(18, 23, dataList));
		list.add(new Segment<Taxon>(7, 13, dataList));
		list.add(new Segment<Taxon>(1, 10, dataList));
		list.add(new Segment<Taxon>(20, 22, dataList));
		
		Collections.sort(list);
		
		List<Segment<Taxon>> sortedlist = new ArrayList<Segment<Taxon>>();
		sortedlist.add(new Segment<Taxon>(1, 10, dataList));
		sortedlist.add(new Segment<Taxon>(1, 10, dataList));
		sortedlist.add(new Segment<Taxon>(7, 13, dataList));
		sortedlist.add(new Segment<Taxon>(7, 15, dataList));
		sortedlist.add(new Segment<Taxon>(18, 23, dataList));
		sortedlist.add(new Segment<Taxon>(20, 22, dataList));
		 
		Iterator<Segment<Taxon>> targetIt = list.iterator();
		
		for (Segment<Taxon> segmentTaxon : sortedlist) {
			Assert.assertEquals(segmentTaxon, targetIt.next());
			
		}
		
	}
	
	@Test
	public void testEquals() throws Exception {
		List<Taxon> dataList1 = createTaxonDataList(1l,1l,"taxon scientifc name", "taxon hank");
		List<Taxon> dataList2 = createTaxonDataList(5l,1l,"taxon scientifc name", "taxon hank");
		
		Segment<Taxon> target = new Segment<Taxon>(1, 100, dataList1);
		Segment<Taxon> targetEquals = new Segment<Taxon>(1, 100, dataList1);
		Segment<Taxon> targetDifentData = new Segment<Taxon>(1, 100, dataList2);
		Segment<Taxon> targetDiferentX = new Segment<Taxon>(2, 100, dataList1);
		Segment<Taxon> targetDiferentY = new Segment<Taxon>(1, 99, dataList1);
		
		Assert.assertTrue(target.equals(target));
		Assert.assertTrue(target.equals(targetEquals));
		Assert.assertFalse(target.equals(targetDifentData));
		Assert.assertFalse(target.equals(targetDiferentX));
		Assert.assertFalse(target.equals(targetDiferentY));
		
		
	}
	
	@Test
	public void testUnion() throws Exception {
		
		List<Taxon> dataList1 = createTaxonDataList(1l,1l,"taxon scientifc name", "taxon hank");
		List<Taxon> dataListNOTaxon = createTaxonDataList(0l, 0l,"no taxon", "no taxon");
		
		Segment<Taxon> reference = new Segment<Taxon>(10, 100, dataList1);
		Segment<Taxon> sameReference = new Segment<Taxon>(10, 100, dataList1);
		
		Segment<Taxon> sameCoordinatesReferenceAndDiferentData = new Segment<Taxon>(10, 100, dataListNOTaxon);
		
		Segment<Taxon> LimitsInsedeReference = new Segment<Taxon>(11, 99, dataList1);
		Segment<Taxon> insedeReference = new Segment<Taxon>(20, 29, dataList1);
		
		Segment<Taxon> IntersectOnLeftOfReference = new Segment<Taxon>(1, 15, dataList1);
		Segment<Taxon> IntersectOnLimitLeftOfReference = new Segment<Taxon>(1, 10, dataList1);
		
		Segment<Taxon> NoIntersectOnLeftOfReference = new Segment<Taxon>(1, 5, dataList1);
		Segment<Taxon> NoIntersectOnLimitLeftOfReference = new Segment<Taxon>(1, 9, dataList1);
		
		Segment<Taxon> IntersectOnLimitRigthOfReference = new Segment<Taxon>(100, 110, dataList1);
		Segment<Taxon> IntersectOnRigthOfReference = new Segment<Taxon>(90, 115, dataList1);
		
		Segment<Taxon> NoIntersectOnLimitRigthOfReference = new Segment<Taxon>(101, 110, dataList1);
		Segment<Taxon> NoIntersectOnRigthOfReference = new Segment<Taxon>(150, 180, dataList1);
		
		
		Assert.assertEquals(new Segment<Taxon>(10, 100, dataList1), reference.union(sameReference));
		Assert.assertEquals(new Segment<Taxon>(10, 100, dataList1), sameReference.union(reference));
		
		Assert.assertNotEquals(new Segment<Taxon>(10, 100, dataList1), reference.union(sameCoordinatesReferenceAndDiferentData));
		
		Assert.assertEquals(new Segment<Taxon>(10, 100, dataList1), reference.union(LimitsInsedeReference));
		
		Assert.assertEquals(new Segment<Taxon>(10, 100, dataList1), LimitsInsedeReference.union(reference));
		
		Assert.assertEquals(new Segment<Taxon>(10, 100, dataList1), reference.union(insedeReference));
		Assert.assertEquals(new Segment<Taxon>(1, 100, dataList1), reference.union(IntersectOnLeftOfReference));
		Assert.assertEquals(new Segment<Taxon>(1, 100, dataList1), reference.union(IntersectOnLimitLeftOfReference));
		
		Assert.assertNull(reference.union(NoIntersectOnLeftOfReference));
		Assert.assertNull(reference.union(NoIntersectOnLimitLeftOfReference));

		
		Assert.assertEquals(new Segment<Taxon>(10, 110, dataList1), reference.union(IntersectOnLimitRigthOfReference));
		Assert.assertEquals(new Segment<Taxon>(10, 115, dataList1), reference.union(IntersectOnRigthOfReference));

		Assert.assertNull(reference.union(NoIntersectOnRigthOfReference));
		Assert.assertNull(reference.union(NoIntersectOnLimitRigthOfReference));
		
	}
	
	@Test
	public void testIntersects() throws Exception {
		List<Taxon> dataList = createTaxonDataList(1l,1l,"taxon scientifc name", "taxon hank");
		Segment<Taxon> interval1 = new Segment<Taxon>(1, 100, dataList);
		Segment<Taxon> interval2 = new Segment<Taxon>(1, 100, dataList);
		Segment<Taxon> interval3 = new Segment<Taxon>(30, 50, dataList);
		Segment<Taxon> interval4 = new Segment<Taxon>(1, 29, dataList);
		Segment<Taxon> interval5 = new Segment<Taxon>(51, 90, dataList);
		
		Segment<Taxon> interval6 = new Segment<Taxon>(1, 30, dataList);
		Segment<Taxon> interval7 = new Segment<Taxon>(50, 90, dataList);
		
		
		
		Assert.assertTrue(interval1.intersects(interval2));
		Assert.assertTrue(interval2.intersects(interval1));
		
		Assert.assertFalse(interval3.intersects(interval4));
		Assert.assertFalse(interval3.intersects(interval5));
		
		Assert.assertTrue(interval3.intersects(interval6));
		Assert.assertTrue(interval3.intersects(interval7));
		
	}
	
	@Test
	public void testCotains() throws Exception {
		
		List<Taxon> dataList = createTaxonDataList(1l,1l,"taxon scientifc name", "taxon hank");
		
		Segment<Taxon> reference = new Segment<Taxon>(10, 100, dataList);
		Segment<Taxon> sameReference = new Segment<Taxon>(10, 100, dataList);
		Segment<Taxon> LimitsInsedeReference = new Segment<Taxon>(11, 99, dataList);
		Segment<Taxon> insedeReference = new Segment<Taxon>(20, 29, dataList);
		
		Segment<Taxon> onLeftOfReferenceLimit = new Segment<Taxon>(1, 9, dataList);
		Segment<Taxon> onLeftOfReference = new Segment<Taxon>(2, 7, dataList);
		
		Segment<Taxon> onRigthtOfReferenceLimit = new Segment<Taxon>(101, 110, dataList);
		Segment<Taxon> onRigthOfReference = new Segment<Taxon>(105, 115, dataList);
		
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
	public void getIntersect() throws Exception {
		
		List<Taxon> dataListTaxonA = createTaxonDataList(1l,1l,"A", "taxon hank A");
		List<Taxon> dataListTaxonB = createTaxonDataList(2l, 1l,"B", "taxon hank B");
		
		Segment<Taxon> reference = new Segment<Taxon>(10, 100, dataListTaxonA);
		Segment<Taxon> sameReference = new Segment<Taxon>(10, 100, dataListTaxonB);
		
		Segment<Taxon> sameCoordinatesReferenceAndSameDataList = new Segment<Taxon>(10, 100, dataListTaxonA);
		
		Segment<Taxon> insideReference = new Segment<Taxon>(20, 29, dataListTaxonB);
		Segment<Taxon> onLimitOfRigthOfReference = new Segment<Taxon>(100, 110, dataListTaxonB);
		Segment<Taxon> onRigthOfReference = new Segment<Taxon>(80, 115, dataListTaxonB);
		Segment<Taxon> limitsInsedeReference = new Segment<Taxon>(11, 99, dataListTaxonB);
		Segment<Taxon> OnLimitOfLeftOfReference = new Segment<Taxon>(1, 10, dataListTaxonB);
		Segment<Taxon> onLeftOfReference = new Segment<Taxon>(5, 25, dataListTaxonB);
		
		Segment<Taxon> outOfLimitOfRigthtOfReference = new Segment<Taxon>(101, 110, dataListTaxonB);
		Segment<Taxon> outOfLimitOfLeftOfReference = new Segment<Taxon>(1, 9, dataListTaxonB);
		
		List<Taxon> taxonList = new ArrayList<Taxon>();
		taxonList.addAll(dataListTaxonA);
		taxonList.addAll(dataListTaxonB);
		
		Segment<Taxon> segmentSameReference = new Segment<Taxon>(10, 100, taxonList);
		Segment<Taxon> segmentsameCoordinatesReferenceAndSameDataList = new Segment<Taxon>(10, 100, dataListTaxonA);
		Segment<Taxon> segmentInsedeReference = new Segment<Taxon>(20, 29, taxonList);
		Segment<Taxon> segmentOnLimitOfRigthtOfReference = new Segment<Taxon>(100, 100, taxonList);
		Segment<Taxon> segmentOnRigthOfReference = new Segment<Taxon>(80, 100, taxonList);
		Segment<Taxon> segmentLimitsInsedeReference = new Segment<Taxon>(11, 99, taxonList);
		Segment<Taxon> segmentOnLimitOfLeftOfReference = new Segment<Taxon>(10, 10, taxonList);
		Segment<Taxon> segmentOnLeftOfReference = new Segment<Taxon>(10, 25, taxonList);
		
		
		Assert.assertEquals(segmentSameReference, reference.getIntersect(sameReference));
		Assert.assertEquals(segmentsameCoordinatesReferenceAndSameDataList, reference.getIntersect(sameCoordinatesReferenceAndSameDataList));
		Assert.assertEquals(segmentInsedeReference, reference.getIntersect(insideReference));
		Assert.assertEquals(segmentOnLimitOfRigthtOfReference, reference.getIntersect(onLimitOfRigthOfReference));
		Assert.assertEquals(segmentOnRigthOfReference, reference.getIntersect(onRigthOfReference));
		Assert.assertEquals(segmentLimitsInsedeReference, reference.getIntersect(limitsInsedeReference));
		Assert.assertEquals(segmentOnLimitOfLeftOfReference, reference.getIntersect(OnLimitOfLeftOfReference));
		Assert.assertEquals(segmentOnLeftOfReference, reference.getIntersect(onLeftOfReference));

		Assert.assertNull(reference.getIntersect(outOfLimitOfRigthtOfReference));
		Assert.assertNull(reference.getIntersect(outOfLimitOfLeftOfReference));
		
	}
	
	@Test
	public void testSubtracionbetweenSegments() throws Exception {
		List<Taxon> dataListTaxonA = createTaxonDataList(1l,1l,"A", "taxon hank A");
		List<Taxon> dataListTaxonB = createTaxonDataList(2l, 1l,"B", "taxon hank B");
		
		Segment<Taxon> reference = new Segment<Taxon>(50, 80, dataListTaxonA);
		
		Segment<Taxon> sameReference = new Segment<Taxon>(50, 80, dataListTaxonA);
		Segment<Taxon> insideReference = new Segment<Taxon>(60, 70, dataListTaxonB);
		Segment<Taxon> intersecstReferenceOnRight = new Segment<Taxon>(30, 55, dataListTaxonB);
		
		Segment<Taxon> intersecstReferenceOnRightLimit = new Segment<Taxon>(50, 60, dataListTaxonB);
		
		Segment<Taxon> intersecstReferenceOnLeft = new Segment<Taxon>(70, 100, dataListTaxonB);
		
		
		
		List<Segment<Taxon>> subtractResultListEmpty = new ArrayList<Segment<Taxon>>();
		
		@SuppressWarnings("unchecked")
		List<Segment<Taxon>> subtractResultListWhenReferenceIsInside = createSubtractResultList(
				new Segment<Taxon>(50, 59, dataListTaxonA), 
				new Segment<Taxon>(71, 80, dataListTaxonA));
		
		@SuppressWarnings("unchecked")
		List<Segment<Taxon>> subtractResultListWhenOtherIntersectsReferenceOnRight = createSubtractResultList(
				new Segment<Taxon>(56, 80, dataListTaxonA));
		
		@SuppressWarnings("unchecked")
		List<Segment<Taxon>> subtractResultListWhenOtherIntersectsReferenceOnRightLimit = createSubtractResultList(
				new Segment<Taxon>(61, 80, dataListTaxonA));
		
		@SuppressWarnings("unchecked")
		List<Segment<Taxon>> subtractResultListWhenOtherIntersectsReferenceOnLeft = createSubtractResultList(
				new Segment<Taxon>(50, 69, dataListTaxonA));
		
		
		
		Assert.assertEquals(subtractResultListEmpty, reference.subtract(reference));
		Assert.assertEquals(subtractResultListEmpty, reference.subtract(sameReference));
		
		Assert.assertEquals(subtractResultListWhenReferenceIsInside, reference.subtract(insideReference));
		Assert.assertEquals(subtractResultListWhenOtherIntersectsReferenceOnRight, reference.subtract(intersecstReferenceOnRight));
		
		Assert.assertEquals(subtractResultListWhenOtherIntersectsReferenceOnRightLimit, reference.subtract(intersecstReferenceOnRightLimit));
		Assert.assertEquals(subtractResultListWhenOtherIntersectsReferenceOnLeft, reference.subtract(intersecstReferenceOnLeft));
		
	
	}
	
	@Test
	public void testSubtracionbetweenSegmentsWhenSubtrahendIsToLargeThanMinuendSegment() throws Exception {
		List<Taxon> dataListTaxonA = createTaxonDataList(1l,1l,"A", "taxon hank A");
		List<Taxon> dataListTaxonB = createTaxonDataList(2l, 1l,"B", "taxon hank B");
		
		Segment<Taxon> reference = new Segment<Taxon>(57, 285, dataListTaxonA);
		
		Segment<Taxon> subtrahend = new Segment<Taxon>(1, 444, dataListTaxonB);
		
		List<Segment<Taxon>> subtractResultListEmpty = new ArrayList<Segment<Taxon>>();
		Assert.assertEquals(subtractResultListEmpty, reference.subtract(subtrahend));
		
		
	}
	
	
	private List<Segment<Taxon>> createSubtractResultList(Segment<Taxon>... segments){
		List<Segment<Taxon>> resultList = new ArrayList<Segment<Taxon>>();
		for (Segment<Taxon> segment : segments) {
			resultList.add(segment);
		}
		return resultList;
	}

	private List<Taxon> createTaxonDataList(Long taxonomyId, Long taxonomyParentId, String scientificName, String rank) {
		List<Taxon> dataList = new ArrayList<Taxon>();
		Taxon taxon = new Taxon(taxonomyId, taxonomyParentId, scientificName, rank);
		dataList.add(taxon);
		return dataList;
	}

}
