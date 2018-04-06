package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.Study;


public interface SampleDAO extends DAO<Sample> {
	
	List<Sample> listAllByStudy(Study study);
	List<Sample> listAllByStatus(SampleStatus status);
	List<Sample> listAllActiveSampleByStudy(Study study);
	List<Sample> findLastSamplesByStatus(SampleStatus status, Integer maxResult);
	
	
}
