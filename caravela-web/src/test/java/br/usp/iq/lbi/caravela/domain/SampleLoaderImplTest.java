package br.usp.iq.lbi.caravela.domain;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.usp.iq.lbi.caravela.model.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import com.google.gson.Gson;

import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.dto.FeatureTO;
import br.usp.iq.lbi.caravela.dto.ReadOnContigTO;
import br.usp.iq.lbi.caravela.model.Study;

public class SampleLoaderImplTest {
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	@Ignore
	@Test
	public void test() throws Exception {
		Long sampleId = 1l;
		
		Sample sample = new Sample(new Study(), SampleStatus.UPLOADED,"zcth-02", "description zcth-02");
		
		ContigTO contigTO = createContigTO();
		Contig contig = createContig(contigTO, sample);
		
		String jsonLine = createJsonLine(contigTO);
		
		File zcthSampleFile = tmpFolder.newFile("zcth-02-sample-file.json");
		FileWriter zcthSampleFileWriter = new FileWriter(zcthSampleFile);
		zcthSampleFileWriter.write(jsonLine);
		zcthSampleFileWriter.close();
		
		SampleFile sampleFile = new SampleFile(sample, FileType.ALL_JSON, FileStatus.UPLOADED, "pier", zcthSampleFile.getAbsolutePath());
		sample.setSampleFiles(Arrays.asList(sampleFile));
		
		ContigTOProcessor mockContigTOProcessor = createMockContigTOProcessor(sample, contigTO, contig);
		
		
		SampleLoaderImpl target = new SampleLoaderImpl(mockContigTOProcessor);
		target.loadFromFileToDatabase(sampleId);
		
		Mockito.verify(mockContigTOProcessor).convert(sample, contigTO);
		
	}


	private Contig createContig(ContigTO contigTO, Sample sample) {
		return new Contig(sample, contigTO.getReference(), contigTO.getSequence(), contigTO.getSize(), contigTO.getNumberOfreads(), contigTO.getNumberOfReadsClassified(), contigTO.getNumberOfFeatures(), contigTO.getTaxonomicIdentificationIndex());
	}

	private ContigTOProcessor createMockContigTOProcessor(Sample sample, ContigTO contigTO, Contig contig) {
		ContigTOProcessor mock = Mockito.mock(ContigTOProcessor.class);
		Mockito.when(mock.convert(sample, contigTO)).thenReturn(contig);
		return mock;
	}


	private String createJsonLine(ContigTO contigTO) {
		Gson gson = new Gson();
		return gson.toJson(contigTO);
	}
	

	private static ContigTO createContigTO() {
		String reference = "ZCTH0002_100040";
		String sequence = "ATTATAGCTTATTATTTCGACGAGTAATCATTTAAAAGATAATGATAATCAATTTTGTCATCCATTCGTTTTGGAGGCAATTATAAAAGAAAAGAGGTGCTATGATGAAAAGAATAAGCTTGGCTTGGCAAATTTTAATAGGTCTTGTACTAGGTATTGTTGTTGGTACTATTTTTTATGGTAACCCAGATGTTGCGAAATTTTTGCAGCCAATTGGGGATATATTTTTACGATTAATTAAAATGATCGTCATTCCAATTGTTATTTCCAGCTTAATTGTCGGTATTGCAAGTGTAGGTG";
		List<FeatureTO> features = new ArrayList<>();
		List<ReadOnContigTO> readsOnCotig = new ArrayList<>();
		return new ContigTO(null, reference, sequence, features, readsOnCotig);
		
	}
}
