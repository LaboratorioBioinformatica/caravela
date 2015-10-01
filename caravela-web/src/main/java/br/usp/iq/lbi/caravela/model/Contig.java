package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;
import java.util.List;

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
	
	private final String reference;
	private final String sequence;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="contig")
	private List<Feature> features;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="contig")
	private List<Read> reads;
	
	public Contig(Sample sample, String reference, String sequence) {
		this.sample = sample;
		this.reference = reference;
		this.sequence = sequence;
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
	
	public Long getId(){
		return id;
	}
	

}
