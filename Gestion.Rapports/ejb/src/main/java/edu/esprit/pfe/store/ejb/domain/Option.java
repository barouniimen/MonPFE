package edu.esprit.pfe.store.ejb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Option It a Lookup table should
 * generate hashcode, equals and toString methods with the natural identifier in
 * order to use this class later as collections in the front-end. The
 * designation attribute can be a natural identifier for this class and it must
 * be unique
 * 
 */
@Entity
@Table(name = "eps_option")
public class Option implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPT_ID")
	private int idOption;
	@Column(name = "OPT_DESIGNATION", unique = true)
	private String designation;
	@OneToMany(mappedBy = "option")
	private List<Student> students = new ArrayList<Student>();
	private static final long serialVersionUID = 1L;

	public Option() {
		super();
	}

	public Option(String designation) {
		super();
		this.designation = designation;
	}

	/* BoilerPlate Getters and Setters */

	public int getIdOption() {
		return this.idOption;
	}

	public void setIdOption(int idOption) {
		this.idOption = idOption;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((designation == null) ? 0 : designation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option other = (Option) obj;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return designation;
	}

}
