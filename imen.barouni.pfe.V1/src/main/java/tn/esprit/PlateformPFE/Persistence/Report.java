package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Report implements Serializable {

	private int id;
	private Calendar officialSubmissionDate;
	private String state; // TODO pourrait être une enumération
	private Calendar uploadDate;
	private int version;
	private Project project;
	private List<ReportKeyWord> keyWords;
	private static final long serialVersionUID = 1L;

	public Report() {
		super();
	}

	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getOfficialSubmissionDate() {
		return this.officialSubmissionDate;
	}

	public void setOfficialSubmissionDate(Calendar officialSubmissionDate) {
		this.officialSubmissionDate = officialSubmissionDate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Calendar getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Calendar uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne
	@JoinColumn(name="project")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@OneToMany(mappedBy="report",cascade=CascadeType.ALL)
	public List<ReportKeyWord> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<ReportKeyWord> keyWords) {
		this.keyWords = keyWords;
	}

}
