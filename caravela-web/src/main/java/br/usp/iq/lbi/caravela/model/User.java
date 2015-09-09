package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 6629252658777965035L;
	
	private Long id;
	private String name;
	private String password;
	
	public User() {}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getName(){
		return name;
	}
	

}