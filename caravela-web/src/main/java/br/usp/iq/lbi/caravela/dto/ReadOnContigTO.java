package br.usp.iq.lbi.caravela.dto;

import com.google.gson.annotations.SerializedName;



public class ReadOnContigTO {
	private final String reference;
	private final String sequence;
	@SerializedName("start")
	private final Integer startAlignment;
	@SerializedName("end")
	private final Integer endAlignment;
	private final String cigar;
	private final Integer flag;
	private final Integer pair;
	private final TaxonTO taxon;
	
	public ReadOnContigTO(String reference, String sequence, Integer startAlignment, Integer endAlignment, String cigar, Integer flag, Integer pair, TaxonTO taxon) {
		this.reference = reference;
		this.sequence = sequence;
		this.startAlignment = startAlignment;
		this.endAlignment = endAlignment;
		this.cigar = cigar;
		this.flag = flag;
		this.pair = pair;
		this.taxon = taxon;	
		
	}
	
	public boolean hasTaxon(){
		if(this.taxon != null){
			return  true;
		} else {
			return false;
		}
	}

	public String getReference() {
		return reference;
	}

	public String getSequence() {
		return sequence;
	}

	public Integer getStartAlignment() {
		return startAlignment;
	}

	public String getCigar() {
		return cigar;
	}

	public Integer getEndAlignment() {
		return endAlignment;
	}

	public Integer getFlag() {
		return flag;
	}

	public Integer getPair() {
		return pair;
	}
	
	public Integer getSize(){
		return sequence.length();
	}

	public TaxonTO getTaxon() {
		return taxon;
	}
	

}
