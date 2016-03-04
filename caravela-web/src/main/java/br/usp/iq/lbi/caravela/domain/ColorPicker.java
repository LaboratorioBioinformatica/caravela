package br.usp.iq.lbi.caravela.domain;

import br.usp.iq.lbi.caravela.model.Taxon;

public interface ColorPicker {
	
	String generateRandomColor();
	String generateColorByTaxon(Taxon taxon);

}
