package br.usp.iq.lbi.caravela.dto;

public class PhiloDistTO {
	private Long geneOID;
	private Long taxonOID;
	private Double identity;
	private String lineage;
	
	public PhiloDistTO(Long geneOID, Long taxonOID, Double identity, String lineage) {
		this.geneOID = geneOID;
		this.taxonOID = taxonOID;
		this.identity = identity;
		this.lineage = lineage;
	}

	public Long getGeneOID() {
		return geneOID;
	}

	public Long getTaxonOID() {
		return taxonOID;
	}

	public Double getIdentity() {
		return identity;
	}

	public String getLineage() {
		return lineage;
	}

}
