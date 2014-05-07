package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r"),
		@NamedQuery(name = "Registration.findByAcademicYear", query = "SELECT r FROM Registration r WHERE r.registrationPK.academicYear = :academicYear"),
		@NamedQuery(name = "Registration.findByClassGroupId", query = "SELECT r FROM Registration r WHERE r.registrationPK.classGroupId = :classGroupId"),
		@NamedQuery(name = "Registration.findByUserId", query = "SELECT r FROM Registration r WHERE r.registrationPK.userId = :userId") })
public class Registration implements Serializable {

	private RegistrationPK registrationPK;
	private Student student;
	private ClassGroup classGroup;
	private static final long serialVersionUID = 1L;

	
	
	public Registration(RegistrationPK registrationPK) {
		super();
		this.registrationPK = registrationPK;
	}

	public Registration() {
		super();
	}

	@EmbeddedId
	public RegistrationPK getRegistrationPK() {
		return registrationPK;
	}

	public void setRegistrationPK(RegistrationPK registrationPK) {
		this.registrationPK = registrationPK;
	}

	@ManyToOne
	@JoinColumn(name = "student")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne
	@JoinColumn(name = "classGroup")
	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((registrationPK == null) ? 0 : registrationPK.hashCode());
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
		Registration other = (Registration) obj;
		if (registrationPK == null) {
			if (other.registrationPK != null)
				return false;
		} else if (!registrationPK.equals(other.registrationPK))
			return false;
		return true;
	}

}
