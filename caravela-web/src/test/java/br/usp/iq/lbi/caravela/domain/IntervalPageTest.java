package br.usp.iq.lbi.caravela.domain;


import org.junit.Assert;
import org.junit.Test;


public class IntervalPageTest {
	
	@Test
	public void testGetterAndSetters() throws Exception {
		
		Integer start = 1;
		Integer end = 100;
		IntervalPage target = new IntervalPage(start, end);
		
		Assert.assertEquals(start, target.getStart());
		Assert.assertEquals(end, target.getEnd());
		
	}
	
	@Test
	public void testEquals() throws Exception {
		
		IntervalPage target = new IntervalPage(0, 9);
		IntervalPage targetEquals = new IntervalPage(0, 9);
		IntervalPage targetNotEquals = new IntervalPage(0, 10);
		
		Assert.assertTrue(target.equals(targetEquals));
		Assert.assertFalse(target.equals(targetNotEquals));
	}

}
