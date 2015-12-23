package br.usp.iq.lbi.caravela.dto;


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
	
	
	@Override
	public int hashCode() {
		return this.product.hashCode() * source.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof GeneProductTO)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		GeneProductTO geneProductTO = (GeneProductTO) obj;
		return this.product.equals(geneProductTO.getProduct())  && this.source.equals(geneProductTO.getSource()); 
	}

}
