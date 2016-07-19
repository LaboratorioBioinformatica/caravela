package br.usp.iq.lbi.caravela.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.model.Taxon;

@ApplicationScoped
public class NCBITaxonFinderImpl implements NCBITaxonFinder {

	private static final Logger logger = LoggerFactory.getLogger(NCBITaxonFinderImpl.class);
	
	@Inject TaxonDAO taxonDAO;
	private Map<Long, Taxon> taxons = new HashMap<Long, Taxon>();
	
	public NCBITaxonFinderImpl() {}
	
	public NCBITaxonFinderImpl(TaxonDAO taxonDAO) {
		this.taxonDAO = taxonDAO;
	}

	@Override
	public Taxon searchTaxonByNCBITaxonomyId(Long ncbiTaxonomyId) {
		loadTaxonsToMemory(); //maybe should be at constructor
		return taxons.get(ncbiTaxonomyId);
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
