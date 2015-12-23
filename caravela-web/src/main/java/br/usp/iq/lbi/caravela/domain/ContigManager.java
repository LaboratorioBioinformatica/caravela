package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.model.Read;

public interface ContigManager {
	ContigTO searchContigById(Long contigId);
	List<Read> searchReadOnContigByContigId(Long contigId);

}
