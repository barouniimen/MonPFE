package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Appointement implements Serializable{
	
	private int id;
	private Date date;
	private User personToMeet;
	private String note;

	private static final long serialVersionUID = 1L;
	
	
	public Appointement(Date date, User personToMeet, String note) {
		super();
		this.date = date;
		this.personToMeet = personToMeet;
		this.note = note;
	}

	public Appointement() {
		super();
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne
	@JoinColumn(name = "personToMeet")
	public User getPersonToMeet() {
		return personToMeet;
	}

	public void setPersonToMeet(User personToMeet) {
		this.personToMeet = personToMeet;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
