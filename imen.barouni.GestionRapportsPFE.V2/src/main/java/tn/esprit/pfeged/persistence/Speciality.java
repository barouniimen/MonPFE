package tn.esprit.pfeged.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "Speciality.findAll", query = "SELECT s FROM Speciality s"),
		@NamedQuery(name = "Speciality.findById", query = "SELECT s FROM Speciality s WHERE s.id = :id"),
		@NamedQuery(name = "Speciality.findByTitle", query = "SELECT s FROM Speciality s WHERE s.title = :title") })
public class Speciality implements Serializable {

	private int id;
	private String title;
	private List<ClassGroup> classGroups;
	private static final long serialVersionUID = 1L;

	public Speciality() {
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(mappedBy = "speciality")
	public List<ClassGroup> getClassGroups() {
		return classGroups;
	}

	public void setClassGroups(List<ClassGroup> classGroups) {
		this.classGroups = classGroups;
	}

}
