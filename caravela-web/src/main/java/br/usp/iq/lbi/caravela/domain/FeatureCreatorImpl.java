package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.dao.FeatureDAO;
import br.usp.iq.lbi.caravela.dto.FeatureAnnotationTO;
import br.usp.iq.lbi.caravela.dto.FeatureTO;
import br.usp.iq.lbi.caravela.dto.GeneProductTO;
import br.usp.iq.lbi.caravela.dto.PhiloDistTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.FeatureAnnotation;
import br.usp.iq.lbi.caravela.model.FeatureAnnotationType;
import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Philodist;
import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class FeatureCreatorImpl implements FeatureCreator {
	
	private static final Logger logger = LoggerFactory.getLogger(FeatureCreatorImpl.class);
	
	@Inject private FeatureDAO featureDAO;
	@Inject private NCBITaxonFinder ncbiTaxonFinder;
	
	
	public FeatureCreatorImpl() {}
	
	public FeatureCreatorImpl(FeatureDAO featureDAO, NCBITaxonFinder ncbiTaxonFinder) {
		this.featureDAO = featureDAO;
		this.ncbiTaxonFinder = ncbiTaxonFinder;
	}

	@Override
	public List<Feature> createList(Contig contig, List<FeatureTO> featureTOList) {
		List<Feature> featureList = new ArrayList<>();
		
		if(featureTOList != null && ! featureTOList.isEmpty()){
			featureList.addAll(getFeatures(contig, featureTOList));
			
			Integer batchSize = featureList.size();
			for (Feature feature : featureList) {
				featureDAO.addBatch(feature, batchSize);
			}
			
		}
		
		return featureList;
	}


	private List<Feature> getFeatures(Contig contig, List<FeatureTO> featureTOList) {
		List<Feature> features = new ArrayList<>();
		for (FeatureTO featureTO : featureTOList) {
			Feature feature = new Feature(contig, featureTO.getType(), featureTO.getStart(), featureTO.getEnd(), featureTO.getStrand());
			
			feature.toAssigTaxon(getTaxon(featureTO, feature));
			feature.addGeneProduct(getGeneProduct(feature, featureTO));
			feature.addPhilodist(getPhilodist(feature, featureTO));
			
			List<FeatureAnnotation> featureAnnotationList = getFeatureAnnotationList(feature, featureTO);
			for (FeatureAnnotation featureAnnotation : featureAnnotationList) {
				feature.addFeatureAnnotation(featureAnnotation);
			}
			features.add(feature);
		}
		return features;
	}

	private Taxon getTaxon(FeatureTO featureTO, Feature feature) {
		Taxon taxon = null; 
		if(featureTO.hasTaxon()){
			Long taxonomyId = featureTO.getTaxonTO().getTaxonomyId();
			taxon = ncbiTaxonFinder.searchTaxonByNCBITaxonomyId(taxonomyId);
			if(taxon == null){
				logger.warn("NCBI taxonomy id: " + taxonomyId + " not found!");
			} 
		}
		return taxon;
	}

	private List<FeatureAnnotation> getFeatureAnnotationList(Feature feature, FeatureTO featureTO) {
		List<FeatureAnnotation> list = new ArrayList<>();
		if(featureTO.hasFeatureAnnotations()){
			List<FeatureAnnotationTO> annotationTOList = featureTO.getAnnotations();
			for (FeatureAnnotationTO faTO : annotationTOList) {
				
				FeatureAnnotation featureAnnotation = new FeatureAnnotation(feature, FeatureAnnotationType.valueOf(faTO.getType().toString()), faTO.getName(), faTO.getIdentity(), faTO.getAlignLength(), 
						faTO.getQueryStart(), faTO.getQueryEnd(), faTO.getSubjectStart(), faTO.getSubjectEnd(), faTO.getEvalue(), faTO.getBitScore());
				list.add(featureAnnotation);
			}
		}
		return list;
	}

	private Philodist getPhilodist(Feature feature, FeatureTO featureTO) {
		Philodist philodist = null;
		if(featureTO.hasPhilodist()){
			PhiloDistTO philoDistTO = featureTO.getPhiloDist();
			philodist = new Philodist(feature, philoDistTO.getGeneOID(), philoDistTO.getTaxonOID(), philoDistTO.getIdentity(), philoDistTO.getLineage());
		}
		
		return philodist;
	}

	private GeneProduct getGeneProduct(Feature feature, FeatureTO featureTO) {
		GeneProduct geneProduct = null;
		if(featureTO.hasGeneProduct()){
			GeneProductTO geneProductTO = featureTO.getGeneProduct();
			geneProduct = new GeneProduct(feature, geneProductTO.getProduct(), geneProductTO.getSource());
		}
		return geneProduct;
	}

}
