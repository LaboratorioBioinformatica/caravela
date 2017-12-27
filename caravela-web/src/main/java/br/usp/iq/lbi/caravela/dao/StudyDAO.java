package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Study;


public interface StudyDAO extends DAO<Study> {

	List<Study> listAll();
	Study load(Study study);

}
