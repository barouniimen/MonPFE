package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
	@NamedQuery(name = "SubmissionEvent.findByState", query = "SELECT e FROM SubmissionEvent e WHERE e.state = :state"),
	@NamedQuery(name = "SubmissionEvent.findBySpeciality", query = "SELECT e FROM SubmissionEvent e WHERE e.speciality = :speciality")})
public class SubmissionEvent implements Serializable {

	private int id;
	private Speciality speciality;
	private Date dueDate;
	private Date startDate;
	private EventState state;
	private static final long serialVersionUID = 1L;
	
	
	
	public SubmissionEvent(Speciality speciality, Date dueDate,
			Date startDate, EventState state) {
		super();
		this.speciality = speciality;
		this.dueDate = dueDate;
		this.startDate = startDate;
		this.state = state;
	}

	public SubmissionEvent() {
		super();
	}

	@OneToOne
	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
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
}
