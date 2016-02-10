package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class ReadWrapperImpl implements ReadWrapper {
	
	public Map<String, List<Read>> groupBy(List<Read> reads, String taxonomicRank) {
		
		Map<String,List<Read>> readsGroupedByTaxon = new HashMap<String, List<Read>>();
		
		for (Read read : reads) {
			if(read.isMapping()){
				if(read.hasTaxon()){
					
					String scientificName = read.getScientificNameByRank(taxonomicRank);
					if(scientificName == null){
						scientificName = read.getScientificName();
					}
					
					List<Read> taxonList = readsGroupedByTaxon.get(scientificName);
					if(taxonList != null){
						taxonList.add(read);
					} else {
						taxonList = new ArrayList<Read>();
						taxonList.add(read);
						readsGroupedByTaxon.put(scientificName, taxonList);
					}
					
				} else {
					List<Read> noTaxonList = readsGroupedByTaxon.get(Taxon.NO_TAXON);
					if(noTaxonList != null){
						noTaxonList.add(read);
					} else {
						noTaxonList = new ArrayList<Read>();
						noTaxonList.add(read);
						readsGroupedByTaxon.put(Taxon.NO_TAXON, noTaxonList);
					}
				}
			}
			
		}
		return readsGroupedByTaxon;
	}

}
