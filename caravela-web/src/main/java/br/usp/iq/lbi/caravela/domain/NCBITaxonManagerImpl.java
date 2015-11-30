package br.usp.iq.lbi.caravela.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class NCBITaxonManagerImpl implements NCBITaxonManager {
	
	private static final Logger logger = LoggerFactory.getLogger(NCBITaxonManagerImpl.class);
	
	private static final String DELIMITER_PATTERN = "\\||\\n";
	@Inject private TaxonDAO taxonDAO;

	
	public Long countNumberOfTaxon() {
		return taxonDAO.count();
	}

	public void clear() {
		taxonDAO.truncateTaxonTable();
		
	}

	public void register(File fileNCBIScientificNames, File fileNCBINodes) throws FileNotFoundException {
		FileReader fileReaderNCBIScientificNames = new FileReader(fileNCBIScientificNames);
		HashMap<Long, String> scientificNamehashMap = new HashMap<Long, String>();
		
		Scanner scannerNames = new Scanner(fileReaderNCBIScientificNames);
		scannerNames.useDelimiter(DELIMITER_PATTERN);
		while (scannerNames.hasNext()) {
			
			Long taxaId = new Long(scannerNames.next().trim());
			String scientificName = scannerNames.next().trim();
			scientificNamehashMap.put(taxaId, scientificName);
		}
		scannerNames.close();
		
		
		FileReader fileReaderNCBINodes = new FileReader(fileNCBINodes);
		
		Scanner scannerNodes = new Scanner(fileReaderNCBINodes);
		scannerNodes.useDelimiter(DELIMITER_PATTERN);
		while (scannerNodes.hasNext()) {
			Long taxaId = new Long(scannerNodes.next().trim());
			Long parentTaxaId = new Long(scannerNodes.next().trim());
			String rank = scannerNodes.next().trim();
			taxonDAO.addBatch(new Taxon(taxaId, parentTaxaId, scientificNamehashMap.get(taxaId), rank));
		}

		scannerNodes.close();
		
	}

	public Map<Long, Taxon> SearchAllTaxon() {
		HashMap<Long, Taxon> allTaxonHashMap = new HashMap<Long, Taxon>();
		List<Taxon> allTaxon = taxonDAO.findAll();
		for (Taxon taxon : allTaxon) {
			allTaxonHashMap.put(taxon.getTaxonomyId(), taxon);
		}
		return allTaxonHashMap;
	}

	public Taxon searchByTaxonomicId(Long taxonomicId) {
		Taxon taxon = null;
		try {
			taxon = taxonDAO.findByTaxonomicId(taxonomicId);
		} catch (NoResultException nre) {
			logger.warn("Taxonomic Id: " + taxonomicId +" Not found", nre);
		}
		return taxon;
	}
	
	

}
