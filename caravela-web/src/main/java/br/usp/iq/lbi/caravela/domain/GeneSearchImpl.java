package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.FeatureDAO;
import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;

@RequestScoped
public class GeneSearchImpl implements GeneSearch {
	
	@Inject private ReadDAO readDAO;
	@Inject private ContigDAO contiDao;
	@Inject private TaxonDAO taxonDAO;
	@Inject private FeatureDAO featureDAO;
	
	public List<Contig> SearchContigListBySampleAndGeneProductSource(Sample sample, String geneProductSource) {
		return featureDAO.FindBySampleAndGeneProductSource(sample, geneProductSource);
	}

}
