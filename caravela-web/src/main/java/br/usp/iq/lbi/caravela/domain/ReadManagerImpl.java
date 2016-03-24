package br.usp.iq.lbi.caravela.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@ApplicationScoped
public class ReadManagerImpl implements ReadManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ReadManagerImpl.class);
	
	private static final long TREE_ROOT_ELEMENT = 1l;
	
	@Inject private TaxonDAO taxonDAO;
	private Map<Long, Taxon> taxons = new HashMap<Long, Taxon>();
	
	public synchronized HashMap<String, Taxon> loadLineagem(Read read) {
		
		loadTaxonsToMemory();
		
		HashMap<String, Taxon> lineagem = new HashMap<String, Taxon>();
		
		Taxon taxon = read.getTaxon();
		do {
			lineagem.put(taxon.getRank(), taxon);
			taxon = taxons.get(taxon.getTaxonomyParentId());
		} while (taxon.getId() != TREE_ROOT_ELEMENT && taxon.getTaxonomyParentId() != TREE_ROOT_ELEMENT);
		read.setLineagem(lineagem);
		return lineagem;
	}

	private void loadTaxonsToMemory() {
		if(taxons.isEmpty()){
			List<Taxon> allTaxons = taxonDAO.findAll();
			for (Taxon taxon : allTaxons) {
				taxons.put(taxon.getTaxonomyId(), taxon);
			}
			logger.info("########################################################################");
			logger.info("total of taxons loading: " + taxons.size());
			logger.info("########################################################################");
		}
	}

}
