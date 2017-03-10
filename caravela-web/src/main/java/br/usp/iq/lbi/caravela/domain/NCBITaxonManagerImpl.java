package br.usp.iq.lbi.caravela.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.domain.exception.ServiceAlreadyIsRunningException;
import br.usp.iq.lbi.caravela.model.Taxon;

@ApplicationScoped
public class NCBITaxonManagerImpl implements NCBITaxonManager {
	
	
	private static final Logger logger = LoggerFactory.getLogger(NCBITaxonManagerImpl.class);
	
	
	private static final double ONE = 1d;
	private Boolean isRegisterRunning = Boolean.FALSE;

	
	private static final String DELIMITER_PATTERN = "\\||\\n";
	@Inject private TaxonDAO taxonDAO;

	
	public Long countNumberOfTaxon() {
		return taxonDAO.count();
	}

	public void clear() {
		if( ! isRegisterRunning){
			taxonDAO.truncateTaxonTable();
		} else {
			throw new ServiceAlreadyIsRunningException("Action can not be done because NCBI Taxon file loader is running!");
		}
			
	}
	
	public boolean isRegisterRunning() {
		return isRegisterRunning;
	}
	
	public Boolean isClean() {
		Long count = taxonDAO.count();
		if (count < ONE) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	

	public void register(File fileNCBIScientificNames, File fileNCBINodes) throws FileNotFoundException, ServiceAlreadyIsRunningException {
		
		if( ! isRegisterRunning){
			
			try {
				logger.info("NCBI Taxon file loader START");
				isRegisterRunning = Boolean.TRUE;
				
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
				logger.info("NCBI Taxon file loader END");
				
			} finally {
				isRegisterRunning = Boolean.FALSE;
			}
		} else {
			throw new ServiceAlreadyIsRunningException("NCBI Taxon file loader already is running!");
		}
		
						
	}
	
	

}
