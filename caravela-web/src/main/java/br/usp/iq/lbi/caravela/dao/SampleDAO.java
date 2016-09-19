package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.Treatment;


public interface SampleDAO extends DAO<Sample> {
	
	List<Sample> listAllByTreatment(Treatment treatment);
	List<Sample> listAllByStatus(SampleStatus status);
	
	
}
