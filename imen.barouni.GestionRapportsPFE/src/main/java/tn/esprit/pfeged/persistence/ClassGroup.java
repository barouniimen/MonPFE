package tn.esprit.pfeged.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "Classgroup.findAll", query = "SELECT c FROM ClassGroup c"),
		@NamedQuery(name = "Classgroup.findById", query = "SELECT c FROM ClassGroup c WHERE c.id = :id"),
		@NamedQuery(name = "Classgroup.findByReference", query = "SELECT c FROM ClassGroup c WHERE c.reference = :reference") })
public class ClassGroup implements Serializable {

	private int id;
	private String reference;
	private Speciality speciality;
	private List<Registration> registrations;
	private static final long serialVersionUID = 1L;

	public ClassGroup() {
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

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@ManyToOne
	@JoinColumn(name = "speciality")
	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	@OneToMany(mappedBy = "classGroup")
	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}

}
