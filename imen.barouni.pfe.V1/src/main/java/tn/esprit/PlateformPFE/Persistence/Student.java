package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "Student")
public class Student extends User implements Serializable {

	private String registrationNumber;
	private DiplomaField diplomaField;

	public Student() {
		super();
	}

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	@ManyToOne
	@JoinColumn(name = "diplomaField")
	// TODO Définir la stratégie de la cascade
	public DiplomaField getDiplomaField() {
		return diplomaField;
	}

	public void setDiplomaField(DiplomaField diplomaField) {
		this.diplomaField = diplomaField;
	}

	private static final long serialVersionUID = 1L;

}
