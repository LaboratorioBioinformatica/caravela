package br.usp.iq.lbi.caravela.intervalTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class IntervalTest {
	
	@Test
	public void testToSring() throws Exception {
		long start = 1;
		long end = 50;
		String data = "data";
		Interval<String> target = new Interval<String>(start, end, data);
		Assert.assertEquals("1:50", target.toString());
	}
	
	@Test
	public void testGetters() throws Exception {
		long start = 1;
		long end = 50;
		String data = "data";
		Interval<String> target = new Interval<String>(start, end, data);
		Assert.assertEquals(start, target.getStart());
		Assert.assertEquals(end, target.getEnd());
		Assert.assertEquals(data, target.getData());
	}
	
	@Test
	public void testContains() throws Exception {
		String data = "data";
		Interval<String> reference = new Interval<String>(10, 20, data);
		Assert.assertTrue(reference.contains(10));
		Assert.assertTrue(reference.contains(15));
		Assert.assertTrue(reference.contains(20));
		Assert.assertFalse(reference.contains(9));
		Assert.assertFalse(reference.contains(21));

	}
	
	@Test
	public void testIntersects() throws Exception {
		String data = "data";
		Interval<String> reference = new Interval<String>(10, 20, data);
		Interval<String> sameReference = new Interval<String>(10, 20, data);
		Interval<String> intersectcsReferenceOnLeft = new Interval<String>(5, 12, data);
		Interval<String> intersectcsReferenceOnRight = new Interval<String>(18, 25, data);
		Interval<String> intersectcsReferenceInside = new Interval<String>(11, 19, data);
		
		Interval<String> noIntersectcsReferenceOnLeft = new Interval<String>(1, 9, data);
		Interval<String> noIntersectcsReferenceOnRight = new Interval<String>(21, 30, data);
		
		Assert.assertTrue(reference.intersects(sameReference));
		Assert.assertTrue(reference.intersects(intersectcsReferenceOnLeft));
		Assert.assertTrue(reference.intersects(intersectcsReferenceOnRight));
		Assert.assertTrue(reference.intersects(intersectcsReferenceInside));
		
		Assert.assertFalse(reference.intersects(noIntersectcsReferenceOnLeft));
		Assert.assertFalse(reference.intersects(noIntersectcsReferenceOnRight));

	}
	
	@Test
	public void testSort() throws Exception {
		String data = "data";
		Interval<String> first = new Interval<String>(1, 20, data);
		Interval<String> second = new Interval<String>(2, 15, data);
		Interval<String> third = new Interval<String>(25, 45, data);
		Interval<String> fourth = new Interval<String>(25, 50, data);
		Interval<String> fifth = new Interval<String>(25, 60, data);
		
		List<Interval<String>> intervalListNoSorted = new ArrayList<Interval<String>>();
		intervalListNoSorted.add(fifth);
		intervalListNoSorted.add(third);
		intervalListNoSorted.add(third);
		intervalListNoSorted.add(first);
		intervalListNoSorted.add(second);
		intervalListNoSorted.add(fourth);

		List<Interval<String>> intervalListSortedExpected = new ArrayList<Interval<String>>();
		intervalListSortedExpected.add(first);
		intervalListSortedExpected.add(second);
		intervalListSortedExpected.add(third);
		intervalListSortedExpected.add(third);
		intervalListSortedExpected.add(fourth);
		intervalListSortedExpected.add(fifth);
		
		
		Collections.sort(intervalListNoSorted);
		
		Assert.assertEquals(intervalListSortedExpected, intervalListNoSorted);
		
	}


}
