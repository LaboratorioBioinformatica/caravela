package br.usp.iq.lbi.caravela.domain;

import java.util.List;

public interface Paginator {
	
	List<IntervalPage> getPages(Long totalNumerOfRecord, Long numberOfRecordPerPage);
}
