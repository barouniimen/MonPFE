package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Comments.findAll", query = "SELECT c FROM Comments c"),
	@NamedQuery(name = "Comments.findById", query = "SELECT c FROM Comments c WHERE c.id = :id"),
	@NamedQuery(name = "Comments.findBydate", query = "SELECT c FROM Comments c WHERE c.date = :date"),
	@NamedQuery(name = "Comments.findByReport", query = "SELECT c FROM Comments c WHERE c.report = :report"),
	@NamedQuery(name = "Comments.findByeditorName", query = "SELECT c FROM Comments c WHERE c.editorName = :editorName")})
public class Comments implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Date date;
	private String content;
	private String editorName;
	private Report report;
	
	
	
	public Comments(Date date, String content, String editorName,
			Report report) {
		super();
		this.date = date;
		this.content = content;
		this.editorName = editorName;
		this.report = report;
	}

	public Comments(){
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Lob
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEditorName() {
		return editorName;
	}
	public void setEditorName(String editor) {
		this.editorName = editor;
	}

	@ManyToOne
	@JoinColumn(name = "report")
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
}
