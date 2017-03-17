package br.usp.iq.lbi.caravela.dto;

import br.usp.iq.lbi.caravela.model.TaxonomicRank;

public class ContigFilterParametersTO {
	
	private Double itg;
	private Integer numberOfFeatures;
	private Integer numberOfBorders;
	private Double ct;
	private Double ctv;
	private TaxonomicRank taxonomicRank;
	
	public ContigFilterParametersTO() {}
	
	public ContigFilterParametersTO(Double itg, Integer numberOfFeatures, Integer numberOfBorders, Double ct, Double ctv, TaxonomicRank taxonomicRank) {
		this.itg = itg;
		this.numberOfFeatures = numberOfFeatures;
		this.numberOfBorders = numberOfBorders;
		this.ct = ct;
		this.ctv = ctv;
		this.taxonomicRank = taxonomicRank;
	}

	public Double getItg() {
		return itg;
	}

	public void setItg(Double itg) {
		this.itg = itg;
	}

	public Integer getNumberOfFeatures() {
		return numberOfFeatures;
	}

	public void setNumberOfFeatures(Integer numberOfFeatures) {
		this.numberOfFeatures = numberOfFeatures;
	}

	public Integer getNumberOfBorders() {
		return numberOfBorders;
	}

	public void setNumberOfBorders(Integer numberOfBorders) {
		this.numberOfBorders = numberOfBorders;
	}

	public Double getCt() {
		return ct;
	}

	public void setCt(Double ct) {
		this.ct = ct;
	}

	public Double getCtv() {
		return ctv;
	}

	public void setCtv(Double ctv) {
		this.ctv = ctv;
	}

	public TaxonomicRank getTaxonomicRank() {
		return taxonomicRank;
	}
	
	public void setTaxonomicRank(TaxonomicRank taxonomicRank) {
		this.taxonomicRank = taxonomicRank;
	}
	
	
}
