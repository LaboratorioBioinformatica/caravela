package lbi.usp.br.caravela.dto;

public class PhiloDistTO {
	//homolog Gene OID
	private Long geneOID;
	
	//homolog Taxon OID
	private Long taxonOID;
	
	//percent of Identity
	private Double identity;
	
	private String lineage;
	
	public PhiloDistTO(Long geneOID, Long taxonOID, Double identity, String lineage) {
		this.geneOID = geneOID;
		this.taxonOID = taxonOID;
		this.identity = identity;
		this.lineage = lineage;
	}

}
