package lbi.usp.br.caravela.dto.featureViewer;

import java.io.Serializable;

public class FeatureViewerDataTO implements Serializable {
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
}
