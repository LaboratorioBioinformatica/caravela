package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Sample;

public interface GeneProductDAO extends DAO<GeneProduct> {
	GeneProduct findGeneProductByFeatureId(Long featureId);
	List<GeneProduct> findBySampleAndGeneProductName(Sample sample, String geneProductName);

}
