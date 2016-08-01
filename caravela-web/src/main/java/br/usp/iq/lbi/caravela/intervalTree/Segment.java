package br.usp.iq.lbi.caravela.intervalTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Segment<Type extends Comparable<Type>> implements Comparable<Segment<Type>>, Serializable {
	
private static final long serialVersionUID = 6552981806740144131L;
	
	private Integer x;
	private Integer y;
	private List<Type> data;
	
	public Segment(Integer x, Integer y, List<Type> data) {
		this.x = x;
		this.y = y;
		this.data = data;
	}
	
	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}
	
	public List<Type> getData(){
		Collections.sort(data);
		return data;
	}
	
	public Integer getSize(){
		return this.y - this.x;
	}
	
	
	public boolean contains(int time) {
		return time <= y && time >= x;
	}
	
	// the reference is this. The other is on left?  
	public boolean isOnLeft(Segment<?> other){
		return other.x < x && other.y < y;
	}
	
	// the reference is this. The other Is on rigth?  
	public boolean isOnRight(Segment<?> other){
		return other.x > x && other.y > y;
	}
	
	public boolean contains(Segment<?> other){
		return contains(other.getX()) && contains(other.getY());
	}
	
	public boolean intersects(Segment<?> other) {
		// modify to closed interval
		return other.getY() >= x && other.getX() <= y;
	}
	
	public List<Segment<Type>> subtract(Segment<?> other){
		List<Segment<Type>> resultList = new ArrayList<Segment<Type>>();
		
		if(this.intersects(other)){
			if(other.contains(this)){
				return resultList;
			} 
			if(this.contains(other)){
	
				if(hasRemnantSegmentOnRight(this.y, other.y)){
					Segment<Type> remnantSegmentOnRigth = createRemnantSegment(other.y +1, this.y, this.getData());
					resultList.add(remnantSegmentOnRigth);
				}
			
				if (hasRemnantSegmentOnLeft(this.x, other.x -1)) {
					Segment<Type> remnantSegmentOnLeft = createRemnantSegment(this.x, other.x -1, this.getData());
					resultList.add(remnantSegmentOnLeft);
				}
				
			}
			if(this.isOnLeft(other)){
				Segment<Type> remnantSegmentOnRigth = createRemnantSegment(other.y +1, this.y, this.getData());
				resultList.add(remnantSegmentOnRigth);
			}
			if(this.isOnRight(other)){
				Segment<Type> remnantSegmentOnLeft = createRemnantSegment(this.x, other.x -1, this.getData());
				resultList.add(remnantSegmentOnLeft);
			}
		}
		Collections.sort(resultList);
		return resultList;
	}
	
	private Segment<Type> createRemnantSegment(Integer x, Integer y, List<Type> data){
		Segment<Type> segment = null;
		if(x <= y){
			segment = new Segment<Type>(x, y, data);
		}
		return segment;
		
	}
	
	private boolean hasRemnantSegmentOnRight(Integer thisY, Integer otherY){
		if(thisY > otherY){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean hasRemnantSegmentOnLeft(Integer thisX, Integer otherX){
		if(otherX > thisX){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Segment<Type> union(Segment<Type> other){
		Segment<Type> segment = null;
		if(this.intersects(other)){
			List<Type> dataListMerged =  segmentTypeMerge(this.getData(), other.getData());
			if(this.contains(other)){
				segment = new Segment<Type>(this.x, this.y, dataListMerged);
			} else if(other.contains(this)){
				segment = new Segment<Type>(other.x, other.y, dataListMerged);
			} else if(this.isOnLeft(other)) {
				segment = new Segment<Type>(other.x, this.y, dataListMerged);
			} else if(this.isOnRight(other)){
				segment = new Segment<Type>(this.x, other.y, dataListMerged);
			}
		}
		return segment;
	}
	
	public Segment<Type> getIntersect(Segment<Type> other) {
		Segment<Type> segment = null;
		if(this.intersects(other)){
			List<Type> dataListMerged =  segmentTypeMerge(this.getData(), other.getData());
			if(this.contains(other)){
				segment = new Segment<Type>(other.getX(), other.getY(), dataListMerged);
			} else if(other.contains(this)){
				segment = new Segment<Type>(x, y, dataListMerged);
			} else if(this.isOnLeft(other)){
				segment = new Segment<Type>(x, other.y, dataListMerged);
			} else if(this.isOnRight(other)){
				segment = new Segment<Type>(other.x, y, dataListMerged);
			}
			
		}
		return segment;
	}
	
	private List<Type> segmentTypeMerge(List<Type> thisData, List<Type> otherData) {
		if(thisData.equals(otherData)){
			return thisData;
		} else {
			List<Type> segmentDataMerged = new ArrayList<Type>();
			segmentDataMerged.addAll(thisData);
			segmentDataMerged.addAll(otherData);
			return segmentDataMerged;
		}
	}

	public int compareTo(Segment<Type> other) {
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
		if(! (obj instanceof Segment<?>)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		@SuppressWarnings("unchecked")
		Segment<Type> segment = (Segment<Type>) obj;
		return this.x.equals(segment.getX()) && this.y.equals(segment.getY()) && this.getData().equals(segment.getData()); 
	}
	
	@Override
	public String toString() {
		StringBuilder segmentStringBuilder = new StringBuilder().append(getX()).append(":").append(getY()).append(":").append(getData());
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
