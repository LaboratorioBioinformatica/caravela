package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sequence")
public class Read implements Serializable {
	
	private static final long serialVersionUID = 8538633266293466706L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Sample sample;
	@ManyToOne
	private Contig contig;
	private String reference;
	private String sequence;
	private Integer pair;
	
	@Embedded
	private TaxonomicAssignment taxonomicAssignment;
	
	@Embedded
	private  Mapping mapping;
	
	public Read() {}
	
	public Read(String reference, Sample sample, Contig contig, String sequence, Integer pair, Mapping mapping) {
		this.reference = reference;
		this.sample = sample;
		this.contig = contig;
		this.sequence = sequence;
		this.pair = pair;
		this.mapping = mapping;
	}

	
	public Long getId() {
		return id;
	}

	public String getReference() {
		return reference;
	}

	public String getSequence() {
		return sequence;
	}

	public Integer getPair() {
		return pair;
	}
	
	public Sample getSample() {
		return sample;
	}
	
	public Integer getStartAlignment(){
		return mapping.getStart();
	}
	
	public Integer getEndAlignment(){
		return mapping.getEnd();
	}
	
	public Integer getFlagAlignment(){
		return mapping.getFlag();
	}
	
	public Contig getContig(){
		return this.contig;
	}
	
	public boolean hasTaxon(){
		if(this.taxonomicAssignment != null ){
			return true;
		} else {
			return false;
		}
	}
	
	public Taxon getTaxon(){
		if(hasTaxon()){
			return taxonomicAssignment.getTaxon();
		} else {
			return null;
		}
		
	}
	
	public Taxon getTaxonLineagemByRank(String rank){
		if(hasTaxon()){
			return taxonomicAssignment.getTaxonLineagemByRank(rank);
		} else {
			return null;
		}
	}
	
	
	public void setLineagem(HashMap<String, Taxon> lineagem){
		this.taxonomicAssignment.setLineagem(lineagem);
	}
	
	public List<Taxon> getLineagem(){
		if(hasTaxon()){
			return taxonomicAssignment.getLineagem();
		} else {
			return Collections.emptyList();
		}
	}
	
	public Double getTaxonScore(){
		if(hasTaxon()){
			return taxonomicAssignment.getTaxonScore();
		} else {
			return null;
		}
	}
	
	public void toAssigTaxon(TaxonomicAssignment taxonomicAssignment ){
		this.taxonomicAssignment = taxonomicAssignment;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Read)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		Read read = (Read) obj;
		return this.reference.equals(read.getReference()) && this.sample.getName().equals(read.getSample().getName()) && this.pair == read.getPair(); 
	}	
	

}
