package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.dto.FeatureTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;

public interface FeatureCreator {
	List<Feature> createList(Contig contig, List<FeatureTO> featureTOList);
}
