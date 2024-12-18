package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pertsona {

	@Id
	private String userName;
	private String password;

	public Pertsona() {}
	
	public Pertsona(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUserName() {
		return this.userName;
	}


}
