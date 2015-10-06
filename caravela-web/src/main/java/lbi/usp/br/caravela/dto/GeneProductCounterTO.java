package lbi.usp.br.caravela.dto;

public class GeneProductCounterTO implements Comparable<GeneProductCounterTO>{
	
	private final GeneProductTO geneProductTO;
	private Integer total;
	
	public GeneProductCounterTO(GeneProductTO geneProductTO) {
		this.geneProductTO = geneProductTO;
		total = 1;
	}
	
	public void addOne(){
		this.total++;
	}
	
	public Integer getTotal(){
		return this.total;
	}
	
	public String getProductName(){
		return this.geneProductTO.getProduct();
	}
	
	public String getProductSource(){
		return this.geneProductTO.getSource();
	}

	public int compareTo(GeneProductCounterTO o) {
		if(this.total > o.getTotal()){
			return -1;
		}
		if(this.total < o.getTotal()){
			return 1;
		}
		return 0;
	}

}
