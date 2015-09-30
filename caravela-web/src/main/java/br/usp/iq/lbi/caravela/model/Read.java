package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="sequence")
public class Read implements Serializable {
	
	private static final long serialVersionUID = 8538633266293466706L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private final Sample sample;
	@ManyToOne
	private final Contig contig;
	private final String reference;
	private final String sequence;
	private final Integer pair;
	@OneToOne
	private final Taxon taxon;
	
	@Embedded
	private final Mapping mapping;
	
	public Read(String reference, Sample sample, Contig contig, String sequence, Integer pair, Taxon taxon, Mapping mapping) {
		this.reference = reference;
		this.sample = sample;
		this.contig = contig;
		this.sequence = sequence;
		this.pair = pair;
		this.taxon = taxon;
		this.mapping = mapping;
	}

	
	public Long getId() {
		return id;
	}

	public String getReference() {
		return reference;
	}

	public String getSequence() {
		return sequence;
	}

	public Integer getPair() {
		return pair;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public Sample getSample() {
		return sample;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Read)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		Read read = (Read) obj;
		return this.reference.equals(read.getReference()) && this.sample.getName().equals(read.getSample().getName()) && this.pair == read.getPair(); 
	}	
	

}
