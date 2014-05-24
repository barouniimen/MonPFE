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
	    @NamedQuery(name = "Registration.findByClassGroupId", query = "SELECT r FROM Registration r WHERE r.registrationPK.classGroupId = :classGroupId"),
		@NamedQuery(name = "Registration.findByAcademicYandStudentId", query = "SELECT r FROM Registration r WHERE r.academicYear = :academicYear and r.registrationPK.userId = :userId"),
		@NamedQuery(name = "Registration.findByUserId", query = "SELECT r FROM Registration r WHERE r.registrationPK.userId = :userId") })
public class Registration implements Serializable {

	private RegistrationPK registrationPK;
	private Student student;
	private ClassGroup classGroup;
	private String academicYear;
	private static final long serialVersionUID = 1L;

	public Registration(RegistrationPK registrationPK, Student student,
			ClassGroup classGroup, String academicYear) {
		super();
		this.registrationPK = registrationPK;
		this.academicYear = academicYear;
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
	@JoinColumn(insertable = false, updatable = false, referencedColumnName = "id",name = "userId")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, referencedColumnName = "id",name = "classGroupId")
	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

}
