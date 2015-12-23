package br.usp.iq.lbi.caravela.dto;

import java.util.ArrayList;
import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
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
			Taxon taxon = read.getTaxonLineagemByRank("genus");
			
			if(taxon == null){
				taxon = read.getTaxon();
			}
			taxonTO = new TaxonTO.Builder().setScientificName(taxon.getScientificName()).setHank(taxon.getRank())
							.setTaxonomyId(taxon.getTaxonomyId()).setScore(read.getTaxonScore()).build();
		}
		
		return new ReadOnContigTO(read.getReference(), read.getSequence(), 
				read.getStartAlignment(),  read.getEndAlignment(), read.getFlagAlignment(), read.getPair(), taxonTO);
	}

	private FeatureTO createFeatureTO(Feature feature) {
		GeneProductTO geneProduct = createGeneProduct(feature);
		PhiloDistTO philoDistTO = createPhiloDistTo(feature);
		
		return new FeatureTO(feature.getSource(), feature.getType(), 
				feature.getStart(), feature.getEnd(), feature.getStrand(), geneProduct, philoDistTO);
	}

	private PhiloDistTO createPhiloDistTo(Feature feature) {
		//this version no has Philo dist on CONTIG ENTITY!
		return null;
	}

	private GeneProductTO createGeneProduct(Feature feature) {
		String productName = feature.getProductName();
		String productSource = feature.getProductSource();
		
		if(productName != null && productSource != null) {
			return new GeneProductTO(productName, productSource);
		} else {
			return null;
		}
	}

}