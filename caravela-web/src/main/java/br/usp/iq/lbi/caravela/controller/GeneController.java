package br.usp.iq.lbi.caravela.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import lbi.usp.br.caravela.dto.TaxonCounterTO;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.GeneSearch;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Sample;

@Controller
public class GeneController {
	
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final GeneSearch geneSearch;
	
	
	public GeneController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public GeneController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigDAO contigDAO, GeneSearch geneSearch) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.geneSearch = geneSearch;
	}
	
	@Post
	@Path("/gene/search/by/producSource")
	public void searchByProductSource(Long sampleId, String productSource) {
		Sample sample = sampleDAO.load(sampleId);
		List<Contig> contigList = geneSearch.SearchContigListBySampleAndGeneProductSource(sample, productSource);
		
		HashMap<Integer,TaxonCounterTO> taxonCounterTOHashMap = new HashMap<Integer, TaxonCounterTO>();
		for (Contig contig : contigList) {
			taxonCounterTOHashMap.putAll(contig.getTaxonCounterTOHashMap());
		}
		ArrayList<TaxonCounterTO> taxonCounterTOList = new ArrayList<TaxonCounterTO>(taxonCounterTOHashMap.values());
		Collections.sort(taxonCounterTOList);
		

		result.include("numberOfContigFound", contigList.size());
		result.include("numberOfTaxonFound", 0);
		result.include("taxonCounterTOList", taxonCounterTOList);
		
		result.include("productSource", productSource);
		result.include("sample", sample);
		result.include("contigList", contigList);
	}
	
	@Get
	@Path("/gene/search/by/producSource/{sampleId}/{productSource}")
	public void searchByProductSourceGet(Long sampleId, String productSource) {
		result.forwardTo(this).searchByProductSource(sampleId, productSource);
	}
	
	
	@Post
	@Path("/gene/search/by/productName")
	public void searchByProductName(Long sampleId, String productName) {
		Sample sample = sampleDAO.load(sampleId);
		List<Feature> featureList = geneSearch.SearchFeatureListBySampleAndGeneProductName(sample, productName);

		result.include("productName", productName);
		result.include("sample", sample);
		result.include("featureList", featureList);
		
	}

	
	
	

}
