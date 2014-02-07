package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity implementation class for Entity: TeacherRole
 * 
 */
@Entity
public class TeacherRole implements Serializable {

	private TeacherRolePK pk;
	private String role;
	private Teacher teacher;
	private Project project;
	// TODO pourrait être une énumération
	private static final long serialVersionUID = 1L;

	public TeacherRole() {
		super();
	}

	public TeacherRole(TeacherRolePK pk, String role) {
		super();
		this.pk = pk;
		this.role = role;
	}

	@EmbeddedId
	public TeacherRolePK getPk() {
		return pk;
	}

	public void setPk(TeacherRolePK pk) {
		this.pk = pk;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(insertable=false, updatable=false, referencedColumnName="id", name="teacherId")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne
	@JoinColumn(insertable=false, updatable=false, referencedColumnName="id", name="projectId")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
