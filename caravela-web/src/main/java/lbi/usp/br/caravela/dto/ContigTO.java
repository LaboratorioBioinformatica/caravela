package lbi.usp.br.caravela.dto;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.google.gson.annotations.SerializedName;

public class ContigTO {
	
	private final String reference;
	private final String sequence;
	@SerializedName("nOfR")
	private final Integer numberOfreads;
	@SerializedName("nOfF")
	private final Integer numberOfFeatures;
	private final Integer size;
	private final List<FeatureTO> features;
	private final List<ReadOnContigTO> readsOnCotig;
	
	public ContigTO(String reference, String sequence, List<FeatureTO> features, List<ReadOnContigTO> readsOnCotig) {
		this.reference = reference;
		this.sequence = sequence;
		this.features = features;
		this.readsOnCotig = readsOnCotig;
		this.numberOfreads = readsOnCotig.size();
		this.numberOfFeatures = features.size();
		this.size = sequence.length();
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("reference: ")
		.append(reference)
		.append("\t")
		.append("Number of reads on contig: ")
		.append(numberOfreads)
		.append("Number of features on contig: ")
		.append(numberOfFeatures).toString();
	}

	public String getReference() {
		return reference;
	}

	public String getSequence() {
		return sequence;
	}

	public Integer getNumberOfreads() {
		return numberOfreads;
	}

	public Integer getNumberOfFeatures() {
		return numberOfFeatures;
	}

	public Integer getSize() {
		return size;
	}

	public List<FeatureTO> getFeatures() {
		return features;
	}

	public List<ReadOnContigTO> getReadsOnCotig() {
		return readsOnCotig;
	}
	
	public Integer getNumberOfReadsClassified(){
		Integer totalNumberOfReadsClassified = 0;
		for (ReadOnContigTO readOnContig : this.readsOnCotig) {
			List<TaxonTO> taxons = readOnContig.getTaxons();
			
			if(taxons != null && ! taxons.isEmpty()){
				totalNumberOfReadsClassified = taxons.size();
				
				for (TaxonTO taxonTO : taxons) {
					System.out.println(taxonTO.getTaxonomyId());
					System.out.println(taxonTO.getScientificName());
				}
			} 
		}
		return totalNumberOfReadsClassified;
	}
	

}
