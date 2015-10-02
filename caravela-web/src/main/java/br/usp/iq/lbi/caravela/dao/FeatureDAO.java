package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;

public interface FeatureDAO extends DAO<Feature>  {
	List<Feature> loadAllFeaturesByContig(Contig contig);

}
