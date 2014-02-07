package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "Teacher")
public class Teacher extends User implements Serializable {

	private TeacherGrade grade;
	private int coachingHours;
	private TeachingUnit teachingUnit;
	private List<TeacherRole> teacherRoles;
	private static final long serialVersionUID = 1L;

	public Teacher() {
		super();
	}

	@Enumerated(EnumType.STRING)
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
	@JoinColumn(name="teachingUnit")
	public TeachingUnit getTeachingUnit() {
		return teachingUnit;
	}

	public void setTeachingUnit(TeachingUnit teachingUnit) {
		this.teachingUnit = teachingUnit;
	}

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
	public List<TeacherRole> getTeacherRoles() {
		return teacherRoles;
	}

	public void setTeacherRoles(List<TeacherRole> teacherRoles) {
		this.teacherRoles = teacherRoles;
	}
}
