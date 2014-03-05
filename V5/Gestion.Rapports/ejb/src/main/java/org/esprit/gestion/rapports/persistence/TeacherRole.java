package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
		@NamedQuery(name = "Teacherrole.findAll", query = "SELECT t FROM TeacherRole t"),
		@NamedQuery(name = "Teacherrole.findByProjectId", query = "SELECT t FROM TeacherRole t WHERE t.pk.projectId = :projectId"),
		@NamedQuery(name = "Teacherrole.findByTeacherId", query = "SELECT t FROM TeacherRole t WHERE t.pk.teacherId = :teacherId") })
public class TeacherRole implements Serializable {

	private TeacherRolePK pk;
	private TeacherRole role;
	private Teacher teacher;
	private Project project;
	private static final long serialVersionUID = 1L;

	public TeacherRole() {
		super();
	}

	@EmbeddedId
	public TeacherRolePK getPk() {
		return pk;
	}

	public void setPk(TeacherRolePK pk) {
		this.pk = pk;
	}

	@Enumerated(EnumType.STRING)
	public TeacherRole getRole() {
		return role;
	}

	public void setRole(TeacherRole role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, referencedColumnName = "id", name = "teacherId")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, referencedColumnName = "id", name = "projectId")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
