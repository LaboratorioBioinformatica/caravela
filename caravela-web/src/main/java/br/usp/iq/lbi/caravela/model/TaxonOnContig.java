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
	
	private Double coverage;
	
	@Column(name="number_of_reads")
	private Integer numberOfReads;
	
	@Column(name="number_of_unique_bases")
	private Integer numberOfUniqueBases;
	
	@Column(name="unique_coverage")
	private Double uniqueCoverage;
	
	
	public TaxonOnContig() {
		
	}
	
	public TaxonOnContig(Sample sample, Contig contig, TaxonomicRank rank, Taxon taxon, Double coverage, Integer numberOfReads, Integer numberOfUniqueBases, Double uniqueCoverage) {
		this.contig = contig;
		this.taxon = taxon;
		this.rank = rank;
		this.coverage = coverage;
		this.numberOfReads = numberOfReads;
		this.numberOfUniqueBases = numberOfUniqueBases;
		this.uniqueCoverage = uniqueCoverage;
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

	public Double getCoverage() {
		return coverage;
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
	
	public Integer getNumberOfUniqueBases(){
		return this.numberOfUniqueBases;
	}
	
	public Double getUniqueCoverage(){
		return this.uniqueCoverage;
	}
	
	

}
