package org.esprit.gestion.rapports.persistence;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
	@NamedQuery(name = "Student.findByRegistrationNumber", query = "SELECT s FROM Student s WHERE s.registrationNumber = :registrationNumber"),
	@NamedQuery(name = "Student.findByAllocatedSpace", query = "SELECT s FROM Student s WHERE s.storageSpace.allocatedSpace = :allocatedSpace") })
public class Student extends User implements Serializable {

	private String registrationNumber;
	private List<Registration> registrations;
	private StorageSpace storageSpace;

	public Student() {
		super();
	}

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	@OneToMany(mappedBy="student",cascade=CascadeType.PERSIST)
	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}

	
	public StorageSpace getStorageSpace() {
		return storageSpace;
	}

	public void setStorageSpace(StorageSpace storageSpace) {
		this.storageSpace = storageSpace;
	}

	private static final long serialVersionUID = 1L;

}
