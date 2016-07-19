package br.usp.iq.lbi.caravela.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="philodist")
public class Philodist {
	
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private Feature feature;
	@Column(name="gene_oid")
	private Long geneOID;
	@Column(name="taxon_oid")
	private Long taxonOID;
	private Double identity;
	private String lineage;

	public Philodist() {}
	
	public Philodist(Feature feature, Long geneOID, Long taxonOID, Double identity, String lineage) {
		this.feature = feature;
		this.geneOID = geneOID;
		this.taxonOID = taxonOID;
		this.identity = identity;
		this.lineage = lineage;
	}

	public Long getId() {
		return id;
	}

	public Feature getFeature() {
		return feature;
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
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Philodist)){
			return false;
		}
		if(obj == this){
			return true;
		}
		
		Philodist philodist = (Philodist) obj;
		
		return this.geneOID.equals(philodist.getGeneOID()) &&
				this.taxonOID.equals(philodist.getTaxonOID()) &&
				this.identity.equals(philodist.getIdentity()) &&
				this.lineage.equals(philodist.lineage);
	}
	

}
