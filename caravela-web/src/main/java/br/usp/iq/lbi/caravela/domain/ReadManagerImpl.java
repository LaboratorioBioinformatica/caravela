package br.usp.iq.lbi.caravela.domain;

import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@ApplicationScoped
public class ReadManagerImpl implements ReadManager {
	
	private static final long TREE_ROOT_ELEMENT = 1l;
	
	@Inject NCBITaxonFinder ncbiTaxonFinder;
	
	public synchronized HashMap<String, Taxon> loadLineagem(Read read) {
		
		HashMap<String, Taxon> lineagem = new HashMap<String, Taxon>();
		
		Taxon taxon = read.getTaxon();
		do {
			lineagem.put(taxon.getRank(), taxon);
			taxon = ncbiTaxonFinder.searchTaxonByNCBITaxonomyId(taxon.getTaxonomyParentId());
		} while (taxon.getId() != TREE_ROOT_ELEMENT && taxon.getTaxonomyParentId() != TREE_ROOT_ELEMENT);
		read.setLineagem(lineagem);
		return lineagem;
	}


}
