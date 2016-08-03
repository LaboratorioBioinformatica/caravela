package br.usp.iq.lbi.caravela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="system_user")
public class User implements Serializable {
	
	private static final long serialVersionUID = 6629252658777965035L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="user_name")
	private String userName;
	private String name;
	private String password;
	
	public User() {}
	
	public User(String userName, String name, String password) {
		this.userName = userName;
		this.name = name;
		this.password = password;
	}
	
	public String getName(){
		return name;
	}

	public String getPassword() {
		return password;
	}
	
	public String getUserName(){
		return userName;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if( ! (obj instanceof User)){
			return false;
		}
		if(obj == this){
			return true;
		}
		
		User other = (User) obj;
		
		return this.userName.equals(other.getUserName()) && this.password.equals(other.getPassword());
	}
	
	
	

}
