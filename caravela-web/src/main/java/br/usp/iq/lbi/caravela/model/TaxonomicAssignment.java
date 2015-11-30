package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Embeddable
public class TaxonomicAssignment implements Serializable {
	
	private static final long serialVersionUID = -3270691371612199910L;
	
	@ManyToOne
	private Taxon taxon;
	
	@Transient
	private HashMap<String, Taxon> lineagem;
	
	
	@Column(name="taxon_score")
	private Double taxonScore; 
	
	public TaxonomicAssignment(){}
	
	public TaxonomicAssignment(Taxon taxon, Double taxonScore) {
		this.taxon = taxon;
		this.taxonScore = taxonScore;
		
	}

	public Taxon getTaxon() {
		return taxon;
	}
	
	public Double getTaxonScore() {
		return taxonScore;
	}
	
	public List<Taxon> getLineagem(){
		if(lineagem != null){
			return new ArrayList<Taxon>(lineagem.values());
		} else {
			return Collections.emptyList();
		}
	}
	
	public void setLineagem(HashMap<String, Taxon> lineagem){
		this.lineagem = lineagem;
	}
	
	
	public Taxon getTaxonLineagemByRank(String rank){
		if(lineagem != null){
			return lineagem.get(rank);
		} else {
			return null;
		}
	}
	

}
