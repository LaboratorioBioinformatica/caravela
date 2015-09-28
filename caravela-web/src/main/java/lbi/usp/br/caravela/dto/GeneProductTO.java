package lbi.usp.br.caravela.dto;

public class GeneProductTO {
	
	private final String product;
	private final String source;
	
	public GeneProductTO(String product, String source) {
		this.product = product;
		this.source = source;
	}

	public String getProduct() {
		return product;
	}

	public String getSource() {
		return source;
	}

}
