package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.usp.iq.lbi.caravela.dto.TaxonCounterTO;


@Entity
@Table(name="contig")
public class Contig implements Serializable {
	
	private static final long serialVersionUID = -98237573034655829L;
	
	@Id
	@GeneratedValue
	private  Long id;
	
	@ManyToOne
	private Sample sample;
	
	private String reference;
	
	@Basic(fetch=FetchType.LAZY)
	private String sequence;
	
	private Integer size;
	
	@Column(name="number_of_reads")
	private Integer numberOfReads;
	
	@Column(name="number_of_reads_classified")
	private Integer numberOfReadsClassified;
	
	@Column(name="number_of_features")
	private Integer numberOfFeatures;
	
	@Column(name="tii")
	private Double taxonomicIdentificationIndex; 
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="contig")
	private List<Feature> features;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="contig")
	private List<Read> reads;
	
	public Contig() {}
	
	public Contig(Sample sample, String reference, String sequence, Integer size, Integer nOfR, Integer nOfRC, Integer nOfF, Double taxonomicIdentificationIndex) {
		this.sample = sample;
		this.reference = reference;
		this.sequence = sequence;
		this.size = size;
		this.numberOfReads = nOfR;
		this.numberOfReadsClassified = nOfRC;
		this.numberOfFeatures = nOfF;
		this.taxonomicIdentificationIndex = taxonomicIdentificationIndex;
	}

	public Sample getSample() {
		return sample;
	}

	public String getReference() {
		return reference;
	}
	
	public List<Read> getReads(){
		return reads;
	}

	public String getSequence() {
		return sequence;
	}
	
	public Integer getSize(){
		return size;
	}
	
	public Double getTaxonomicIdentificationIndex(){
		return taxonomicIdentificationIndex;
	}
	
	public Long getId(){
		return id;
	}

	public Integer getNumberOfReads() {
		return numberOfReads;
	}

	public Integer getNumberOfReadsClassified() {
		return numberOfReadsClassified;
	}
	
	public List<Feature> getFeatures(){
		return this.features;
	}

	public Integer getNumberOfFeatures() {
		return numberOfFeatures;
	}
	
	
	public List<Taxon> getTaxons(){
		ArrayList<Taxon> taxons = new ArrayList<Taxon>();
		
		for (Read read : this.reads) {
			Taxon taxon = read.getTaxon();
			if(taxon != null) {
				taxons.add(taxon);
			}
		}
		return taxons;
	}
	
	public List<TaxonCounterTO> getTaxonCounterTOList(){
		
		HashMap<Long, TaxonCounterTO> taxonCounterTOhashMap = new HashMap<Long, TaxonCounterTO>();
		
		for (Read read : this.reads) {
			Taxon taxon = read.getTaxon();
			if(taxon != null) {
				Long taxonomyId = taxon.getTaxonomyId();
				TaxonCounterTO taxonCounterTO = taxonCounterTOhashMap.get(taxonomyId);
				if(taxonCounterTO == null){
					taxonCounterTOhashMap.put(taxonomyId, new TaxonCounterTO(taxon));
				} else {
					taxonCounterTO.addOne();
				}
			}
		}
		List<TaxonCounterTO> taxonCounterList = new ArrayList<TaxonCounterTO>(taxonCounterTOhashMap.values());
		return taxonCounterList;
	}
	
	
	public HashMap<Long, TaxonCounterTO> getTaxonCounterTOHashMap(){
		
		HashMap<Long, TaxonCounterTO> taxonCounterTOhashMap = new HashMap<Long, TaxonCounterTO>();
		
		for (Read read : this.reads) {
			Taxon taxon = read.getTaxon();
			if(taxon != null) {
				Long taxonomyId = taxon.getTaxonomyId();
				TaxonCounterTO taxonCounterTO = taxonCounterTOhashMap.get(taxonomyId);
				if(taxonCounterTO == null){
					taxonCounterTOhashMap.put(taxonomyId, new TaxonCounterTO(taxon));
				} else {
					taxonCounterTO.addOne();
				}
			}
		}
		return taxonCounterTOhashMap;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Contig)){
			return false;
		}
		if(obj == this){
			return true;
		}
		
		Contig contig = (Contig) obj;
		
		return this.reference.equals(contig.getReference()) &&
		this.sample.equals(contig.getSample()) &&
		this.size.equals(contig.getSize());
		
	}
	

}
