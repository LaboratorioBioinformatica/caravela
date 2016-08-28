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
@Table(name="contig_statistic_by_tii")
public class ContigStatisticByTii {
	
	@Id
	@GeneratedValue
	private  Long id;
	
	@ManyToOne
	private Contig contig;
	
	@ManyToOne
	private Sample sample;
	
	@Enumerated(EnumType.STRING)
	private TaxonomicRank rank;
	
	@Column(name="ictcr")
	private Double indexOfConsistencyTaxonomicByCountReads;
	
	@Column(name="ivct")
	private Double indexOfVerticalConsistencyTaxonomic;
	
	private Integer boundary;
	private Double unclassified;
	private Double undefined;
	
	public ContigStatisticByTii() {}
	
	public ContigStatisticByTii(Sample sample, Contig contig, TaxonomicRank rank, Integer boundary, Double unclassified, Double undefined, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic) {
		this.sample = sample;
		this.contig = contig;
		this.rank = rank;
		this.boundary = boundary;
		this.unclassified = unclassified;
		this.undefined = undefined;
		this.indexOfConsistencyTaxonomicByCountReads = indexOfConsistencyTaxonomicByCountReads;
		this.indexOfVerticalConsistencyTaxonomic = indexOfVerticalConsistencyTaxonomic;
	}
	
	public TaxonomicRank getRank() {
		return rank;
	}

	public Double getIndexOfConsistencyTaxonomicByCountReads() {
		return indexOfConsistencyTaxonomicByCountReads;
	}

	public Double getIndexOfVerticalConsistencyTaxonomic() {
		return indexOfVerticalConsistencyTaxonomic;
	}

	public Long getId() {
		return id;
	}

	public Contig getContig() {
		return contig;
	}

	public Integer getBoundary() {
		return boundary;
	}

	public Double getUnclassified() {
		return unclassified;
	}

	public Double getUndefined() {
		return undefined;
	}
	
	public Sample getSample(){
		return sample;
	}
	
}
