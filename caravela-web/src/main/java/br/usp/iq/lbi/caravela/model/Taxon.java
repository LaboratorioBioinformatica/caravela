package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="taxon")
public class Taxon implements Serializable {
	
	private static final long serialVersionUID = 3637514606101828793L;
	
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private final Read read;
	@Column(name="taxonomy_id")
	private final Integer taxonomyId;
	@Column(name="scientific_name")
	private final String scientificName;
	private final String rank;
	private final Double score;
		
	public Taxon(Read read, Integer taxonomyId, String scientificName, String rank, Double score) {
		this.read = read;
		this.taxonomyId = taxonomyId;
		this.scientificName = scientificName; 
		this.rank = rank;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public Integer getTaxonomyId() {
		return taxonomyId;
	}

	public String getScientificName() {
		return scientificName;
	}

	public String getRank() {
		return rank;
	}

	public Double getScore() {
		return score;
	}
	
	@Override
	public int hashCode() {
		return this.taxonomyId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Taxon)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		Taxon taxon = (Taxon) obj;
		return this.taxonomyId == taxon.getTaxonomyId() && this.scientificName.equals(taxon.getScientificName()); 
		
	}

}
