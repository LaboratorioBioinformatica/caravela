package br.usp.iq.lbi.caravela.dto;

import java.util.List;

public class FeatureTO {
	private final Long id;
	private final String type;
	private final Integer start;
	private final Integer end;
	private final Integer strand;
	private final TaxonTO taxonTO;
	private List<FeatureAnnotationTO> annotations;
	private final PhiloDistTO philoDist;
	private final GeneProductTO geneProduct;
	
	public FeatureTO(Long id, String type, Integer start, Integer end, Integer strand, TaxonTO taxonTO, List<FeatureAnnotationTO> annotations, GeneProductTO geneProduct, PhiloDistTO philoDist) {
		this.id = id;
		this.type = type;
		this.start = start;
		this.end = end;
		this.strand = strand;
		this.taxonTO = taxonTO;
		this.annotations = annotations;
		this.geneProduct = geneProduct;
		this.philoDist = philoDist;
	}
	
	public Long getId(){
		return id;
	}

	public String getType() {
		return type;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public Integer getStrand() {
		return strand;
	}

	public PhiloDistTO getPhiloDist() {
		return philoDist;
	}
	
	public boolean hasGeneProduct(){
		return (geneProduct != null) ? true : false;
	
	}
	
	public boolean hasTaxon(){
		return (taxonTO != null) ? true : false;
	
	}
	
	public boolean hasPhilodist(){
		return (philoDist != null) ? true : false;
	
	}
	
	public boolean hasFeatureAnnotations(){
		if(annotations != null && ! annotations.isEmpty()) {
			return true;
		} else {
			return false;
		}
	
	}

	public GeneProductTO getGeneProduct() {
		return geneProduct;
	}

	public TaxonTO getTaxonTO() {
		return taxonTO;
	}

	public List<FeatureAnnotationTO> getAnnotations() {
		return annotations;
	}
	
}
