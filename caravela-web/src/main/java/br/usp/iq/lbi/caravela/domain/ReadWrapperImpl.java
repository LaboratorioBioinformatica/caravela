package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class ReadWrapperImpl implements ReadWrapper {
	
	@Inject private ReadManager readManager;
	
	public Map<Taxon, List<Read>> groupBy(List<Read> reads, String taxonomicRank) {
		
		
		Map<Taxon,List<Read>> readsGroupedByTaxon = new HashMap<Taxon, List<Read>>();
		
		
		for (Read read : reads) {
			if(read.isMapping()){
				if(read.hasTaxon()){
					//LOADING LINEAGEM!!!
					HashMap<String, Taxon> lineagem = readManager.loadLineagem(read);
					read.setLineagem(lineagem);
					
					
					Taxon taxon = read.getTaxonByRank(taxonomicRank);
					if(taxon == null){
						//taxon = read.getTaxon(); // get any taxon assigned!!!!
						List<Read> noTaxonList = readsGroupedByTaxon.get(Taxon.getNOTaxon());
						readsGroupedByTaxon.put(Taxon.getNOTaxon(), addReadInNoTaxonList(read, noTaxonList));
						continue;
					}
					
					List<Read> taxonList = readsGroupedByTaxon.get(taxon);
					if(taxonList != null){
						taxonList.add(read);
					} else {
						taxonList = new ArrayList<Read>();
						taxonList.add(read);
						readsGroupedByTaxon.put(taxon, taxonList);
					}
					
				} else {
					List<Read> noTaxonList = readsGroupedByTaxon.get(Taxon.getNOTaxon());
					readsGroupedByTaxon.put(Taxon.getNOTaxon(), addReadInNoTaxonList(read, noTaxonList));
				}
			}
			
		}
		return readsGroupedByTaxon;
	}

	private List<Read> addReadInNoTaxonList(Read read, List<Read> noTaxonList) {
		if(noTaxonList != null){
			noTaxonList.add(read);
		} else {
			noTaxonList = new ArrayList<Read>();
			noTaxonList.add(read);
		}
		return noTaxonList;
	}

}
