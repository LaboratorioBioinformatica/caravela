package br.usp.iq.lbi.caravela.domain;

import br.usp.iq.lbi.caravela.model.Sample;

public interface SampleReporter {
	
	void reportChimericPotentialFromContig(Sample sample, Double tii, String rank);

}
