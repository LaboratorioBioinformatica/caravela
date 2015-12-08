package lbi.usp.br.caravela.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ContigTO {
	
	private final Long id;
	private final String reference;
	private final String sequence;
	@SerializedName("nOfR")
	private final Integer numberOfreads;
	@SerializedName("nOfF")
	private final Integer numberOfFeatures;
	private final Integer size;
	private final List<FeatureTO> features;
	private final List<ReadOnContigTO> readsOnCotig;
	
	public ContigTO(Long id, String reference, String sequence, List<FeatureTO> features, List<ReadOnContigTO> readsOnCotig) {
		this.id = id;
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
	
	public Long getId(){
		return id;
	}
	
	public  Double getTaxonomicIdentificationIndex(){
		
		Double totalNumberOfReads = new Double(this.readsOnCotig.size());
		Double totalReadsClassified = new Double(0);
		
		for (ReadOnContigTO readOnContig : readsOnCotig) {
			if(readOnContig.hasTaxon()){
				totalReadsClassified++;
			}
		} 
		
		Double taxonomicIdentificationIndex = new Double(totalReadsClassified / totalNumberOfReads);
		
		if(taxonomicIdentificationIndex.isNaN()){
			taxonomicIdentificationIndex = new Double(0);
		}

		
		return taxonomicIdentificationIndex;
	}
	
	public Integer getNumberOfReadsClassified(){
		Integer totalNumberOfReadsClassified = 0;
		for (ReadOnContigTO readOnContig : this.readsOnCotig) {
			TaxonTO taxon = readOnContig.getTaxon();
			if(taxon != null){
				totalNumberOfReadsClassified ++;
			} 
		}
		return totalNumberOfReadsClassified;
	}
}
