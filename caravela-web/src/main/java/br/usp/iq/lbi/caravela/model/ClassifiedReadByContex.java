package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="classified_read_by_context")
public class ClassifiedReadByContex {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Sample sample;
	@ManyToOne
	private Contig contig;
	@ManyToOne
	private Read read;
	@ManyToOne
	private Taxon taxon;
	
	public ClassifiedReadByContex() {
	}
	
	public ClassifiedReadByContex(Sample sample, Contig contig, Read read, Taxon taxon) {
		this.sample = sample;
		this.contig = contig;
		this.read = read;
		this.taxon = taxon;
	}

	public Long getId() {
		return id;
	}

	public Sample getSample() {
		return sample;
	}

	public Contig getContig() {
		return contig;
	}

	public Read getRead() {
		return read;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	
}
