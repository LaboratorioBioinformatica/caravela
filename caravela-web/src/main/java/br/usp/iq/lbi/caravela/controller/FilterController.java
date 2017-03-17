package br.usp.iq.lbi.caravela.controller;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dto.ContigFilterParametersTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

@Controller
public class FilterController {
	
	
	private final Result result;
	private final WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	
	
	protected FilterController(){
		this(null, null, null, null);
	}	
	
	@Inject
	public FilterController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigDAO contigDAO) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
	}
	
	@Get
	@Path("/filter/contig/tbr/view/{sampleId}")
	public void filterContigTBR(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		ContigFilterParametersTO filterTBRParameters = new ContigFilterParametersTO(0.5, 0, 0, 0.4, 0.7, TaxonomicRank.GENUS);
		
		result.include("sample", sample);
		result.include("filterTBRParameters", filterTBRParameters);
	}
	
	@Post
	@Path("/filter/contig/tbr")
	@IncludeParameters
	public void filterContigTBR(Long sampleId, ContigFilterParametersTO filterTBRParameters){
		
		Integer firstResult = 0;
		Integer maxResult = 100;
		
		Sample sample = sampleDAO.load(sampleId);
		List<Contig> contigList = contigDAO.FindByContigListTBRBySample(sample, filterTBRParameters.getItg(), filterTBRParameters.getNumberOfFeatures(), filterTBRParameters.getTaxonomicRank(), 
				filterTBRParameters.getNumberOfBorders(), filterTBRParameters.getCt(), filterTBRParameters.getCtv(), firstResult, maxResult);
		
		result.include("sample", sample);
		result.include("contigList", contigList);
		
	}
	
	@Get
	@Path("/filter/contig/pq/view/{sampleId}")
	public void filterContigPQ(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		ContigFilterParametersTO filterPQParameters = new ContigFilterParametersTO(0.5, 0, 1, 0.2, 0.4, TaxonomicRank.GENUS);
		
		result.include("sample", sample);
		result.include("filterPQParameters", filterPQParameters);
	}
	
	@Post
	@Path("/filter/contig/pq")
	@IncludeParameters
	public void filterContigPQ(Long sampleId, ContigFilterParametersTO filterPQParameters){
		
		Integer firstResult = 0;
		Integer maxResult = 100;
		
		Sample sample = sampleDAO.load(sampleId);
		List<Contig> contigList = contigDAO.FindByContigListPQBySample(sample, filterPQParameters.getItg(), filterPQParameters.getNumberOfFeatures(), filterPQParameters.getTaxonomicRank(), 
				filterPQParameters.getNumberOfBorders(), filterPQParameters.getCt(), filterPQParameters.getCtv(), firstResult, maxResult);
		
		result.include("sample", sample);
		result.include("contigList", contigList);
		
	}
	
	

}
