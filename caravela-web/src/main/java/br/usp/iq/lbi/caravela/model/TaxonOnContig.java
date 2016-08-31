package br.usp.iq.lbi.caravela.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@Enumerated(EnumType.STRING)
	private TaxonomicRank rank;
	
	@ManyToOne
	private Taxon taxon;
	
	private Double covarage;
	
	@Column(name="number_of_reads")
	private Integer numberOfReads;
	
	public TaxonOnContig() {
		
	}
	
	public TaxonOnContig(Sample sample, Contig contig, TaxonomicRank rank, Taxon taxon, Double covarage, Integer numberOfReads) {
		this.contig = contig;
		this.taxon = taxon;
		this.rank = rank;
		this.covarage = covarage;
		this.numberOfReads = numberOfReads;
		this.sample = sample;
	}

	public Long getId() {
		return id;
	}

	public TaxonomicRank getRank(){
		return rank;
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
	
	public Integer getNumberOfReads(){
		return numberOfReads;
	}
	

}
