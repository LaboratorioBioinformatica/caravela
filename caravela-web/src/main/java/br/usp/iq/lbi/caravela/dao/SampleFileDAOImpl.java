package br.usp.iq.lbi.caravela.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.usp.iq.lbi.caravela.model.SampleFile;


public class SampleFileDAOImpl extends DAOImpl<SampleFile> implements SampleFileDAO {

	@Inject
	public SampleFileDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
