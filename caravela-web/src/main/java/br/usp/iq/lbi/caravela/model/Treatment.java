package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="treatment")
public class Treatment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String description;
	
	public Treatment() {}

	public Treatment(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}	

}
