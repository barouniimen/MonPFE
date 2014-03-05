package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "ReportKeyWord.findAll", query = "SELECT t FROM ReportKeyWord t"),
	@NamedQuery(name = "ReportKeyWord.findByreportId", query = "SELECT t FROM ReportKeyWord t WHERE t.pk.reportId = :reportId"),
	@NamedQuery(name = "ReportKeyWord.findBykeyWordId", query = "SELECT t FROM ReportKeyWord t WHERE t.pk.keyWordId = :keyWordId") })
public class ReportKeyWord implements Serializable {

	private ReportKeyWordPk pk;
	private Report report;
	private KeyWord keyWord;
	private static final long serialVersionUID = 1L;

	public ReportKeyWord() {
		super();
	}

	public ReportKeyWord(ReportKeyWordPk pk) {
		super();
		this.pk = pk;
	}

	@ManyToOne
	@JoinColumn(insertable=false, updatable=false, referencedColumnName="id", name="reportId")
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	@ManyToOne
	@JoinColumn(insertable=false, updatable=false, referencedColumnName="id", name="keyWordId")
	public KeyWord getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(KeyWord keyWord) {
		this.keyWord = keyWord;
	}

	@EmbeddedId
	public ReportKeyWordPk getPk() {
		return pk;
	}

	public void setPk(ReportKeyWordPk pk) {
		this.pk = pk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportKeyWord other = (ReportKeyWord) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
	

}
