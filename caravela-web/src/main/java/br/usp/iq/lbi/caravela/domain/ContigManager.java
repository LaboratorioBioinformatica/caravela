package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.dto.ContigReportTO;
import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;

public interface ContigManager {
	ContigTO searchContigById(Long contigId);
	List<Read> searchReadOnContigByContigId(Long contigId);
	List<ContigReportTO> searchAllContigBySample(Sample sample);

}
