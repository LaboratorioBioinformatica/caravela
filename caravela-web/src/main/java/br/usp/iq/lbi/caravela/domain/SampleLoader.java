package br.usp.iq.lbi.caravela.domain;

public interface SampleLoader {
	
	void loadFromFileToDatabase(Long sampleId) throws Exception;

}
