package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Mapping implements Serializable {

	private static final int NO_MAPPING_VALUE = 0;
	private static final long serialVersionUID = -366412626925732691L;
	@Column(name="start_alignment")
	private final Integer start;
	@Column(name="end_alignment")
	private final Integer end;
	@Column(name="flag_alignment")
	private final Integer flag;

	
	public Mapping() {
		this(null,null,null);
	}
	
	public Mapping(Integer start, Integer end, Integer flag) {
		this.start = start;
		this.end = end;
		this.flag = flag; 
	}
	
	public boolean isMapping(){
		boolean mapping = true;
		if(end.equals(NO_MAPPING_VALUE)){
			mapping = false;
		} 
		return mapping;
	}
	
	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public Integer getFlag() {
		return flag;
	}
	
	
}
