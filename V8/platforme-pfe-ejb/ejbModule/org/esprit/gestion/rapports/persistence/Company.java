package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 * @author Imen Barouni
 *
 */
@Embeddable
public class Company implements Serializable {

	private String adress;
	private String name;
	private static final long serialVersionUID = 1L;

	
	
	public Company(String adress, String name) {
		super();
		this.adress = adress;
		this.name = name;
	}

	public Company() {
		super();
	}

	@Column(name="CompanyAdress")
	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@Column(name="CompanyName")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
