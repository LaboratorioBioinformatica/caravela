package br.usp.iq.lbi.caravela.domain;

import java.util.HashSet;
import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Sample;

public interface GeneSearch {
	List<Contig> searchContigListBySampleAndGeneProductSource(Sample sample, String geneProductSource);
	HashSet<GeneProduct> searchGeneProductBySampleAndGeneProductName(Sample sample, String geneProductName);

}
