package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="feature")
public class Feature implements Serializable {

	private static final long serialVersionUID = 9071792536581499568L;
	
	@ManyToOne
	private final Contig contig;
	private final String source;
	
	@Column(name="feature_type")
	private final String type;
	@Column(name="start_alignment")
	private final Integer start;
	@Column(name="end_alignment")
	private final Integer end;
	private final Integer strand;
	@Column(name="product_name")
	private final String productName;
	@Column(name="product_source")
	private final String productSource;
	
	public Feature(Contig contig, String source, String type, Integer start, Integer end, Integer strand, String productName, String productSource) {
		this.contig = contig;
		this.source = source;
		this.type = type;
		this.start = start;
		this.end = end;
		this.strand = strand;
		this.productName = productName;
		this.productSource = productSource;
	}

	public String getSource() {
		return source;
	}

	public String getType() {
		return type;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public Integer getStrand() {
		return strand;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductSource() {
		return productSource;
	}
	

}
