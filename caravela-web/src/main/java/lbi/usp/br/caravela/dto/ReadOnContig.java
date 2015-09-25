package lbi.usp.br.caravela.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;



public class ReadOnContig {
	private final String reference;
	private final String sequence;
	@SerializedName("start")
	private final Integer startAlignment;
	@SerializedName("end")
	private final Integer endAlignment;
	private final Integer flag;
	private final Integer pair;
	private final List<Taxon> taxons;
	
	public ReadOnContig(String reference, String sequence, Integer startAlignment, Integer endAlignment, Integer flag, Integer pair, List<Taxon> taxons) {
		this.reference = reference;
		this.sequence = sequence;
		this.startAlignment = startAlignment;
		this.endAlignment = endAlignment;
		this.flag = flag;
		this.pair = pair;
		this.taxons = taxons;	
		
	}
	
	
	
	

}
