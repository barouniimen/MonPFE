package tn.esprit.pfeged.persistence;

import java.io.Serializable;
import java.util.Calendar;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
		@NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
		@NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
		@NamedQuery(name = "Event.findByDueDate", query = "SELECT e FROM Event e WHERE e.dueDate = :dueDate"),
		@NamedQuery(name = "Event.findByStartDate", query = "SELECT e FROM Event e WHERE e.startDate = :startDate"),
		@NamedQuery(name = "Event.findByState", query = "SELECT e FROM Event e WHERE e.state = :state"),
		@NamedQuery(name = "Event.findByType", query = "SELECT e FROM Event e WHERE e.type = :type") })
public class Event implements Serializable {

	private int id;
	private Calendar dueDate;
	private Calendar startDate;
	private EventState state;
	private EventType type;
	private Project project;
	private static final long serialVersionUID = 1L;

	public Event() {
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
	public Calendar getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@Enumerated(EnumType.STRING)
	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	@Enumerated(EnumType.STRING)
	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "project")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
