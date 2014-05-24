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
	@NamedQuery(name = "PresentationEvent.findAll", query = "SELECT c FROM PresentationEvent c"),
	@NamedQuery(name = "PresentationEvent.findById", query = "SELECT c FROM PresentationEvent c WHERE c.id = :id"),
	@NamedQuery(name = "PresentationEvent.findBydueDate", query = "SELECT c FROM PresentationEvent c WHERE c.dueDate = :dueDate"),
	@NamedQuery(name = "PresentationEvent.findBystate", query = "SELECT c FROM PresentationEvent c WHERE c.state = :state")})
public class PresentationEvent implements Serializable {

	private int id;
	private Date dueDate;
	private EventState state;
	private Project project;
	private int classrommId;
	private static final long serialVersionUID = 1L;

	
	
	public PresentationEvent(Date dueDate, EventState state, Project project) {
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
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Enumerated(EnumType.STRING)
	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}
	
	@OneToOne
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
