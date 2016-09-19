package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleStatus;

@RequestScoped
public class SampleProcessorManagerImpl implements SampleProcessorManager {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleProcessorManagerImpl.class);
	
	@Inject SampleDAO sampleDAO;
	@Inject SampleLoader sampleLoader;
	
	public void processAllSamplesUploaded(){
		logger.info("START process All sample uploaded");
		List<Sample> samplesToBeProcessed = sampleDAO.listAllByStatus(SampleStatus.UPLOADED);
		logger.info("Total af Sample to be processed: " + samplesToBeProcessed.size());
		for (Sample sample : samplesToBeProcessed) {
			try {
				sampleLoader.loadFromFileToDatabase(sample.getId());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		logger.info("END process All sample uploaded");
		
		
	}
	

}
