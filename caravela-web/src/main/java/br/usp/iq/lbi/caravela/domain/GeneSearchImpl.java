package br.usp.iq.lbi.caravela.domain;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.dao.TaxonDAO;

@RequestScoped
public class GeneSearchImpl {
	
	@Inject private ReadDAO readDAO;
	@Inject private ContigDAO contiDao;
	@Inject private TaxonDAO taxonDAO;

}
