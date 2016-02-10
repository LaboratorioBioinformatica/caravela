package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;

public interface ConsensusBuilding {
	
	List<FeatureViewerDataTO> buildConsensus(List<FeatureViewerDataTO> featureViewerDataTOList);

}
