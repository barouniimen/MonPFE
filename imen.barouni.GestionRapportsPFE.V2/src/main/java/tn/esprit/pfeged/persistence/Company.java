package tn.esprit.pfeged.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Company implements Serializable {

	private String adress;
	private String name;
	private static final long serialVersionUID = 1L;

	public Company() {
		super();
	}

	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
