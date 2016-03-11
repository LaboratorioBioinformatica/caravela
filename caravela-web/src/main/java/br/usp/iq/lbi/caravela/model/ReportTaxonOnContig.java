package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="report_taxon_contig")
public class ReportTaxonOnContig {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private ReportContig reportContig;
	
	@ManyToOne
	private Taxon taxon;
	private Double covarage;
	
	public ReportTaxonOnContig() {
		
	}
	
	public ReportTaxonOnContig(ReportContig reportContig, Taxon taxon, Double covarage) {
		this.taxon = taxon;
		this.covarage = covarage;
		this.reportContig = reportContig;
	}

	public Long getId() {
		return id;
	}

	public ReportContig getReportContig() {
		return reportContig;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public Double getCovarage() {
		return covarage;
	}
	

}
