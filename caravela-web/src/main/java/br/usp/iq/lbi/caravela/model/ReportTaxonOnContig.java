package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="report_taxon_on_contig")
public class ReportTaxonOnContig {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private ReportSample reportSample;
	
	@ManyToOne
	private Contig contig;
	
	@ManyToOne
	private Taxon taxon;
	
	private Double covarage;
	
	public ReportTaxonOnContig() {
		
	}
	
	public ReportTaxonOnContig(ReportSample reportSample, Contig contig, Taxon taxon, Double covarage) {
		this.contig = contig;
		this.taxon = taxon;
		this.covarage = covarage;
		this.reportSample = reportSample;
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

	public ReportSample getReportSample() {
		return reportSample;
	}

	public Contig getContig() {
		return contig;
	}
	

}
