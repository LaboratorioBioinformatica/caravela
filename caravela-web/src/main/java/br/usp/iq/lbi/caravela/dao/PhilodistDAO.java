package br.usp.iq.lbi.caravela.dao;

import br.usp.iq.lbi.caravela.model.Philodist;

public interface PhilodistDAO extends DAO<Philodist> {
	Philodist findPhilodistByFeatureId(Long featureId);

}
