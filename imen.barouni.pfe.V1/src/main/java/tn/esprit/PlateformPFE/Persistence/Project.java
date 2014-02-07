package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project implements Serializable {

	private Calendar academicYear;
	private String topic;
	private int id;
	private List<TeacherRole> teacherRoles;
	private List<Event> events;
	private List<Report> reports;
	private Company company;
	private CompanyCoach coach;
	private static final long serialVersionUID = 1L;

	public Project() {
		super();
	}

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

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	public List<TeacherRole> getTeacherRoles() {
		return teacherRoles;
	}

	public void setTeacherRoles(List<TeacherRole> teacherRoles) {
		this.teacherRoles = teacherRoles;
	}

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	@ManyToOne
	@JoinColumn(name="company")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne
	@JoinColumn(name="coach")
	public CompanyCoach getCoach() {
		return coach;
	}

	public void setCoach(CompanyCoach coach) {
		this.coach = coach;
	}

}
