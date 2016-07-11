package br.usp.iq.lbi.caravela.domain;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;

@RequestScoped
public class SampleLoaderImpl implements SampleLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleLoaderImpl.class);
	

	@Override
	public void loadFromFileToDatabase(Sample sample) {
		try {
			logger.info("Loading Sample File: " + sample.getName());
			SampleFile sampleFile = sample.getFileWithAllInformation();
			
			Stream<String> stream = Files.lines(Paths.get(sampleFile.getFilePath()));
			long totalNumberOfLines = stream.count();
			
			logger.info("number of contig to load: " + totalNumberOfLines);
			System.out.println(totalNumberOfLines);
			
		} catch (Exception e) {
			logger.error("Error to load sample file", e);
		}
		
		
	}

}
