package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="feature")
public class Feature implements Serializable {

	private static final long serialVersionUID = 9071792536581499568L;
	
	@Id
	@GeneratedValue
	private  Long id;
	@ManyToOne
	private Contig contig;
	@Column(name="feature_type")
	private String type;
	@Column(name="start_alignment")
	private Integer start;
	@Column(name="end_alignment")
	private Integer end;
	private Integer strand;
	@ManyToOne
	private Taxon taxon;
	@OneToOne(fetch=FetchType.EAGER, mappedBy="feature", cascade=CascadeType.ALL)
	private GeneProduct geneProduct;
	@OneToOne(fetch=FetchType.EAGER, mappedBy="feature", cascade=CascadeType.ALL)
	private Philodist philodist;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="feature", cascade=CascadeType.ALL)
	private List<FeatureAnnotation> featureAnnotations;
	
	public Feature() {}
	
	public Feature(Contig contig, String type, Integer start, Integer end, Integer strand) {
		this.contig = contig;
		this.type = type;
		this.start = start;
		this.end = end;
		this.strand = strand;
	}
	
	public void addGeneProduct(GeneProduct geneProduct){
		this.geneProduct = geneProduct;
	}
	
	public void addPhilodist(Philodist philodist){
		this.philodist = philodist;
	}
	
	public void addFeatureAnnotation(FeatureAnnotation featureAnnotation){
		if(this.featureAnnotations != null){
			featureAnnotations.add(featureAnnotation);	
		} else {
			this.featureAnnotations = new ArrayList<>();
			featureAnnotations.add(featureAnnotation);
		}
	}

	public Contig getContig() {
		return contig;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public GeneProduct getGeneProduct() {
		return geneProduct;
	}

	public Philodist getPhilodist() {
		return philodist;
	}

	public List<FeatureAnnotation> getFeatureAnnotations() {
		return featureAnnotations;
	}

	
	public String getType() {
		return type;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public Integer getStrand() {
		return strand;
	}

	
	public Long getId(){
		return id;
	}
	
	public void toAssigTaxon(Taxon taxon){
		this.taxon = taxon;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Feature)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		Feature feature = (Feature) obj;
		return this.contig.equals(feature.getContig()) &&
				this.type.equals(feature.getType()) &&
				this.start.equals(feature.getStart()) &&
				this.end.equals(feature.getEnd()) &&
				this.strand.equals(feature.getStrand());
	}	
	

}
