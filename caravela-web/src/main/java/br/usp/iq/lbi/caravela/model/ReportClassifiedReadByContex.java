package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="report_classified_read_by_context")
public class ReportClassifiedReadByContex {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private ReportSample reportSample;
	@ManyToOne
	private Contig contig;
	@ManyToOne
	private Read read;
	@ManyToOne
	private Taxon taxon;
	
	public ReportClassifiedReadByContex() {
	}
	
	public ReportClassifiedReadByContex(ReportSample reportSample, Contig contig, Read read, Taxon taxon) {
		this.reportSample = reportSample;
		this.contig = contig;
		this.read = read;
		this.taxon = taxon;
	}

	public Long getId() {
		return id;
	}

	public ReportSample getReportSample() {
		return reportSample;
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
