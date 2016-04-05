package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.domain.TaxonomySearch;
import br.usp.iq.lbi.caravela.dto.GeneProductTO;
import br.usp.iq.lbi.caravela.dto.search.GeneProductCounterTO;
import br.usp.iq.lbi.caravela.dto.search.TaxonCounterTO;
import br.usp.iq.lbi.caravela.dto.search.TaxonomomySearchTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;

import com.google.gson.Gson;

@Controller
public class TaxonomyController {
	
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final TaxonomySearch taxonomySearch; 
	private final TaxonDAO taxonDAO; 
	
	
	public TaxonomyController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public TaxonomyController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigDAO contigDAO, TaxonomySearch taxonomySearch, TaxonDAO taxonDAO) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.taxonomySearch = taxonomySearch;
		this.taxonDAO = taxonDAO;
	}
	
	
	@Path("/taxonomy/search/{sampleId}/{scientificName}")
	public void search(Long sampleId, String scientificName) {
		Sample sample = sampleDAO.load(sampleId);
		TaxonomomySearchTO searchTaxonomicSearchTO = taxonomySearch.searchTaxonomicSearchTOBySampleAndScientificName(sample, scientificName);
		
		
		List<GeneProductCounterTO> geneProductCounterToList = searchTaxonomicSearchTO.getGeneProductCounterTo();

		result.include("geneProductCounterToList", geneProductCounterToList);
		result.include("numberOfContigFound", searchTaxonomicSearchTO.getNumberOfContigFound());
		result.include("numberOfTaxonFound", searchTaxonomicSearchTO.getNumberOfTaxonFound());
		result.include("scientificName", scientificName);
		result.include("sample", sample);
		result.include("contigList", searchTaxonomicSearchTO.getContigs());
		
	}
	
	@Path("/taxonomy/search/{sampleId}/{taxonomyId}/{taxonCoverage}")
	public void search(Long sampleId, Long taxonomyId, Double taxonCoverage) {
		Sample sample = sampleDAO.load(sampleId);
		
		List<Contig> contigList = taxonomySearch.SearchContigBySampleTaxonomyIdAndTaxonCovarage(sample, taxonomyId, taxonCoverage);
		Hashtable<String, GeneProductCounterTO> geneProductCounterTOHashTable = new Hashtable<String, GeneProductCounterTO>();
		
		for (Contig contig : contigList) {
			createGeneProductCounterTOHashMap(contig.getFeatures(), geneProductCounterTOHashTable);
		}
		
		List<GeneProductCounterTO> geneProductCounterTOList = new ArrayList<GeneProductCounterTO>(geneProductCounterTOHashTable.values());
		Collections.sort(geneProductCounterTOList);
		
		
		Taxon taxon = taxonDAO.load(taxonomyId);
		
		Gson gson = new Gson();
		
		
		result.include("geneProductCounterJson", gson.toJson(geneProductCounterTOList));
		result.include("taxonCoverage", taxonCoverage);
		result.include("geneProductCounterToList", geneProductCounterTOList);
		result.include("numberOfContigFound", contigList.size());
		result.include("scientificName", taxon.getScientificName());
		result.include("sample", sample);
		result.include("contigList", contigList);
		
	}

	
	@Post
	@Path("/taxonomy/search/fragment")
	public void searchFragment(Long sampleId, String scientificName, Double taxonCoverage) {
		Sample sample = sampleDAO.load(sampleId);
		List<TaxonCounterTO> taxonCounterTOList = taxonomySearch.searchTaxonCounterTOBySampleAndScientificName(sample, scientificName, taxonCoverage);
		
		result.include("scientificName", scientificName);
		result.include("sample", sample);
		result.include("taxonCounterTOList", taxonCounterTOList);
		result.include("taxonCoverage", taxonCoverage);
		
	}
	
	private void createGeneProductCounterTOHashMap(List<Feature> features, Hashtable<String,GeneProductCounterTO> geneProductCounterTOHashTable) {
		for (Feature feature : features) {
			String productSource = feature.getProductSource();
			if(productSource != null){
				GeneProductTO geneProduct = new GeneProductTO(feature.getProductName(), productSource);
				GeneProductCounterTO geneProductCounterTO = geneProductCounterTOHashTable.get(productSource);
				
				if(geneProductCounterTO == null){
					geneProductCounterTOHashTable.put(productSource, new GeneProductCounterTO(geneProduct));
				} else {
					geneProductCounterTO.addOne();
				}
			}
		}
	}


	
	
	

}
