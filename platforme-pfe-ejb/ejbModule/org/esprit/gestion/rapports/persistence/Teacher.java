package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
/**
 * @author Imen Barouni
 *
 */
@Entity
@DiscriminatorValue(value = "Teacher")
@NamedQueries({
		@NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t"),
		@NamedQuery(name = "Teacher.findById", query = "SELECT t FROM Teacher t WHERE t.id = :id"),
		@NamedQuery(name = "Teacher.findByEmail", query = "SELECT t FROM Teacher t WHERE t.email = :email"),
		@NamedQuery(name = "Teacher.findByFirstName", query = "SELECT t FROM Teacher t WHERE t.firstName = :firstName"),
		@NamedQuery(name = "Teacher.findByLastName", query = "SELECT t FROM Teacher t WHERE t.lastName = :lastName"),
		@NamedQuery(name = "Teacher.findByLogin", query = "SELECT t FROM Teacher t WHERE t.login = :login"),
		@NamedQuery(name = "Teacher.findByPassword", query = "SELECT t FROM Teacher t WHERE t.password = :password"),
		@NamedQuery(name = "Teacher.findByLoginAndPassword", query = "SELECT t FROM Teacher t WHERE t.login = :login and t.password = :password"),
		@NamedQuery(name = "Teacher.findByPhoneNumber", query = "SELECT t FROM Teacher t WHERE t.phoneNumber = :phoneNumber"),
		@NamedQuery(name = "Teacher.findByCoachingHours", query = "SELECT t FROM Teacher t WHERE t.coachingHours = :coachingHours"),
		@NamedQuery(name = "Teacher.findByGrade", query = "SELECT t FROM Teacher t WHERE t.grade = :grade"),
		@NamedQuery(name = "Teacher.RetrieveGrades", query = "SELECT t.grade FROM Teacher t")})

public class Teacher extends User implements Serializable {

	private TeacherGrade grade;
	private int coachingHours;
	private TeachingUnit teachingUnit;
	private List<TeacherRole> teacherRoles;
	private static final long serialVersionUID = 1L;

	
	
	public Teacher(String firstName, String lastName, String login,
			String password, String email, int phoneNumber, TeacherGrade grade,
			int coachingHours, TeachingUnit teachingUnit) {
		super(firstName, lastName, login, password, email, phoneNumber);
		this.grade = grade;
		this.coachingHours = coachingHours;
		this.teachingUnit = teachingUnit;
	}

	public Teacher() {
		super();
	}

	@Enumerated
	public TeacherGrade getGrade() {
		return this.grade;
	}

	public void setGrade(TeacherGrade grade) {
		this.grade = grade;
	}

	public int getCoachingHours() {
		return this.coachingHours;
	}

	public void setCoachingHours(int coachingHours) {
		this.coachingHours = coachingHours;
	}

	@ManyToOne
	@JoinColumn(name = "teachingUnit")
	public TeachingUnit getTeachingUnit() {
		return teachingUnit;
	}

	public void setTeachingUnit(TeachingUnit teachingUnit) {
		this.teachingUnit = teachingUnit;
	}

	@OneToMany(mappedBy = "teacher")
	public List<TeacherRole> getTeacherRoles() {
		return teacherRoles;
	}

	public void setTeacherRoles(List<TeacherRole> teacherRoles) {
		this.teacherRoles = teacherRoles;
	}
}
