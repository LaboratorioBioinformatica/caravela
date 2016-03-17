package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaginatorImpl implements Paginator {
	
	private static final Long ZERO = 0l;
	
	
	public List<IntervalPage> getPages(Long totalNumerOfRecord, Long numberOfRecordPerPage){
		
		ArrayList<IntervalPage> intervalPages = new ArrayList<IntervalPage>();
		
		if(totalNumerOfRecord < numberOfRecordPerPage){
			Integer start = ZERO.intValue();
			Integer end = totalNumerOfRecord.intValue()-1;
			intervalPages.add(new IntervalPage(start, end));
			
		} else {
	
			Long prevTotalPages = totalNumerOfRecord  / numberOfRecordPerPage;
			Long RestTotalPages = totalNumerOfRecord  % numberOfRecordPerPage;
			
			
			for (int i = 0; i < prevTotalPages; i++) {
				Long start = i * numberOfRecordPerPage;
				Long end = (i * numberOfRecordPerPage) + numberOfRecordPerPage - 1;
				intervalPages.add(new IntervalPage(start.intValue(), end.intValue()));
			}
			
			if( ! RestTotalPages.equals(ZERO)){
				IntervalPage lastInterval = intervalPages.get(intervalPages.size()-1);
				Integer end = lastInterval.getEnd() + RestTotalPages.intValue();
				Integer start = lastInterval.getEnd()+1;
				intervalPages.add(new IntervalPage(start, end));
			}
			
		}
		
		
		return intervalPages;
	}
	

}
