package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Sample;

public interface GeneSearch {
	List<Contig> SearchContigListBySampleAndGeneProductSource(Sample sample, String geneProductSource);
	List<Feature> SearchFeatureListBySampleAndGeneProductName(Sample sample, String geneProductName);

}
