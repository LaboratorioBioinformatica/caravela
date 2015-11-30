package br.usp.iq.lbi.caravela.domain;

import java.util.HashMap;

import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

public interface ReadManager {
	HashMap<String, Taxon> loadLineagem(Read read);

}
