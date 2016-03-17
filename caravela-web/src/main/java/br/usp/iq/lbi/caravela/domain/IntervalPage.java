package br.usp.iq.lbi.caravela.domain;



public class IntervalPage {
	
	private Integer start;
	private Integer end;
	
	public IntervalPage(Integer start, Integer end) {
		this.start = start;
		this.end = end;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof IntervalPage)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		IntervalPage intervalPage = (IntervalPage) obj;
		return this.start.equals(intervalPage.getStart()) && this.end.equals(intervalPage.getEnd()); 
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(this.start).append(":").append(this.end).toString();
	}

}
