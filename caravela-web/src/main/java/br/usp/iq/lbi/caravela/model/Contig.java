package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="contig")
public class Contig implements Serializable {
	
	private static final long serialVersionUID = -98237573034655829L;
	
	@ManyToOne
	private Sample sample;
	
	private final String reference;
	private final String sequence;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="feature")
	private final List<Feature> features;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="read")
	private final List<Read> reads;
	
	public Contig(Sample sample, String reference, String sequence, List<Feature> features, List<Read> reads) {
		this.sample = sample;
		this.reference = reference;
		this.sequence = sequence;
		this.features = features;
		this.reads = reads;
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
	

}
