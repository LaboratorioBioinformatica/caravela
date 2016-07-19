package br.usp.iq.lbi.caravela.dto;

public class FeatureAnnotationTO {
	private FeatureAnnotationTypeTO type;
	private String name;
	private Double identity;
	private Integer alignLength;
	private Integer queryStart;
	private Integer queryEnd;
	private Integer subjectStart;
	private Integer subjectEnd;
	private Double evalue;
	private Double bitScore;
	
	public FeatureAnnotationTO(FeatureAnnotationTypeTO annotationType, String annotationName, Double identity, Integer alignLength, Integer queryStart, Integer queryEnd, Integer subjectStart, Integer subjectEnd, Double evalue,Double bitScore) {
		this.type = annotationType;
		this.name = annotationName;
		this.identity = identity;
		this.alignLength = alignLength;
		this.queryStart = queryStart;
		this.queryEnd = queryEnd;
		this.subjectStart = subjectStart;
		this.subjectEnd = subjectEnd;
		this.evalue = evalue;
		this.bitScore = bitScore;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FeatureAnnotationTO)){
			return false;
		}
		if(obj == this){
			return true;
		}
		FeatureAnnotationTO fa = (FeatureAnnotationTO) obj;
		
		return this.type.equals(fa.type) &&
				this.name.equals(fa.name) &&
				this.identity.equals(fa.identity) &&
				this.alignLength.equals(fa.alignLength) &&
				this.queryStart.equals(fa.queryStart) &&
				this.queryEnd.equals(fa.queryEnd) &&
				this.subjectStart.equals(fa.subjectStart) &&
				this.subjectEnd.equals(fa.subjectEnd) &&
				this.evalue.equals(fa.evalue) &&
				this.bitScore.equals(fa.bitScore);
				 
	}

	public FeatureAnnotationTypeTO getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Double getIdentity() {
		return identity;
	}

	public Integer getAlignLength() {
		return alignLength;
	}

	public Integer getQueryStart() {
		return queryStart;
	}

	public Integer getQueryEnd() {
		return queryEnd;
	}

	public Integer getSubjectStart() {
		return subjectStart;
	}

	public Integer getSubjectEnd() {
		return subjectEnd;
	}

	public Double getEvalue() {
		return evalue;
	}

	public Double getBitScore() {
		return bitScore;
	}
	
	@Override
	public String toString() {
		return this.name.toString();
	}
	
}
