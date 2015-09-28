package lbi.usp.br.caravela.dto;

public class FeatureTO {
	private final String source;
	private final String type;
	private final Integer start;
	private final Integer end;
	private final Integer strand;
	private final PhiloDistTO philoDist;
	private final GeneProductTO geneProduct;
	
	public FeatureTO(String source, String type, Integer start, Integer end, Integer strand, GeneProductTO geneProduct, PhiloDistTO philoDist) {
		this.source = source;
		this.type = type;
		this.start = start;
		this.end = end;
		this.strand = strand;
		this.geneProduct = geneProduct;
		this.philoDist = philoDist;
	}

	public String getSource() {
		return source;
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

	public GeneProductTO getGeneProduct() {
		return geneProduct;
	}
	
}
