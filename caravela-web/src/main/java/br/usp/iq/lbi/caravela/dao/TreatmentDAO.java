package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Treatment;


public interface TreatmentDAO extends DAO<Treatment> {

	List<Treatment> listAll();
	Treatment load(Treatment treatment);

}
