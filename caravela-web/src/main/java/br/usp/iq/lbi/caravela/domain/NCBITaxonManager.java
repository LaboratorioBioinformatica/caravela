package br.usp.iq.lbi.caravela.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import br.usp.iq.lbi.caravela.model.Taxon;

public interface NCBITaxonManager {
	
	Long countNumberOfTaxon();
	void clear();
	void register(File fileNCBIScientificNames, File fileNCBINodes) throws FileNotFoundException;
	Taxon searchByTaxonomicId(Long taxonomicId);
	Map<Long, Taxon> SearchAllTaxon();

}
