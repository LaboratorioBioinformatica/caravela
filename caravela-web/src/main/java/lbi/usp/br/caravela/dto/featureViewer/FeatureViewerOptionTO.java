package lbi.usp.br.caravela.dto.featureViewer;

import java.io.Serializable;

public class FeatureViewerOptionTO implements Serializable {

	private static final long serialVersionUID = 6070678874934512048L;
	
	private String description;
	private String id;
	
	public FeatureViewerOptionTO(String description, String id) {
		this.description = description;
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

}
