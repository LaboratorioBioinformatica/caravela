package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.FeatureAnnotation;

public interface FeatureAnnotationDAO extends DAO<FeatureAnnotation>{
	 List<FeatureAnnotation> findFeatureAnnotationsByFeatureId(Long featureId);

}
