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
	@NamedQuery(name = "SubmissionEvent.findAll", query = "SELECT e FROM SubmissionEvent e"),
	@NamedQuery(name = "SubmissionEvent.findById", query = "SELECT e FROM SubmissionEvent e WHERE e.id = :id"),
	@NamedQuery(name = "SubmissionEvent.findByDueDate", query = "SELECT e FROM SubmissionEvent e WHERE e.dueDate = :dueDate"),
	@NamedQuery(name = "SubmissionEvent.findByStartDate", query = "SELECT e FROM SubmissionEvent e WHERE e.startDate = :startDate"),
	@NamedQuery(name = "SubmissionEvent.findByState", query = "SELECT e FROM SubmissionEvent e WHERE e.state = :state")})
public class SubmissionEvent implements Serializable {

	private int id;
	private List<Project> submittedProjects;
	private Date dueDate;
	private Date startDate;
	private int minPeriodToSubmit;
	private EventState state;
	private static final long serialVersionUID = 1L;
	
	
	

	public SubmissionEvent(List<Project> submittedProjects, Date dueDate,
			Date startDate, EventState state, int minPeriodToSubmit) {
		super();
		this.submittedProjects = submittedProjects;
		this.dueDate = dueDate;
		this.startDate = startDate;
		this.state = state;
		this.minPeriodToSubmit =  minPeriodToSubmit;
	}


	public SubmissionEvent() {
		super();
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Enumerated(EnumType.STRING)
	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	@OneToMany(mappedBy = "submissionEvent")
	public List<Project> getSubmittedProjects() {
		return submittedProjects;
	}

	public void setSubmittedProjects(List<Project> submittedProjects) {
		this.submittedProjects = submittedProjects;
	}


	public int getMinPeriodToSubmit() {
		return minPeriodToSubmit;
	}


	public void setMinPeriodToSubmit(int minPeriodToSubmit) {
		this.minPeriodToSubmit = minPeriodToSubmit;
	}
}
