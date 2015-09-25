package lbi.usp.br.caravela.dto;

public class Taxon {
	
	private final Integer taxonomyId;
	private final String scientificName;
	private final String hank;
	private final Double score;
	
	public static class Builder {
		private Integer taxonomyId;
		private String scientificName;
		private String hank;
		private Double score;
		
		public Builder() {}
		
		public Builder setTaxonomyId(Integer taxonomyId){
			this.taxonomyId = taxonomyId;
			return this;
		}
		
		public Builder setScientificName(String scientificName){
			this.scientificName = scientificName;
			return this;
		}
		
		public Builder setHank(String hank){
			this.hank = hank;
			return this;
		}
		
		public Builder setScore(Double score){
			this.score = score;
			return this;
		}
		
		public Taxon build(){
			return new Taxon(this);
		}
	}
	
	public Taxon(Builder builder) {
		this.taxonomyId = builder.taxonomyId;
		this.scientificName = builder.scientificName;
		this.hank = builder.hank;
		this.score = builder.score;
	}
	

}
