package br.usp.iq.lbi.caravela.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "study")
public class Study {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String description;

	public Study() {
	}

	public Study(String name, String description) {
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Study)) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		Study study = (Study) obj;
		return this.name.equals(study.getName()) && this.description.equals(study.getDescription());
	}

}
