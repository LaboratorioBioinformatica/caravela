package br.usp.iq.lbi.caravela.domain;

import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.dao.FeatureDAO;
import br.usp.iq.lbi.caravela.dao.GeneProductDAO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Sample;

@RequestScoped
public class GeneSearchImpl implements GeneSearch {
	
	@Inject private FeatureDAO featureDAO;
	@Inject private GeneProductDAO geneProductDAO;
	
	
	public List<Contig> searchContigListBySampleAndGeneProductSource(Sample sample, String geneProductSource) {
		return featureDAO.FindBySampleAndGeneProductSource(sample, geneProductSource);
	}
	
	public HashSet<GeneProduct> searchGeneProductBySampleAndGeneProductName(Sample sample, String geneProductName){
		 List<GeneProduct> geneProductList = geneProductDAO.findBySampleAndGeneProductName(sample, geneProductName);
		 HashSet<GeneProduct> hashSetGeneProduct = new HashSet<GeneProduct>(geneProductList);
		 return hashSetGeneProduct;
	}
	
	
	
	

}
