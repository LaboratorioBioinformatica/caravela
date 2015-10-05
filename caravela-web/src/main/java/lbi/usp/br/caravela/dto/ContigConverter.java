package lbi.usp.br.caravela.dto;

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
		
		ContigTO contigTO = new ContigTO(contig.getReference(), contig.getSequence(), featuresTO, readsOnContig);
		return contigTO;
	}

	private ReadOnContigTO createReadOnContig(Read read) {
		
		Taxon taxon = read.getTaxon();
		TaxonTO taxonTO = null;
		
		if(taxon != null){
			taxonTO = new TaxonTO.Builder().setScientificName(taxon.getScientificName()).setHank(taxon.getRank())
							.setTaxonomyId(taxon.getTaxonomyId()).setScore(taxon.getScore()).build();
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
