package br.usp.iq.lbi.caravela.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="report_sample")
public class ReportSample {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Sample sample;
	
	@Column(name="tii")
	private Double taxonomicIdentificationIndex;
	
	private String rank;
	
	public ReportSample() {
	}
	
	public ReportSample(Sample sample, Double taxonomicIdentificationIndex, String rank) {
		this.sample = sample;
		this.taxonomicIdentificationIndex = taxonomicIdentificationIndex;
		this.rank = rank;
	}

	public Long getId() {
		return id;
	}

	public Sample getSample() {
		return sample;
	}

	public String getRank() {
		return rank;
	}

	public Double getTaxonomicIdentificationIndex() {
		return taxonomicIdentificationIndex;
	}
	
	

}
