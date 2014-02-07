package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DiplomaField implements Serializable {

	private int id;
	private String category;
	private String localization;
	private List<Student> students;
	private static final long serialVersionUID = 1L;

	public DiplomaField() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocalization() {
		return this.localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	@OneToMany(mappedBy = "diplomaField")
	// TODO Définir la stratégie de la cascade
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
