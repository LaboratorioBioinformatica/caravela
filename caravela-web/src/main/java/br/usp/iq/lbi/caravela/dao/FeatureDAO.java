package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Sample;

public interface FeatureDAO extends DAO<Feature>  {
	List<Feature> loadAllFeaturesByContig(Contig contig);
	List<Contig> FindBySampleAndGeneProductSource(Sample sample, String geneProductSource);
	List<Feature> FindBySampleAndGeneProductName(Sample sample, String geneProductName);

}
