package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Read;
import lbi.usp.br.caravela.dto.ContigTO;

public interface ContigManager {
	ContigTO searchContigById(Long contigId);
	List<Read> searchReadOnContigByContigId(Long contigId);

}
