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
		@NamedQuery(name = "Teacherrole.findByTeacherId", query = "SELECT t FROM TeacherRole t WHERE t.pk.teacherId = :teacherId"),
		@NamedQuery(name = "Teacherrole.findByRole", query = "SELECT t FROM TeacherRole t WHERE t.role = :role"),
		@NamedQuery(name = "Teacherrole.findByRoleAndTeacherId", query = "SELECT t FROM TeacherRole t WHERE t.role = :role AND t.pk.teacherId = :teacherId"),
		@NamedQuery(name = "Teacherrole.findByRoleAndProjectId", query = "SELECT t FROM TeacherRole t WHERE t.role = :role AND t.pk.projectId = :projectId"),
		@NamedQuery(name = "Teacherrole.findByTeacherIdANDProjectId", query = "SELECT t FROM TeacherRole t WHERE t.pk.teacherId = :teacherId and t.pk.projectId = :projectId")})
public class TeacherRole implements Serializable {

	private TeacherRolePK pk;
	private TeacherRoleType role;
	private Teacher teacher;
	private Project project;
	private static final long serialVersionUID = 1L;
	
	

	public TeacherRole(TeacherRolePK pk, TeacherRoleType role) {
		super();
		this.pk = pk;
		this.role = role;
	}

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

	@Enumerated(EnumType.STRING)
	public TeacherRoleType getRole() {
		return role;
	}

	public void setRole(TeacherRoleType role) {
		this.role = role;
	}

}
