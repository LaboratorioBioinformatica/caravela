package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	@Basic(fetch=FetchType.LAZY)
	private String reference;
	@Basic(fetch=FetchType.LAZY)
	private String sequence;
	private Integer lenth;
	private Integer pair;
	
	@Embedded
	private TaxonomicAssignment taxonomicAssignment;
	
	@Embedded
	private  Mapping mapping;
	
	public Read() {}
	
	public Read(String reference, Sample sample, Contig contig, String sequence, Integer lenth, Integer pair, Mapping mapping) {
		this.reference = reference;
		this.sample = sample;
		this.contig = contig;
		this.sequence = sequence;
		this.lenth = lenth;
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
	
	public String getCigar(){
		return mapping.getCigar();
	}
	
	public Contig getContig(){
		return this.contig;
	}
	
	public boolean isMapping(){
		return mapping.isMapping();
	}
	
	public Integer getLenth(){
		return lenth;
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
	
	public String getScientificName(){
		String scientificName = null;
		if(hasTaxon()){
			scientificName = getTaxon().getScientificName();
		}
		return scientificName;
	}
	
	public String getScientificNameByRank(String rank){
		String scientificName = null;
		if(rank != null){
			Taxon taxon = getTaxonByRank(rank);
			if(taxon != null ){
				scientificName = taxon.getScientificName();
			}
		}
		return scientificName;
	}
	
	public Taxon getTaxonByRank(String rank){
		if(hasTaxon()){
			return taxonomicAssignment.getTaxonByRank(rank);
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
	
	@Override
	public String toString() {
		String toString = new StringBuilder().append(this.reference).append(":").append(this.sample.getName()).append(":").append(this.pair).toString();
		return toString;
	}
	

}
