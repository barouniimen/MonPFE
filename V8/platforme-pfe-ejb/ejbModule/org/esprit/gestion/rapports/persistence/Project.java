package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Date;
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
	private Date startDate;
	private String academicYear;
	private String topic;
	private ValidationState validationState;
	private List<TeacherRole> teacherRoles;
	private PresentationEvent events;
	private List<Report> reports;
	private CompanyCoach companycoach;
	private Student student;
	private List<ProjectDomain> projectDomains;
	private static final long serialVersionUID = 1L;

	

	public Project(Date startDate, String academicYear, String topic,
			ValidationState validationState, CompanyCoach companycoach,
			Student student) {
		super();
		this.startDate = startDate;
		this.academicYear = academicYear;
		this.topic = topic;
		this.validationState = validationState;
		this.companycoach = companycoach;
		this.student = student;
	}

	public Project(String academicYear, String topic) {
		super();
		this.setAcademicYear(academicYear);
		this.topic = topic;

	}

	public Project() {
		super();
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

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "companycoach")
	public CompanyCoach getCompanycoach() {
		return companycoach;
	}

	public void setCompanycoach(CompanyCoach coach) {
		this.companycoach = coach;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	@OneToMany(mappedBy = "project")
	public List<ProjectDomain> getProjectDomains() {
		return projectDomains;
	}

	public void setProjectDomains(List<ProjectDomain> projectDomains) {
		this.projectDomains = projectDomains;
	}

	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	


}
