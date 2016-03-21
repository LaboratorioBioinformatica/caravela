package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
import br.usp.iq.lbi.caravela.dto.FeatureTO;
import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;
import br.usp.iq.lbi.caravela.model.Taxon;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

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


	@Path("/contig/view/{contigId}/{rank}/{viewingMode}")
	public void view(Long contigId, String rank, String viewingMode) {
		Contig contig = contigDAO.load(contigId);
		ContigTO contigTO = contigManager.searchContigById(contigId);

		Sample sample = contig.getSample();

		List<String> features = new ArrayList<String>();
		List<FeatureTO> featuresTO = contigTO.getFeatures();
		for (FeatureTO featureTO : featuresTO) {

			StringBuffer featureString = new StringBuffer().append("{x:").append(featureTO.getStart().toString())
					.append(",").append("y:").append(featureTO.getEnd().toString()).append(",")
					.append("description: \"").append(featureTO.getType()).append(" - ")
					.append(getFeatureName(featureTO)).append("\"}");
			features.add(featureString.toString());
		}

		if(viewingMode == null){
			viewingMode = "readsOnContig";
		}
		result.include("rank", rank);
		result.include("viewingMode", viewingMode);
		result.include("sample", sample);
		result.include("features", features);
		result.include("contig", contigTO);
	}

	private String getFeatureName(FeatureTO featureTO) {
		if (featureTO.getGeneProduct() != null) {
			return featureTO.getGeneProduct().getProduct();
		} else {
			return "";
		}

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
