package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	private PresentationEvent presentationEvent;
	private SubmissionEvent submissionEvent;
	private List<Report> reports;
	private CompanyCoach companycoach;
	private Student student;
	private List<ProjectDomain> projectDomains;
	private String fonctionnalitites;
	private static final long serialVersionUID = 1L;

	

	public Project(Date startDate, String academicYear, String topic,
			ValidationState validationState, CompanyCoach companycoach,
			Student student, List<ProjectDomain> projectDomains,
			String fonctionnalitites) {
		super();
		this.startDate = startDate;
		this.academicYear = academicYear;
		this.topic = topic;
		this.validationState = validationState;
		this.companycoach = companycoach;
		this.student = student;
		this.projectDomains = projectDomains;
		this.fonctionnalitites = fonctionnalitites;
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

	@OneToMany(mappedBy = "project")
	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	@ManyToOne
	@JoinColumn(name = "companycoach")
	public CompanyCoach getCompanycoach() {
		return companycoach;
	}

	public void setCompanycoach(CompanyCoach coach) {
		this.companycoach = coach;
	}

	@OneToOne
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

	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@OneToOne
	public PresentationEvent getPresentationEvent() {
		return presentationEvent;
	}

	public void setPresentationEvent(PresentationEvent presentationEvent) {
		this.presentationEvent = presentationEvent;
	}

	@ManyToOne
	@JoinColumn(name = "submissionEvent")
	public SubmissionEvent getSubmissionEvent() {
		return submissionEvent;
	}

	public void setSubmissionEvent(SubmissionEvent submissionEvent) {
		this.submissionEvent = submissionEvent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((academicYear == null) ? 0 : academicYear.hashCode());
		result = prime * result
				+ ((companycoach == null) ? 0 : companycoach.hashCode());
		result = prime * result + id;
		result = prime
				* result
				+ ((presentationEvent == null) ? 0 : presentationEvent
						.hashCode());
		result = prime * result
				+ ((projectDomains == null) ? 0 : projectDomains.hashCode());
		result = prime * result + ((reports == null) ? 0 : reports.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		result = prime * result
				+ ((submissionEvent == null) ? 0 : submissionEvent.hashCode());
		result = prime * result
				+ ((teacherRoles == null) ? 0 : teacherRoles.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		result = prime * result
				+ ((validationState == null) ? 0 : validationState.hashCode());
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
		Project other = (Project) obj;
		if (academicYear == null) {
			if (other.academicYear != null)
				return false;
		} else if (!academicYear.equals(other.academicYear))
			return false;
		if (companycoach == null) {
			if (other.companycoach != null)
				return false;
		} else if (!companycoach.equals(other.companycoach))
			return false;
		if (id != other.id)
			return false;
		if (presentationEvent == null) {
			if (other.presentationEvent != null)
				return false;
		} else if (!presentationEvent.equals(other.presentationEvent))
			return false;
		if (projectDomains == null) {
			if (other.projectDomains != null)
				return false;
		} else if (!projectDomains.equals(other.projectDomains))
			return false;
		if (reports == null) {
			if (other.reports != null)
				return false;
		} else if (!reports.equals(other.reports))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		if (submissionEvent == null) {
			if (other.submissionEvent != null)
				return false;
		} else if (!submissionEvent.equals(other.submissionEvent))
			return false;
		if (teacherRoles == null) {
			if (other.teacherRoles != null)
				return false;
		} else if (!teacherRoles.equals(other.teacherRoles))
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		if (validationState != other.validationState)
			return false;
		return true;
	}

	@Lob
	public String getFonctionnalitites() {
		return fonctionnalitites;
	}

	public void setFonctionnalitites(String fonctionnalitites) {
		this.fonctionnalitites = fonctionnalitites;
	}

}
