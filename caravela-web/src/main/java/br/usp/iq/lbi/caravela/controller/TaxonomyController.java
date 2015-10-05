package br.usp.iq.lbi.caravela.controller;

import java.util.List;

import javax.inject.Inject;

import org.apache.taglibs.standard.tag.common.core.ForEachSupport;

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
		
//		for (Contig contig : contigList) {
//			System.out.println(contig.getReference());
//			List<Feature> features = contig.getFeatures();
//			
//			for (Feature feature : features) {
//				System.out.println(feature.getProductName());
//			}
//			
//		}
		
		result.include("scientificName", scientificName);
		result.include("sample", sample);
		result.include("contigList", contigList);
		
	}
	

}
