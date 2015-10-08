package br.usp.iq.lbi.caravela.controller;

import java.util.List;

import javax.inject.Inject;

import lbi.usp.br.caravela.dto.search.GeneProductCounterTO;
import lbi.usp.br.caravela.dto.search.TaxonCounterTO;
import lbi.usp.br.caravela.dto.search.TaxonomomySearchTO;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.TaxonomySearch;
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
		TaxonomomySearchTO searchTaxonomicSearchTO = taxonomySearch.searchTaxonomicSearchTOBySampleAndScientificName(sample, scientificName);
		List<GeneProductCounterTO> geneProductCounterToList = searchTaxonomicSearchTO.getGeneProductCounterTo();

		result.include("geneProductCounterToList", geneProductCounterToList);
		result.include("numberOfContigFound", searchTaxonomicSearchTO.getNumberOfContigFound());
		result.include("numberOfTaxonFound", searchTaxonomicSearchTO.getNumberOfTaxonFound());
		result.include("scientificName", scientificName);
		result.include("sample", sample);
		result.include("contigList", searchTaxonomicSearchTO.getContigs());
		
	}
	
	@Path("/taxonomy/search/{sampleId}/fragment/{scientificName}")
	public void searchFragment(Long sampleId, String scientificName) {
		Sample sample = sampleDAO.load(sampleId);
		List<TaxonCounterTO> taxonCounterTOList = taxonomySearch.searchTaxonCounterTOBySampleAndScientificName(sample, scientificName);

		result.include("scientificName", scientificName);
		result.include("sample", sample);
		result.include("taxonCounterTOList", taxonCounterTOList);
		
	}

	
	
	

}
