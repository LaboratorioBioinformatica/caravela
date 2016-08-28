package br.usp.iq.lbi.caravela.model;

public enum TaxonomicRank {
	
	SPECIES("species"),
	GENUS("genus"),
	FAMILY("family"),
	ORDER("order"),
	CLASS("class"),
	PHYLUM("phylum"),
	SUPERKINGDOM("superkingdom");
	
	private final String value;
	
	private TaxonomicRank(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
