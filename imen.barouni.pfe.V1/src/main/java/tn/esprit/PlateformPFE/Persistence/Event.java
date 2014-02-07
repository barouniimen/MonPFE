package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;
import java.lang.String;
import java.util.Calendar;
import javax.persistence.*;


@Entity
public class Event implements Serializable {

	private int id;
	private Calendar dueDate;
	private Calendar startDate;
	private String state; // TODO Pourrait être en enumération
	private String type; // TODO Pourrait être en enumération
	private Project project;
	private static final long serialVersionUID = 1L;

	public Event() {
		super();
	}

	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}

	public Calendar getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name="project")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
