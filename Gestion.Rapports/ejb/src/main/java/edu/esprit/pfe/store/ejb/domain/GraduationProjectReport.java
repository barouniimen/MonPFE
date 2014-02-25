package edu.esprit.pfe.store.ejb.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: GraduationProjectReport
 * 
 */
@Entity
@Table(name = "eps_gpr")
public class GraduationProjectReport implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GPR_ID")
	private int idGPR;
	@Column(name = "GPR_TITLE")
	private String title;
	@Column(name = "GPR_DESC")
	private String description;
	@Column(name = "GPR_YEAR")
	private int graduationYear;
	@Column(name = "GPR_ENTREPRISE")
	private String entreprise;
	@Column(name = "GPR_DOWNLOADS")
	private int downloads;
	@Lob
	@Column(name = "GPR_CONTENT")
	private byte[] content;
	@OneToOne
	@JoinColumn(name = "STUDENT_ID_FK", referencedColumnName = "USR_ID")
	private Student student;
	@OneToMany(mappedBy = "report")
	private List<GraduationProjectTechnologies> graduationProjectTechnologies = new ArrayList<GraduationProjectTechnologies>();

	private static final long serialVersionUID = 1L;

	public GraduationProjectReport() {
		super();
	}

	public int getIdGPR() {
		return this.idGPR;
	}

	public void setIdGPR(int idGPR) {
		this.idGPR = idGPR;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getGraduationYear() {
		return this.graduationYear;
	}

	public void setGraduationYear(int graduationYear) {
		this.graduationYear = graduationYear;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<GraduationProjectTechnologies> getGraduationProjectTechnologies() {
		return graduationProjectTechnologies;
	}

	public void setGraduationProjectTechnologies(
			List<GraduationProjectTechnologies> graduationProjectTechnologies) {
		this.graduationProjectTechnologies = graduationProjectTechnologies;
	}

	public String getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}

	public int getDownloads() {
		return downloads;
	}

	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
