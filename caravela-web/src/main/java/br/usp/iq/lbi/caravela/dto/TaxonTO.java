package br.usp.iq.lbi.caravela.dto;



public class TaxonTO {
		
	private final Long taxonomyId;
	private final String scientificName;
	private final String hank;
	private final Double score;
	
	public static class Builder {
		private Long taxonomyId;
		private String scientificName;
		private String hank;
		private Double score;
		
		public Builder() {}
		
		public Builder setTaxonomyId(Long taxonomyId){
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
		
		public TaxonTO build(){
			return new TaxonTO(this);
		}
	}
	
	public TaxonTO(Builder builder) {
		this.taxonomyId = builder.taxonomyId;
		this.scientificName = builder.scientificName;
		this.hank = builder.hank;
		this.score = builder.score;
	}
	
	public Long getTaxonomyId() {
		return taxonomyId;
	}

	public String getScientificName() {
		return scientificName;
	}

	public String getHank() {
		return hank;
	}

	public Double getScore() {
		return score;
	}

}
