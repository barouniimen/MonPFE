package edu.esprit.pfe.store.ejb.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Technologie
 * 
 */
@Entity
@Table(name = "eps_techno")
public class Technologie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TECHNO_ID")
	private int idTechno;
	@Column(name = "TECHNO_DESIGNATION")
	private String designation;
	@OneToMany(mappedBy = "thechnologie")
	private List<GraduationProjectTechnologies> graduationProjectTechnologies = new ArrayList<GraduationProjectTechnologies>();
	private static final long serialVersionUID = 1L;

	public Technologie() {
		super();
	}

	public int getIdTechno() {
		return this.idTechno;
	}

	public void setIdTechno(int idTechno) {
		this.idTechno = idTechno;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<GraduationProjectTechnologies> getGraduationProjectTechnologies() {
		return graduationProjectTechnologies;
	}

	public void setGraduationProjectTechnologies(
			List<GraduationProjectTechnologies> graduationProjectTechnologies) {
		this.graduationProjectTechnologies = graduationProjectTechnologies;
	}

}
