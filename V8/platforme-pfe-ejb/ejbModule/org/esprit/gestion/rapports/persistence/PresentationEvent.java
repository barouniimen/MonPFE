package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "PresentationEvent.findAll", query = "SELECT c FROM PresentationEvent c"),
	@NamedQuery(name = "PresentationEvent.findById", query = "SELECT c FROM PresentationEvent c WHERE c.id = :id"),
	@NamedQuery(name = "PresentationEvent.findBydueDate", query = "SELECT c FROM PresentationEvent c WHERE c.dueDate = :dueDate"),
	@NamedQuery(name = "PresentationEvent.findBystate", query = "SELECT c FROM PresentationEvent c WHERE c.state = :state")})
public class PresentationEvent implements Serializable {

	private int id;
	private Calendar dueDate;
	private EventState state;
	private Project project;
	private int classrommId;
	private static final long serialVersionUID = 1L;

	
	
	public PresentationEvent(Calendar dueDate, EventState state, Project project) {
		super();
		this.dueDate = dueDate;
		this.state = state;
		this.project = project;
	}
	public PresentationEvent() {
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

	@Enumerated(EnumType.STRING)
	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	public int getClassrommId() {
		return classrommId;
	}
	public void setClassrommId(int classrommId) {
		this.classrommId = classrommId;
	}

}
