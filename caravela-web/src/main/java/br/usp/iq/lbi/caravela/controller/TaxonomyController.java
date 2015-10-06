package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import lbi.usp.br.caravela.dto.GeneProductCounterTO;
import lbi.usp.br.caravela.dto.GeneProductTO;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.TaxonomySearch;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Sample;

@Controller
public class TaxonomyController {
	
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final TaxonomySearch taxonomySearch; 
	
	
	public TaxonomyController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public TaxonomyController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigDAO contigDAO, TaxonomySearch taxonomySearch) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.taxonomySearch = taxonomySearch;
	}
	
	
	@Path("/taxonomy/search/{sampleId}/{scientificName}")
	public void search(Long sampleId, String scientificName) {
		Sample sample = sampleDAO.load(sampleId);
		List<Contig> contigList = taxonomySearch.searchListOfContigBySampleAndScientificName(sample, scientificName);
		Integer numberOfContigFound = contigList.size();
		
		Hashtable<String, GeneProductCounterTO> geneProductCounterTOHashMap = new Hashtable<String, GeneProductCounterTO>();
		
		for (Contig contig : contigList) {
			List<Feature> features = contig.getFeatures();
			for (Feature feature : features) {
				String productName = feature.getProductName();
				if(productName != null){
					
					GeneProductTO geneProduct = new GeneProductTO(productName, feature.getProductSource());
					GeneProductCounterTO geneProductCounterTO = geneProductCounterTOHashMap.get(productName);
					
					if(geneProductCounterTO == null){
						geneProductCounterTOHashMap.put(productName, new GeneProductCounterTO(geneProduct));
					} else {
						geneProductCounterTO.addOne();
					}
				}
			}
			
		}
		
		System.out.println(geneProductCounterTOHashMap.size());
		
		
		List<GeneProductCounterTO> geneProductCounterTOList = new ArrayList<GeneProductCounterTO>(geneProductCounterTOHashMap.values());
		
		Collections.sort(geneProductCounterTOList);
		Integer t = 0;
		for (GeneProductCounterTO geneProductCounterTO : geneProductCounterTOList) {
			t++;
			System.out.println(geneProductCounterTO.getProductName() + "  =   " + geneProductCounterTO.getTotal());
			if(t > 10){
				break;
			}
			
		}
		
		
		
		result.include("scientificName", scientificName);
		result.include("sample", sample);
		result.include("contigList", contigList);
		
	}
	

}
