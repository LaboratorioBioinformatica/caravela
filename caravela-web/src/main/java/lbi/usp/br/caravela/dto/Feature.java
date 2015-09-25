package lbi.usp.br.caravela.dto;

public class Feature {
	private final String source;
	private final String type;
	private final Integer start;
	private final Integer end;
	private final Integer strand;
	private final PhiloDist philoDist;
	private final GeneProduct geneProduct;
	
	public Feature(String source, String type, Integer start, Integer end, Integer strand, GeneProduct geneProduct, PhiloDist philoDist) {
		this.source = source;
		this.type = type;
		this.start = start;
		this.end = end;
		this.strand = strand;
		this.geneProduct = geneProduct;
		this.philoDist = philoDist;
	}

}
