package br.usp.iq.lbi.caravela.dto;

import java.util.ArrayList;
import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.FeatureAnnotation;
import br.usp.iq.lbi.caravela.model.GeneProduct;
import br.usp.iq.lbi.caravela.model.Philodist;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;

public class ContigConverter {
	
	public ContigTO toContigTO(Contig contig, List<Feature> features, List<Read> reads){
		
		
		List<FeatureTO> featuresTO = new ArrayList<FeatureTO>();
		List<ReadOnContigTO> readsOnContig = new ArrayList<ReadOnContigTO>();
		
		for (Feature feature : features) {
			FeatureTO featureTO = createFeatureTO(feature);
			featuresTO.add(featureTO);
		}
		
		for (Read read : reads) {
			ReadOnContigTO readOnContigTO =  createReadOnContig(read);
			readsOnContig.add(readOnContigTO);
		}
		
		ContigTO contigTO = new ContigTO(contig.getId(), contig.getReference(), contig.getSequence(), featuresTO, readsOnContig);
		return contigTO;
	}

	private ReadOnContigTO createReadOnContig(Read read) {
		TaxonTO taxonTO = null;
		
		if(read.hasTaxon()){
			Taxon taxon = read.getTaxonByRank("genus");
			
			if(taxon == null){
				taxon = read.getTaxon();
			}
			taxonTO = new TaxonTO.Builder().setScientificName(taxon.getScientificName()).setHank(taxon.getRank())
							.setTaxonomyId(taxon.getTaxonomyId()).setScore(read.getTaxonScore()).build();
		}
		
		return new ReadOnContigTO(read.getReference(), read.getSequence(), 
				read.getStartAlignment(),  read.getEndAlignment(), read.getCigar(), read.getFlagAlignment(), read.getPair(), taxonTO);
	}

	private FeatureTO createFeatureTO(Feature feature) {
		
		return new FeatureTO(feature.getId(), feature.getType(), feature.getStart(), feature.getEnd(), feature.getStrand(), createTaxonTo(feature), createAnnotationTO(feature), createGeneProduct(feature), createPhiloDistTo(feature));
	}
	
	
	
	private List<FeatureAnnotationTO> createAnnotationTO(Feature feature) {
		List<FeatureAnnotationTO> list = new ArrayList<>();
		List<FeatureAnnotation> featureAnnotations = feature.getFeatureAnnotations();
		
		if(featureAnnotations != null && ! featureAnnotations.isEmpty()){
			for (FeatureAnnotation fa : featureAnnotations) {
				
				FeatureAnnotationTO faTO = new FeatureAnnotationTO( FeatureAnnotationTypeTO.valueOf(fa.getType().toString()), fa.getName(),
						fa.getIdentity(), fa.getAlignLength(), fa.getQueryStart(), fa.getQueryEnd(), fa.getSubjectStart(), 
						fa.getSubjectEnd(), fa.getEvalue(), fa.getBitScore());
				list.add(faTO);
			}
		}
		
		return list;
	}

	private TaxonTO createTaxonTo(Feature feature) {
		Taxon taxon = feature.getTaxon();
		TaxonTO taxonTO = null;
		if(taxon != null){
			taxonTO = new TaxonTO.Builder().setTaxonomyId(taxon.getTaxonomyId())
					.setScientificName(taxon.getScientificName())
					.setHank(taxon.getRank())
					.build();
		}
		return taxonTO;
	}

	private PhiloDistTO createPhiloDistTo(Feature feature) {
		Philodist philodist = feature.getPhilodist();
		PhiloDistTO philoDistTO = null;
		if(philodist != null){
			philoDistTO =  new PhiloDistTO(philodist.getGeneOID(), philodist.getTaxonOID(), philodist.getIdentity(), philodist.getLineage());
		}
		return philoDistTO;
	}

	private GeneProductTO createGeneProduct(Feature feature) {
		GeneProduct geneProduct = feature.getGeneProduct();
		GeneProductTO geneProductTO = null;
		if(geneProduct != null) {
			geneProductTO = new GeneProductTO(geneProduct.getProduct(), geneProduct.getSource());
		}
		
		return geneProductTO;
		
	}

}
