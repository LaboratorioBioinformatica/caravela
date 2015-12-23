package br.usp.iq.lbi.caravela.domain;

import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;

public interface ContigTOProcessor {
	
	Contig convert(Sample sample, ContigTO contigTO);

}
