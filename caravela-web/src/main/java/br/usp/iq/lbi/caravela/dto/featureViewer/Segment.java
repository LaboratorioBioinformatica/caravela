package br.usp.iq.lbi.caravela.dto.featureViewer;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Segment implements Comparable<Segment>, Serializable {
	
private static final long serialVersionUID = 6552981806740144131L;
	
	private Integer x;
	private Integer y;
	private List<String> species;
	
	public Segment(Integer x, Integer y, List<String> species) {
		this.x = x;
		this.y = y;
		this.species = species;
	}
	
	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}
	
	public List<String> getSpecies(){
		Collections.sort(species);
		return species;
	}
	
	public int compareTo(Segment other) {
		if (x < other.getX())
			return -1;
		else if (x > other.getX())
			return 1;
		else if (y < other.getY())
			return -1;
		else if (y > other.getY())
			return 1;
		else
			return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Segment)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		Segment segment = (Segment) obj;
		return this.x.equals(segment.getX()) && this.y.equals(segment.getY()) && this.getSpecies().equals(segment.getSpecies()); 
	}
	
	@Override
	public String toString() {
		StringBuilder segmentStringBuilder = new StringBuilder().append(getX()).append(":").append(getY()).append(":").append(getSpecies());
		return segmentStringBuilder.toString();
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash + 13 * x;
		hash = hash + 23 * y;
		return hash;
	}

}
