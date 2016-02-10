package br.usp.iq.lbi.caravela.domain;

import java.util.List;
import java.util.Map;

import br.usp.iq.lbi.caravela.model.Read;

public interface ReadWrapper {
	
	Map<String, List<Read>> groupBy(List<Read> reads, String taxonomicRank);

}
