package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="taxon_on_contig")
public class TaxonOnContig {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Sample sample;
	
	@ManyToOne
	private Contig contig;
	
	@ManyToOne
	private Taxon taxon;
	
	private Double covarage;
	
	public TaxonOnContig() {
		
	}
	
	public TaxonOnContig(Sample sample, Contig contig, Taxon taxon, Double covarage) {
		this.contig = contig;
		this.taxon = taxon;
		this.covarage = covarage;
		this.sample = sample;
	}

	public Long getId() {
		return id;
	}


	public Taxon getTaxon() {
		return taxon;
	}

	public Double getCovarage() {
		return covarage;
	}

	public Sample getSample() {
		return sample;
	}

	public Contig getContig() {
		return contig;
	}
	

}
