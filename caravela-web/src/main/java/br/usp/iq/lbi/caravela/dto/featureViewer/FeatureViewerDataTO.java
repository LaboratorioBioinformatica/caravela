package br.usp.iq.lbi.caravela.dto.featureViewer;

import java.io.Serializable;
import java.util.ArrayList;

public class FeatureViewerDataTO implements Comparable<FeatureViewerDataTO>,  Serializable {
	
	private static final long serialVersionUID = 6552981806740144131L;
	
	private Integer x;
	private Integer y;
	private String description;
	private String id;
	
	public FeatureViewerDataTO(Integer x, Integer y, String description, String id) {
		this.x = x;
		this.y = y;
		this.description = description;
		this.id = id;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}
	
	public boolean contains(int time) {
		return time <= y && time >= x;
	}
	
	// the reference is this. The other is on left?  
	public boolean isOnLeft(FeatureViewerDataTO other){
		return other.x < x && other.y < y;
	}
	
	// the reference is this. The other Is on rigth?  
	public boolean isOnRigth(FeatureViewerDataTO other){
		return other.x > x && other.y > y;
	}
	
	public boolean contains(FeatureViewerDataTO other){
		return contains(other.getX()) && contains(other.getY());
	}

	
	public boolean intersects(FeatureViewerDataTO other) {
		// modify to close interval
		return other.getY() >= x && other.getX() <= y;
	}
	
	public Segment getIntersect(FeatureViewerDataTO other) {
		
		Segment segment = null;
		if(this.intersects(other)){
			if(this.contains(other)){
				segment = new Segment(other.getX(), other.getY(), createTaxonList(other));
			} else if(other.contains(this)){
				segment = new Segment(x, y, createTaxonList(other));
			} else if(this.isOnLeft(other)){
				segment = new Segment(x, other.y, createTaxonList(other));
			} else if(this.isOnRigth(other)){
				segment = new Segment(other.x, y, createTaxonList(other));
			}
			
		}
		return segment;
	}

	private ArrayList<String> createTaxonList(FeatureViewerDataTO other) {
		ArrayList<String> taxonList = new ArrayList<String>();
		taxonList.add(getDescription());
		taxonList.add(other.getDescription());
		return taxonList;
	}
	
	public int compareTo(FeatureViewerDataTO other) {
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
	public int hashCode() {
		int hash = 1;
		hash = hash + 13 * x;
		hash = hash + 23 * y;
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof FeatureViewerDataTO)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		FeatureViewerDataTO fvdto = (FeatureViewerDataTO) obj;
		return this.x.equals(fvdto.getX()) && this.y.equals(fvdto.getY()); 
	}
	
	
}
