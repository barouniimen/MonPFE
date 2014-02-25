package edu.esprit.pfe.store.ejb.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Student
 * 
 */
@Entity
@Table(name = "eps_student")
public class Student extends User implements Serializable {

	@OneToOne(mappedBy = "student")
	private GraduationProjectReport graduationProjectReport;
	@ManyToOne
	@JoinColumn(name = "OPT_ID_FK", referencedColumnName = "OPT_ID")
	private Option option;
	private static final long serialVersionUID = 1L;

	public Student() {
		super();
	}

	public Student(int idUser, String firstName, String lastName, String email,
			String phoneNumber, String login, String password) {
		super(firstName, lastName, email, phoneNumber, login, password);
	}

	public GraduationProjectReport getGraduationProjectReport() {
		return graduationProjectReport;
	}

	public void setGraduationProjectReport(
			GraduationProjectReport graduationProjectReport) {
		this.graduationProjectReport = graduationProjectReport;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

}
