package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: TeachingUnit
 * 
 */
@Entity
public class TeachingUnit implements Serializable {

	private int id;
	private String name;
	private String localization;
	private List<Teacher> teachers;
	private static final long serialVersionUID = 1L;

	public TeachingUnit() {
		super();
	}

	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalization() {
		return this.localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	@OneToMany(mappedBy = "teachingUnit", cascade = CascadeType.ALL)
	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
}
