package br.usp.iq.lbi.caravela.domain;

import br.usp.iq.lbi.caravela.model.Sample;

public interface SampleLoader {
	
	void loadFromFileToDatabase(Sample sample) throws Exception;

}
