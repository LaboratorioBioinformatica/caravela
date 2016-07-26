package br.usp.iq.lbi.caravela.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.SampleFileDAO;
import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;

@RequestScoped
public class SampleLoaderImpl implements SampleLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleLoaderImpl.class);
	
	@Inject private  ContigTOProcessor contigTOProcessor;
	@Inject private SampleDAO sampleDAO;
	@Inject private SampleFileDAO sampleFileDAO;
	@Inject private SampleReporter sampleReporter;
	
	public SampleLoaderImpl() {}
	
	public SampleLoaderImpl(ContigTOProcessor contigTOProcessor) {
		this.contigTOProcessor = contigTOProcessor;
	}
	

	@Override
	public void loadFromFileToDatabase(Sample sample) throws Exception {
		
		try {
			SampleFile sampleFile = sample.getFileWithAllInformation();
			logger.info("Loading Sample File: " + sample.getName());
			Path path = Paths.get(sampleFile.getFilePath());
			
			
			Long totalNumberOfContigToBeLoad = getTotalNumberOfFileLines(path);
			logger.info("number of contig to load: " + totalNumberOfContigToBeLoad);
			
			long startTime = System.currentTimeMillis();
	
			Gson gson = new Gson();
			Stream<String> stream = Files.lines(path);
			
			stream.forEach(c-> {
				contigTOProcessor.convert(sample, gson.fromJson(c, ContigTO.class));
			});
			
			stream.close();
			long endTime = System.currentTimeMillis();
			
			logger.info("Total time to load: " + (endTime - startTime));
			
			logger.info("Strat report: ");
			
			long startTimeToReport = System.currentTimeMillis();
			
			sampleReporter.reportChimericPotentialFromContig(sample, 0d, "genus");
			
			long endTimeToReport = System.currentTimeMillis();
			logger.info("Total time to Report: " + (endTimeToReport - startTimeToReport));
			
			
			sample.toProccessed();
			
			SampleFile sampleFileWithAllInformation = sample.getFileWithAllInformation();
			sampleFileWithAllInformation.toLoaded();
	
			sampleFileDAO.update(sampleFileWithAllInformation);
			sampleDAO.update(sample);
			
			
			
		} catch (Exception e) {
			logger.error("Error to load sample file", e);
			throw e;
		}
		
		
	}
	
	private Long getTotalNumberOfFileLines(Path path) throws IOException{
		Long totalLines = 0l;
		try (Stream<String> stream = Files.lines(path)) {
			totalLines = stream.count();
		} catch (IOException e) {
			logger.error("Error to count sample file", e);
			throw e;
		}
		return totalLines;
	}

}
