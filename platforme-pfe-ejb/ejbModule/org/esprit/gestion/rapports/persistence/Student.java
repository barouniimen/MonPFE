package org.esprit.gestion.rapports.persistence;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
/**
 * @author Imen Barouni
 *
 */
@Entity
@DiscriminatorValue(value = "Student")
@NamedQueries({
	@NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
	@NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id"),
	@NamedQuery(name = "Student.findByEmail", query = "SELECT s FROM Student s WHERE s.email = :email"),
	@NamedQuery(name = "Student.findByFirstName", query = "SELECT s FROM Student s WHERE s.firstName = :firstName"),
	@NamedQuery(name = "Student.findByLastName", query = "SELECT s FROM Student s WHERE s.lastName = :lastName"),
	@NamedQuery(name = "Student.findByLogin", query = "SELECT s FROM Student s WHERE s.login = :login"),
	@NamedQuery(name = "Student.findByPassword", query = "SELECT s FROM Student s WHERE s.password = :password"),
	@NamedQuery(name = "Student.findByLoginAndPassword", query = "SELECT s FROM Student s WHERE s.login = :login and s.password = :password"),
	@NamedQuery(name = "Student.findByPhoneNumber", query = "SELECT s FROM Student s WHERE s.phoneNumber = :phoneNumber"),
	@NamedQuery(name = "Student.findByProjectExist", query = "SELECT s FROM Student s WHERE s.project IS NOT NULL"),
	@NamedQuery(name = "Student.findByProjectNull", query = "SELECT s FROM Student s WHERE s.project IS NULL"),
	
	@NamedQuery(name = "Student.findByRegistrationNumber", query = "SELECT s FROM Student s WHERE s.registrationNumber = :registrationNumber")})
public class Student extends User implements Serializable {

	private String registrationNumber;
	private List<Registration> registrations;
	private Project project;
	private StorageSpace storageSpace;

	
	
	

	public Student() {
		super();
	}

	@Column(unique=true)
	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	@OneToMany(mappedBy="student")
	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}

	@OneToOne
	public StorageSpace getStorageSpace() {
		return storageSpace;
	}

	public void setStorageSpace(StorageSpace storageSpace) {
		this.storageSpace = storageSpace;
	}

	@OneToOne
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	private static final long serialVersionUID = 1L;

}
