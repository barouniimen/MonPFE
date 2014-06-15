package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Imen Barouni
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
		@NamedQuery(name = "Report.findById", query = "SELECT r FROM Report r WHERE r.id = :id"),
		@NamedQuery(name = "Report.findByCorrectorValidation", query = "SELECT r FROM Report r WHERE r.correctorValidation = :correctorValidation"),
		@NamedQuery(name = "Report.findBySize", query = "SELECT r FROM Report r WHERE r.size = :size"),
		@NamedQuery(name = "Report.findByState", query = "SELECT r FROM Report r WHERE r.state = :state"),
		@NamedQuery(name = "Report.findByUploadDate", query = "SELECT r FROM Report r WHERE r.uploadDate = :uploadDate"),
		@NamedQuery(name = "Report.findByVersion", query = "SELECT r FROM Report r WHERE r.version = :version") })
public class Report implements Serializable {

	private int id;
	private boolean correctorValidation;
	private Long size;
	private ReportState state;
	private Date uploadDate;
	private String version;
	private Project project;
	private List<ReportKeyWord> keyWords;
	private List<Comments> comments;
	private String filePath;
	private static final long serialVersionUID = 1L;
	private String fileName;

	

	public Report(boolean correctorValidation,
			Long size, ReportState state, Date uploadDate, String version,
			Project project, List<ReportKeyWord> keyWords,
			List<Comments> comments, String filePath, String fileName) {
		super();
		this.correctorValidation = correctorValidation;
		this.size = size;
		this.state = state;
		this.uploadDate = uploadDate;
		this.version = version;
		this.project = project;
		this.keyWords = keyWords;
		this.comments = comments;
		this.filePath = filePath;
		this.fileName = fileName;
	}

	public Report() {
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
	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public boolean isCorrectorValidation() {
		return correctorValidation;
	}

	public void setCorrectorValidation(boolean correctorValidation) {
		this.correctorValidation = correctorValidation;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Enumerated(EnumType.STRING)
	public ReportState getState() {
		return state;
	}

	public void setState(ReportState state) {
		this.state = state;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne
	@JoinColumn(name = "project")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@OneToMany(mappedBy = "report", cascade = CascadeType.PERSIST)
	public List<ReportKeyWord> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<ReportKeyWord> keyWords) {
		this.keyWords = keyWords;
	}

	@OneToMany(mappedBy = "report")
	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
