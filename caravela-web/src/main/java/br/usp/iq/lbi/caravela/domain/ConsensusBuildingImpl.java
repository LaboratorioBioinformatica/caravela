package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;

@RequestScoped
public class ConsensusBuildingImpl implements ConsensusBuilding {

	public List<FeatureViewerDataTO> buildConsensus(List<FeatureViewerDataTO> featureViewerDataTOList) {
		
		Collections.sort(featureViewerDataTOList);
	
		List<FeatureViewerDataTO> listConsensus = new ArrayList<FeatureViewerDataTO>();
		FeatureViewerDataTO current = null;
		
		
		Iterator<FeatureViewerDataTO> iteratorFeature = featureViewerDataTOList.iterator();
		
		while(iteratorFeature.hasNext()){
			
			FeatureViewerDataTO next = iteratorFeature.next();
			
			if(current == null){
				current = next;
				continue;
			}
			
			FeatureViewerDataTO unionFeature = current.unionFeature(next);
			if(unionFeature != null ){
				current = unionFeature;
			} else {
				listConsensus.add(current);
				current = next;
			}
			
			//não existe mais elementos na lista! 
			if( ! iteratorFeature.hasNext()) {
				listConsensus.add(current);
			}
		}
		
		return listConsensus;
	}

}
