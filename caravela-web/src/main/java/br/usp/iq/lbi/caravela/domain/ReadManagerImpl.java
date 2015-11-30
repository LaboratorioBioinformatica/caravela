package br.usp.iq.lbi.caravela.domain;

import java.util.HashMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class ReadManagerImpl implements ReadManager {
	
	private static final long TREE_ROOT_ELEMENT = 1l;
	
	@Inject private TaxonDAO taxonDAO;
	
	public HashMap<String, Taxon> loadLineagem(Read read) {
		HashMap<String, Taxon> lineagem = new HashMap<String, Taxon>();
		Taxon taxon = read.getTaxon();
		do {
			lineagem.put(taxon.getRank(), taxon);
			//TODO ISTO DEVERIA ESTAR EM MEMÓRIA!
			taxon = taxonDAO.findByTaxonomicId(taxon.getTaxonomyParentId());
			
		} while (taxon.getId() != TREE_ROOT_ELEMENT && taxon.getTaxonomyParentId() != TREE_ROOT_ELEMENT);
		read.setLineagem(lineagem);
		return lineagem;
	}

}
