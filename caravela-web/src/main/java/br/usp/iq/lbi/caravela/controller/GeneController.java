package br.usp.iq.lbi.caravela.controller;

import java.util.List;

import javax.inject.Inject;

import lbi.usp.br.caravela.dto.search.GeneProductCounterTO;
import lbi.usp.br.caravela.dto.search.TaxonCounterTO;
import lbi.usp.br.caravela.dto.search.TaxonomomySearchTO;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.TaxonomySearch;
import br.usp.iq.lbi.caravela.model.Sample;

@Controller
public class GeneController {
	
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	
	
	public GeneController() {
		this(null, null, null, null);
	}
	
	@Inject
	public GeneController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigDAO contigDAO) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
	}
	
	
	@Post
	@Path("/gene/search/by/producSource/{sampleId}/{producSource}")
	public void searchByProductSource(Long sampleId, String producSource) {
		Sample sample = sampleDAO.load(sampleId);
		
//		List<GeneProductCounterTO> geneProductCounterToList = searchTaxonomicSearchTO.getGeneProductCounterTo();

//		result.include("numberOfContigFound", searchTaxonomicSearchTO.getNumberOfContigFound());
//		result.include("numberOfTaxonFound", searchTaxonomicSearchTO.getNumberOfTaxonFound());
		
		result.include("producSource", producSource);
		result.include("sample", sample);
//		result.include("contigList", searchTaxonomicSearchTO.getContigs());
		
	}
	
	@Post
	@Path("/gene/search")
	public void searchFragment(Long sampleId, String scientificName) {
		Sample sample = sampleDAO.load(sampleId);
//		List<TaxonCounterTO> taxonCounterTOList = taxonomySearch.searchTaxonCounterTOBySampleAndScientificName(sample, scientificName);

		result.include("scientificName", scientificName);
		result.include("sample", sample);
//		result.include("taxonCounterTOList", taxonCounterTOList);
		
	}

	
	
	

}
