package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="gene_product")
public class GeneProduct {
	
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private Feature feature;
	private String product;
	private String source;
	
	public GeneProduct() {}
	
	public GeneProduct(Feature feature, String product, String source) {
		this.feature = feature;
		this.product = product;
		this.source = source;
	}
	
	public Long getId(){
		return id;
	}
	
	public Feature getFeature(){
		return feature;
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
		if(! (obj instanceof GeneProduct)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		GeneProduct geneProduct = (GeneProduct) obj;
		return this.product.equals(geneProduct.getProduct())  && this.source.equals(geneProduct.getSource()); 
	}

}
