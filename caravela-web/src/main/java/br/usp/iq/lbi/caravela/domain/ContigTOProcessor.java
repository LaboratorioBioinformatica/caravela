package br.usp.iq.lbi.caravela.domain;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import lbi.usp.br.caravela.dto.ContigTO;

public interface ContigTOProcessor {
	
	Contig convert(Sample sample, ContigTO contigTO);

}
