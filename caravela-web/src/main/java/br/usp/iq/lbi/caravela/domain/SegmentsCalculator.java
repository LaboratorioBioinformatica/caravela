package br.usp.iq.lbi.caravela.domain;

import java.util.List;
import java.util.Map;

import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.dto.featureViewer.Segment;

public interface SegmentsCalculator {
	
	List<Segment> buildUndfinedSegmentsByTaxon (Map<String, List<FeatureViewerDataTO>> featureViewerData);
	

}
