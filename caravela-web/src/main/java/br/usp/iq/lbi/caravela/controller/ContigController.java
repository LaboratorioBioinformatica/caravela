package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.ContigManager;
import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;
import br.usp.iq.lbi.caravela.model.Taxon;

@Controller
public class ContigController {

	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final ContigManager contigManager;
	private final ContigControllerHelper contigControllerHelper;

	public ContigController() {
		this(null, null, null, null, null, null);
	}

	@Inject
	public ContigController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigDAO contigDAO,
			ContigManager contigManager, ContigControllerHelper readsOnContigHelper) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.contigManager = contigManager;
		this.contigControllerHelper = readsOnContigHelper;

	}

	@Get
	@Path("/contig/consensusReadsOnContig/{contigId}/{rank}")
	public void consensusReadsOnContig(Long contigId, String rank) {
		Contig contig = contigDAO.load(contigId);
		List<Read> readsOnContig = contig.getReads();
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = contigControllerHelper.createConsensusFeatureViwer(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(featureViewerConsensusDataMap).serialize();
	}
	
	@Get
	@Path("/contig/undefinedRegionsOnContig/{contigId}/{rank}")
	public void undefinedRegionsOnContig(Long contigId, String rank) {
		Contig contig = contigDAO.load(contigId);
		List<Read> readsOnContig = contig.getReads();
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = contigControllerHelper.undefinedRegions(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(featureViewerConsensusDataMap).serialize();
	}
	
	@Get
	@Path("/contig/unknowRegionsOnContig/{contigId}/{rank}")
	public void unknowRegionsOnContig(Long contigId, String rank) {
		Contig contig = contigDAO.load(contigId);
		List<Read> readsOnContig = contig.getReads();
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = contigControllerHelper.createUnknowRegions(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(featureViewerDataMap).serialize();
	}
	
	@Get
	@Path("/contig/boundariesRegionsOnContig/{contigId}/{rank}")
	public void boundariesRegionsOnContig(Long contigId, String rank) {
		Contig contig = contigDAO.load(contigId);
		List<Read> readsOnContig = contig.getReads();
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = contigControllerHelper.boundariesRegions(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(featureViewerDataMap).serialize();
	}
	@Get
	@Path("/contig/readsUnclissifiedCouldBeClassified/{contigId}/{rank}")
	public void readsUnclissifiedWouldBeClassified(Long contigId, String rank) {
		Contig contig = contigDAO.load(contigId);
		List<Read> readsOnContig = contig.getReads();
		Map<Taxon, List<Read>> readsUnclissifiedCouldBeClassified = contigControllerHelper.searchbyUnclassifiedReadThatCouldBeClassified(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(readsUnclissifiedCouldBeClassified).serialize();
	}
	
	
	@Get
	@Path("/contig/overlapTaxaOnContig/{contigId}/{rank}")
	public void overlapTaxaOnContig(Long contigId, String rank) {
		Contig contig = contigDAO.load(contigId);
		List<Read> readsOnContig = contig.getReads();
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = contigControllerHelper.searchOverlapTaxaOnContig(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(featureViewerConsensusDataMap).serialize();
	}
	
	@Get
	@Path("/contig/readsOnContig/{contigId}/{rank}")
	public void readsOnContig(Long contigId, String rank) {
		Contig contig = contigDAO.load(contigId);
		List<Read> readsOnContig = contig.getReads();
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = contigControllerHelper.createReadsFeatureViwer(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(featureViewerDataMap).serialize();
	}
	
	@Get
	@Path("/contig/featureOnContig/{contigId}")
	public void featureOnContig(Long contigId) {
		Contig contig = contigDAO.load(contigId);
		List<Feature> features = contig.getFeatures();
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = contigControllerHelper.createFeatureViwerByFeatures(features);
		result.use(Results.json()).withoutRoot().from(featureViewerDataMap).serialize();
	}
	
	@Get
	@Path("/contig/viewer/{contigId}")
	public void ContigViewer(Long contigId) {
		Contig contig = contigDAO.load(contigId);
		Map<String, List<FeatureViewerDataTO>> featureViewerDataMap = new HashMap<>();
		featureViewerDataMap.put("Contig", Arrays.asList(new FeatureViewerDataTO(1, contig.getSize(), "Contig: "+ contig.getReference(), String.valueOf(contig.getId()))));
		result.use(Results.json()).withoutRoot().from(featureViewerDataMap).serialize();
	}


	@Path("/contig/view/{contigId}/{rank}/{viewingMode}")
	public void view(Long contigId, String rank, String viewingMode) {
		Contig contig = contigDAO.load(contigId);
		ContigTO contigTO = contigManager.searchContigById(contigId);
		
		List<Read> readsOnContig = contig.getReads();
		Double INT = contigControllerHelper.calculateIndexOfNOTaxon(contig, readsOnContig, rank);
		Double IConfT = contigControllerHelper.calculateIndexOfConfusionTaxonomic(contig, readsOnContig, rank);
		Double IVCT = contigControllerHelper.calculateIndexOfVerticalConsistencyTaxonomic(contig, readsOnContig, rank);
		
		Double IConsT = contigControllerHelper.calculateIndexOfConsistencyTaxonomicByCountReads(readsOnContig, rank);
		
		Double IGConsT = contigControllerHelper.calculateIndexOfConsistencyTaxonomic(contig, readsOnContig, rank);
		
		Double CTSC = calculateConsistencyTaxonomicScoreOfContig(contig, readsOnContig);
		

		Sample sample = contig.getSample();
		
		if(viewingMode == null){
			viewingMode = "readsOnContig";
		}
		
		
		result.include("rank", rank);
		result.include("viewingMode", viewingMode);
		result.include("sample", sample);
		result.include("contig", contigTO);
		result.include("INT", INT);
		result.include("IConfT", IConfT);
		result.include("IVCT", IVCT);
		result.include("IConsT", IConsT);
		result.include("IGConsT", IGConsT);
		result.include("CTSC", CTSC);
		
	}
	
	private Double calculateConsistencyTaxonomicScoreOfContig(Contig contig, List<Read> readsOnContig){
		HashMap<String, Integer> rankMapValue = new HashMap<String, Integer>();
		rankMapValue.put("species", 7);
		rankMapValue.put("genus", 6);
		rankMapValue.put("family", 5);
		rankMapValue.put("order", 4);
		rankMapValue.put("class", 3);
		rankMapValue.put("phylum", 2);
		rankMapValue.put("superkingdom", 1);
		
		
//		List<String> rankList = Arrays.asList("species", "genus", "family", "order", "class", "phylum", "superkingdom");
		
		Double IGGConsT = 0d;
		Double totalRankWeight = 0d;
	
		for (String rank : rankMapValue.keySet()) {
			Integer rankValue = rankMapValue.get(rank);
			totalRankWeight = totalRankWeight + rankValue;
			IGGConsT = (contigControllerHelper.calculateIndexOfConsistencyTaxonomicByCountReads(readsOnContig, rank) * rankValue) + IGGConsT;
		}
		
		
		System.out.println("total rank weight: " + totalRankWeight);
		
		return (IGGConsT / totalRankWeight);
	}


	public void viewJson() throws FileNotFoundException {
		Sample sample = sampleDAO.load(1l);
		SampleFile fileWithAllInformation = sample.getFileWithAllInformation();
		String filePath = fileWithAllInformation.getFilePath();
		Gson gson = new Gson();

		JsonStreamParser parser = new JsonStreamParser(new FileReader(filePath));
		ContigTO contig = gson.fromJson(parser.next(), ContigTO.class);

		result.include("sample", sample);
		result.include("contig", contig);
	}

}
