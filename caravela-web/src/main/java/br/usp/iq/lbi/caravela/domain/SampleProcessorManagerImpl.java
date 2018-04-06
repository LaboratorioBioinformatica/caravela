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
	public static final int MAX_RESULT = 5;

	@Inject SampleDAO sampleDAO;
	@Inject SampleLoader sampleLoader;

	
	public void processAllSamplesUploaded(){
		logger.info("START process All sample uploaded");
		List<Sample> samplesToBeProcessed = sampleDAO.findLastSamplesByStatus(SampleStatus.UPLOADED, MAX_RESULT);
		logger.info("Total of Sample to be processed: " + samplesToBeProcessed.size());
		for (Sample sample : samplesToBeProcessed) {
			try {
				logger.info("Sample name: " + sample.getName());
				sampleLoader.loadFromFileToDatabase(sample.getId());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		logger.info("END process All sample uploaded");
		
		
	}
	

}
