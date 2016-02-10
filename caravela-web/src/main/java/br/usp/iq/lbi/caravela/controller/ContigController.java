package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
import br.usp.iq.lbi.caravela.dto.FeatureTO;
import br.usp.iq.lbi.caravela.dto.ReadOnContigTO;
import br.usp.iq.lbi.caravela.dto.featureViewer.FeatureViewerDataTO;
import br.usp.iq.lbi.caravela.model.Contig;
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
		List<Read> readsOnContig = contigManager.searchReadOnContigByContigId(contigId);
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = contigControllerHelper.createConsensusFeatureViwer(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(featureViewerConsensusDataMap).serialize();
	}
	
	@Get
	@Path("/contig/boundariesReadsOnContig/{contigId}/{rank}")
	public void boundariesOnContig(Long contigId, String rank) {
//		List<Read> readsOnContig = contigManager.searchReadOnContigByContigId(contigId);
//		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = contigControllerHelper.searchOverlapTaxaOnContig(readsOnContig, rank);
//		result.use(Results.json()).withoutRoot().from(featureViewerConsensusDataMap).serialize();
	}
	
	@Get
	@Path("/contig/overlapTaxaOnContig/{contigId}/{rank}")
	public void overlapTaxaOnContig(Long contigId, String rank) {
		List<Read> readsOnContig = contigManager.searchReadOnContigByContigId(contigId);
		Map<String, List<FeatureViewerDataTO>> featureViewerConsensusDataMap = contigControllerHelper.searchOverlapTaxaOnContig(readsOnContig, rank);
		result.use(Results.json()).withoutRoot().from(featureViewerConsensusDataMap).serialize();
	}
	
	@Get
	@Path("/contig/readsOnContig/{contigId}/{rank}")
	public void readsOnContig(Long contigId, String rank) {
		List<Read> readsOnContig = contigManager.searchReadOnContigByContigId(contigId);
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

		List<ReadOnContigTO> readsOnCotig = contigTO.getReadsOnCotig();

		List<String> reads = new ArrayList<String>();

		for (ReadOnContigTO readOnContigTO : readsOnCotig) {

			StringBuffer readString = new StringBuffer().append("{x:")
					.append(readOnContigTO.getStartAlignment().toString()).append(",").append("y:")
					.append(readOnContigTO.getEndAlignment().toString()).append(",").append("description: \"[")
					.append(getTaxonRank(readOnContigTO)).append("] - ").append(getTaxonName(readOnContigTO))
					.append("\",").append("id: \"")
					.append(readOnContigTO.getReference() + "_" + readOnContigTO.getPair()).append("\"}");

			reads.add(readString.toString());

		}
		
		if(viewingMode == null){
			viewingMode = "readsOnContig";
		}
		result.include("rank", rank);
		result.include("viewingMode", viewingMode);
		result.include("sample", sample);
		result.include("reads", reads);
		result.include("features", features);
		result.include("contig", contigTO);
	}

	private String getTaxonRank(ReadOnContigTO readOnContigTO) {
		if (readOnContigTO.getTaxon() != null) {
			return readOnContigTO.getTaxon().getHank();
		} else {
			return "no rank";
		}
	}

	private String getTaxonName(ReadOnContigTO readOnContigTO) {
		if (readOnContigTO.getTaxon() != null) {
			return readOnContigTO.getTaxon().getScientificName();
		} else {
			return Taxon.NO_TAXON;
		}

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
