package br.usp.iq.lbi.caravela.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.chainsaw.Main;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.gson.Gson;

import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.dto.FeatureTO;
import br.usp.iq.lbi.caravela.dto.ReadOnContigTO;
import br.usp.iq.lbi.caravela.model.FileStatus;
import br.usp.iq.lbi.caravela.model.FileType;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;
import br.usp.iq.lbi.caravela.model.SampleStatus;
import br.usp.iq.lbi.caravela.model.Treatment;

public class SampleLoaderImplTest {
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	@Test
	public void test() throws Exception {
		
		ContigTO contigTO = createContigTO();
		
		String jsonLine = createJsonLine(contigTO);
		
		File zcthSampleFile = tmpFolder.newFile("zcth-02-sample-file.json");
		FileWriter zcthSampleFileWriter = new FileWriter(zcthSampleFile);
		zcthSampleFileWriter.write(jsonLine);
		zcthSampleFileWriter.close();
		
		Sample sample = new Sample(new Treatment(), SampleStatus.UPLOADED,"zcth-02", "description zcth-02");
		SampleFile sampleFile = new SampleFile(sample, FileType.ALL_JSON, FileStatus.UPLOADED, "pier", zcthSampleFile.getAbsolutePath());
		sample.setSampleFiles(Arrays.asList(sampleFile));
		
		SampleLoaderImpl target = new SampleLoaderImpl();
		target.loadFromFileToDatabase(sample);
		
		
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
