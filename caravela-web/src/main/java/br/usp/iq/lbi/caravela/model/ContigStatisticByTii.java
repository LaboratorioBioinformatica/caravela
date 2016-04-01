package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
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
	private Integer boundary;
	private Double unclassified;
	private Double undefined;
	
	public ContigStatisticByTii() {}
	
	public ContigStatisticByTii(Sample sample, Contig contig, Integer boundary, Double unclassified, Double undefined) {
		this.sample = sample;
		this.contig = contig;
		this.boundary = boundary;
		this.unclassified = unclassified;
		this.undefined = undefined;
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
