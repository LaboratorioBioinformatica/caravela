package br.usp.iq.lbi.caravela.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.domain.ContigManager;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.SampleFile;
import lbi.usp.br.caravela.dto.ContigTO;
import lbi.usp.br.caravela.dto.FeatureTO;
import lbi.usp.br.caravela.dto.ReadOnContigTO;

@Controller
public class ContigController {
	
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	private final ContigManager contigManager;
	
	
	public ContigController() {
		this(null,null,null, null, null);
	}
	
	@Inject
	public ContigController(Result result, WebUser webUser,  SampleDAO sampleDAO, ContigDAO contigDAO, ContigManager contigManager) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.contigManager = contigManager;
		
	}
	
	@Path("/contig/view/{contigId}")
	public void view(Long contigId) {
		Contig contig = contigDAO.load(contigId);
		ContigTO contigTO = contigManager.searchContigById(contigId);
		
		Sample sample = contig.getSample();
		
		
		 List<String> features = new ArrayList<String>();
		List<FeatureTO> featuresTO = contigTO.getFeatures();
		for (FeatureTO featureTO : featuresTO) {
			
				
			StringBuffer featureString = new StringBuffer().append("{x:").append(featureTO.getStart().toString()).append(",")
					 .append("y:").append(featureTO.getEnd().toString()).append(",")
					 .append("description: \"").append(featureTO.getType()).append(" - ").append(getFeatureName(featureTO)).append("\"}");
			features.add(featureString.toString());
		}
		
		 List<ReadOnContigTO> readsOnCotig = contigTO.getReadsOnCotig();
		 
		 List<String> reads = new ArrayList<String>();
		 
		 for (ReadOnContigTO readOnContigTO : readsOnCotig) {
			 
			 StringBuffer readString = new StringBuffer().append("{x:").append(readOnContigTO.getStartAlignment().toString()).append(",")
			 .append("y:").append(readOnContigTO.getEndAlignment().toString()).append(",")
			 .append("description: \"[").append(getTaxonRank(readOnContigTO)).append("] - ").append(getTaxonName(readOnContigTO)).append("\",")
			 .append("id: \"").append(readOnContigTO.getReference()+"_"+readOnContigTO.getPair()).append("\"}");
			 
			 reads.add(readString.toString());
			 
		}
		
		
		result.include("sample", sample);
		result.include("reads", reads);
		result.include("features", features);
		result.include("contig", contigTO);
	}

	private String getTaxonRank(ReadOnContigTO readOnContigTO) {
		if(readOnContigTO.getTaxon() != null) {
			return readOnContigTO.getTaxon().getHank();
		} else {
			return "no rank";
		}
	}

	private String getTaxonName(ReadOnContigTO readOnContigTO) {
		if(readOnContigTO.getTaxon() != null) {
			return readOnContigTO.getTaxon().getScientificName();
		} else {
			return "no taxon";
		}
			
	}

	private String getFeatureName(FeatureTO featureTO) {
		if(featureTO.getGeneProduct() != null){
			return featureTO.getGeneProduct().getProduct();
		} else {
			return "";
		}
		
	}
	
	
	
	
	
	public void viewJson() throws FileNotFoundException{
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
