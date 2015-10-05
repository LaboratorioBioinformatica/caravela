package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="feature")
public class Feature implements Serializable {

	private static final long serialVersionUID = 9071792536581499568L;
	
	@Id
	@GeneratedValue
	private  Long id;
	@ManyToOne
	private Contig contig;
	private String source;
	
	@Column(name="feature_type")
	private String type;
	@Column(name="start_alignment")
	private Integer start;
	@Column(name="end_alignment")
	private Integer end;
	private Integer strand;
	@Column(name="product_name")
	private String productName;
	@Column(name="product_source")
	private String productSource;
	
	public Feature() {}
	
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
	
	public Long getId(){
		return id;
	}
	

}
