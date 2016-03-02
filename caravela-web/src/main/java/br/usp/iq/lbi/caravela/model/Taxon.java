package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="taxon")
public class Taxon implements Comparable<Taxon>, Serializable {
	

	private static final long serialVersionUID = 3637514606101828793L;
	
	private static final long NO_TAXONOMY_PARENT_ID = 0l;
	private static final long NO_TAXONOMY_ID = 0l;
	private static final String NO_TAXONOMY_SCIENTIFIC_NAME = "no taxon";
	private static final String NO_TAXONOMY_RANK = "no taxon";
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="taxonomy_id")
	private Long taxonomyId;

	@Column(name="taxonomy_parent_id")
	private Long taxonomyParentId;
	
	@Column(name="scientific_name")
	private String scientificName;
	private String rank;
	
	
	public Taxon() {}
		
	public Taxon(Long taxonomyId, Long taxonomyParentId, String scientificName, String rank) {
		this.taxonomyId = taxonomyId;
		this.taxonomyParentId = taxonomyParentId;
		this.scientificName = scientificName; 
		this.rank = rank;
	}
	
	public static final Taxon getNOTaxon(){
		return new Taxon(NO_TAXONOMY_ID,NO_TAXONOMY_PARENT_ID, NO_TAXONOMY_SCIENTIFIC_NAME, NO_TAXONOMY_RANK);
		
	}

	public Long getId() {
		return id;
	}

	public Long getTaxonomyId() {
		return taxonomyId;
	}
	
	
	public Long getTaxonomyParentId() {
		return taxonomyParentId;
	}

	public String getScientificName() {
		return scientificName;
	}

	public String getRank() {
		return rank;
	}
	
	@Override
	public int hashCode() {
		return  Math.toIntExact(this.taxonomyId);
	}
	
	@Override
	public String toString() {
		return getScientificName();
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
		return this.taxonomyId.equals(taxon.getTaxonomyId()); 
	}

	public int compareTo(Taxon o) {
		return this.scientificName.compareTo(o.getScientificName());
	}

}
