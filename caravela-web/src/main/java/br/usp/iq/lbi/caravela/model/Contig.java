package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="contig")
public class Contig implements Serializable {
	
	private static final long serialVersionUID = -98237573034655829L;
	
	@Id
	@GeneratedValue
	private  Long id;
	
	@ManyToOne
	private Sample sample;
	
	private String reference;
	private String sequence;
	
	@Column(name="number_of_reads")
	private Integer numberOfReads;
	
	@Column(name="number_of_reads_classified")
	private Integer numberOfReadsClassified;
	
	@Column(name="number_of_features")
	private Integer numberOfFeatures;
	
	@Column(name="tii")
	private Double taxonomicIdentificationIndex; 
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="contig")
	private List<Feature> features;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="contig")
	private List<Read> reads;
	
	public Contig() {}
	
	public Contig(Sample sample, String reference, String sequence, Integer nOfR, Integer nOfRC, Integer nOfF, Double taxonomicIdentificationIndex) {
		this.sample = sample;
		this.reference = reference;
		this.sequence = sequence;
		this.numberOfReads = nOfR;
		this.numberOfReadsClassified = nOfRC;
		this.numberOfFeatures = nOfF;
		this.taxonomicIdentificationIndex = taxonomicIdentificationIndex;
	}

	public Sample getSample() {
		return sample;
	}

	public String getReference() {
		return reference;
	}

	public String getSequence() {
		return sequence;
	}
	
	public Integer getSize(){
		return sequence.length();
	}
	
	public Double getTaxonomicIdentificationIndex(){
		return taxonomicIdentificationIndex;
	}
	
	public Long getId(){
		return id;
	}

	public Integer getNumberOfReads() {
		return numberOfReads;
	}

	public Integer getNumberOfReadsClassified() {
		return numberOfReadsClassified;
	}
	
	public List<Feature> getFeatures(){
		return this.features;
	}

	public Integer getNumberOfFeatures() {
		return numberOfFeatures;
	}
	
	
	
	
	

}
