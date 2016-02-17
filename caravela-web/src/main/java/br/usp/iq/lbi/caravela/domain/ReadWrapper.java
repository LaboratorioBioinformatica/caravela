package br.usp.iq.lbi.caravela.domain;

import java.util.List;
import java.util.Map;

import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

public interface ReadWrapper {
	
	Map<Taxon, List<Read>> groupBy(List<Read> reads, String taxonomicRank);

}
