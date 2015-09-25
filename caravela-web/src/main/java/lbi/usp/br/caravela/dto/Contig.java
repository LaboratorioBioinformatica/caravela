package lbi.usp.br.caravela.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Contig {
	
	private final String reference;
	private final String sequence;
	@SerializedName("nOfR")
	private final Integer numberOfreads;
	@SerializedName("nOfF")
	private final Integer numberOfFeatures;
	private final Integer size;
	private final List<Feature> features;
	private final List<ReadOnContig> readsOnCotig;
	
	public Contig(String reference, String sequence, List<Feature> features, List<ReadOnContig> readsOnCotig) {
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
	
	

}
