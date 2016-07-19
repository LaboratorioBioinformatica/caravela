package br.usp.iq.lbi.caravela.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="feature_annotation")
public class FeatureAnnotation {
	
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Feature feature;
	@Enumerated(EnumType.STRING)
	@Column(name="feature_annotation_type")
	private FeatureAnnotationType type;
	private String name;
	private Double identity;
	@Column(name="align_length")
	private Integer alignLength;
	@Column(name="query_start")
	private Integer queryStart;
	@Column(name="query_end")
	private Integer queryEnd;
	@Column(name="subject_start")
	private Integer subjectStart;
	@Column(name="subject_end")
	private Integer subjectEnd;
	private Double evalue;
	@Column(name="bit_score")
	private Double bitScore;
	
	public FeatureAnnotation() {}
	
	public FeatureAnnotation(Feature feature, FeatureAnnotationType annotationType, String annotationName, Double identity, Integer alignLength, Integer queryStart, Integer queryEnd, Integer subjectStart, Integer subjectEnd, Double evalue,Double bitScore) {
		this.feature = feature;
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
	
	public Long getId() {
		return id;
	}
	
	public Feature getFeature(){
		return feature;
	}

	public FeatureAnnotationType getType() {
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
	public boolean equals(Object obj) {
		if(!(obj instanceof FeatureAnnotation)){
			return false;
		}
		if(obj == this){
			return true;
		}
		FeatureAnnotation fa = (FeatureAnnotation) obj;
		
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


}
