package br.usp.iq.lbi.caravela.domain;

import java.io.File;
import java.io.FileNotFoundException;

public interface NCBITaxonManager {
	
	Long countNumberOfTaxon();
	void clear();
	Boolean isClean();
	boolean isRegisterRunning();
	void register(File fileNCBIScientificNames, File fileNCBINodes) throws FileNotFoundException;

}
