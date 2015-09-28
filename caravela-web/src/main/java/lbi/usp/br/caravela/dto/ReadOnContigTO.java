package lbi.usp.br.caravela.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;



public class ReadOnContigTO {
	private final String reference;
	private final String sequence;
	@SerializedName("start")
	private final Integer startAlignment;
	@SerializedName("end")
	private final Integer endAlignment;
	private final Integer flag;
	private final Integer pair;
	private final List<TaxonTO> taxons;
	
	public ReadOnContigTO(String reference, String sequence, Integer startAlignment, Integer endAlignment, Integer flag, Integer pair, List<TaxonTO> taxons) {
		this.reference = reference;
		this.sequence = sequence;
		this.startAlignment = startAlignment;
		this.endAlignment = endAlignment;
		this.flag = flag;
		this.pair = pair;
		this.taxons = taxons;	
		
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

	public Integer getEndAlignment() {
		return endAlignment;
	}

	public Integer getFlag() {
		return flag;
	}

	public Integer getPair() {
		return pair;
	}

	public List<TaxonTO> getTaxons() {
		return taxons;
	}

}
