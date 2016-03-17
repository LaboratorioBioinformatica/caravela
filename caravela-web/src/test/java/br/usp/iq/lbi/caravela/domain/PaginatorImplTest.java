package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class PaginatorImplTest {
	
	
	@Test
	public void testGetPagesWhenTotalOfRecordIsEqualsNumberOfRecordPerPage() throws Exception {
		
		Long totalNumerOfRecord = 10l;
		Long numberOfRecordPerPage = 10l;
		
		List<IntervalPage> expectedIntervalPageList = new ArrayList<IntervalPage>();
		
		expectedIntervalPageList.add(new IntervalPage(0, 9));

		PaginatorImpl target = new PaginatorImpl();
		List<IntervalPage> pages = target.getPages(totalNumerOfRecord, numberOfRecordPerPage);
		
		Assert.assertEquals(expectedIntervalPageList, pages);
		
	}
	
	@Test
	public void testGetPagesWhenTotalOfRecordLessThanNumberOfRecordPerPage() throws Exception {
		
		Long totalNumerOfRecord = 9l;
		Long numberOfRecordPerPage = 10l;
		
		List<IntervalPage> expectedIntervalPageList = new ArrayList<IntervalPage>();
		
		expectedIntervalPageList.add(new IntervalPage(0, 8));

		PaginatorImpl target = new PaginatorImpl();
		List<IntervalPage> pages = target.getPages(totalNumerOfRecord, numberOfRecordPerPage);
		
		Assert.assertEquals(expectedIntervalPageList, pages);
		
	}
	
	@Test
	public void testGetPagesWhenNoRestFromDivisionTotalOfRecordByNumberOfRecordPerPage() throws Exception {
		
		Long totalNumerOfRecord = 100l;
		Long numberOfRecordPerPage = 10l;
		
		List<IntervalPage> expectedIntervalPageList = new ArrayList<IntervalPage>();
		expectedIntervalPageList.add(new IntervalPage(0, 9));
		expectedIntervalPageList.add(new IntervalPage(10, 19));
		expectedIntervalPageList.add(new IntervalPage(20, 29));
		expectedIntervalPageList.add(new IntervalPage(30, 39));
		expectedIntervalPageList.add(new IntervalPage(40, 49));
		expectedIntervalPageList.add(new IntervalPage(50, 59));
		expectedIntervalPageList.add(new IntervalPage(60, 69));
		expectedIntervalPageList.add(new IntervalPage(70, 79));
		expectedIntervalPageList.add(new IntervalPage(80, 89));
		expectedIntervalPageList.add(new IntervalPage(90, 99));
		
		PaginatorImpl target = new PaginatorImpl();
		List<IntervalPage> pages = target.getPages(totalNumerOfRecord, numberOfRecordPerPage);
		
		Assert.assertEquals(expectedIntervalPageList, pages);
		
	}
	
	@Test
	public void testGetPagesWhenThereIsRestFromDivisionTotalOfRecordByNumberOfRecordPerPage() throws Exception {
		
		Long totalNumerOfRecord = 103l;
		Long numberOfRecordPerPage = 10l;
		
		List<IntervalPage> expectedIntervalPageList = new ArrayList<IntervalPage>();
		expectedIntervalPageList.add(new IntervalPage(0, 9));
		expectedIntervalPageList.add(new IntervalPage(10, 19));
		expectedIntervalPageList.add(new IntervalPage(20, 29));
		expectedIntervalPageList.add(new IntervalPage(30, 39));
		expectedIntervalPageList.add(new IntervalPage(40, 49));
		expectedIntervalPageList.add(new IntervalPage(50, 59));
		expectedIntervalPageList.add(new IntervalPage(60, 69));
		expectedIntervalPageList.add(new IntervalPage(70, 79));
		expectedIntervalPageList.add(new IntervalPage(80, 89));
		expectedIntervalPageList.add(new IntervalPage(90, 99));
		expectedIntervalPageList.add(new IntervalPage(100, 102));
		
		PaginatorImpl target = new PaginatorImpl();
		List<IntervalPage> pages = target.getPages(totalNumerOfRecord, numberOfRecordPerPage);
		
		Assert.assertEquals(expectedIntervalPageList, pages);
		
		
	}
	

}
