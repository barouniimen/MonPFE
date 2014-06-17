package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Keyword.findAll", query = "SELECT k FROM KeyWord k"),
		@NamedQuery(name = "Keyword.findById", query = "SELECT k FROM KeyWord k WHERE k.id = :id"),
		@NamedQuery(name = "Keyword.findByName", query = "SELECT k FROM KeyWord k WHERE k.name = :name"),
		@NamedQuery(name = "Keyword.findByCategory", query = "SELECT k FROM KeyWord k WHERE k.category = :category")})
public class KeyWord implements Serializable {

	private int id;
	private String name;
	private KeyWordCategory category;
	private List<ReportKeyWord> reportKeyWords;
	private static final long serialVersionUID = 1L;

	
	
	public KeyWord(String name, KeyWordCategory category) {
		super();
		this.name = name;
		this.category = category;
	}

	public KeyWord() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "category")
	public KeyWordCategory getCategory() {
		return category;
	}

	public void setCategory(KeyWordCategory category) {
		this.category = category;
	}

	@OneToMany(mappedBy = "keyWord", cascade=CascadeType.PERSIST)
	public List<ReportKeyWord> getReportKeyWords() {
		return reportKeyWords;
	}

	public void setReportKeyWords(List<ReportKeyWord> reportKeyWords) {
		this.reportKeyWords = reportKeyWords;
	}

}
