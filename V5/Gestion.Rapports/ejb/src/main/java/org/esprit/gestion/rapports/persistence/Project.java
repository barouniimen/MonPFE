package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
		@NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id"),
		@NamedQuery(name = "Project.findByAcademicYear", query = "SELECT p FROM Project p WHERE p.academicYear = :academicYear"),
		@NamedQuery(name = "Project.findByTopic", query = "SELECT p FROM Project p WHERE p.topic = :topic"),
		@NamedQuery(name = "Project.findByValidationState", query = "SELECT p FROM Project p WHERE p.validationState = :validationState") })
public class Project implements Serializable {

	private int id;
	private Calendar academicYear;
	private String topic;
	private ValidationState validationState;
	private List<TeacherRole> teacherRoles;
	private PresentationEvent events;
	private List<Report> reports;
	private CompanyCoach companycoach;
	private static final long serialVersionUID = 1L;

	public Project() {
		super();
	}

	@Temporal(TemporalType.DATE)
	public Calendar getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(Calendar academicYear) {
		this.academicYear = academicYear;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public ValidationState getValidationState() {
		return validationState;
	}

	public void setValidationState(ValidationState validationState) {
		this.validationState = validationState;
	}

	@OneToMany(mappedBy = "project")
	public List<TeacherRole> getTeacherRoles() {
		return teacherRoles;
	}

	public void setTeacherRoles(List<TeacherRole> teacherRoles) {
		this.teacherRoles = teacherRoles;
	}

	
	public PresentationEvent getEvents() {
		return events;
	}

	public void setEvents(PresentationEvent events) {
		this.events = events;
	}

	@OneToMany(mappedBy = "project")
	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "companycoach")
	public CompanyCoach getCoach() {
		return companycoach;
	}

	public void setCoach(CompanyCoach coach) {
		this.companycoach = coach;
	}

}
