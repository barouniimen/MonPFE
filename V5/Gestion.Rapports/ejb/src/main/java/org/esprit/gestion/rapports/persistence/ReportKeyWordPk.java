package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;
/**
 * @author Imen Barouni
 *
 */
@Embeddable
public class ReportKeyWordPk implements Serializable {

	private int reportId;
	private int keyWordId;
	private static final long serialVersionUID = 1L;

	public ReportKeyWordPk() {
		super();
	}

	public ReportKeyWordPk(int reportId, int keyWordId) {
		super();
		this.reportId = reportId;
		this.keyWordId = keyWordId;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getKeyWordId() {
		return keyWordId;
	}

	public void setKeyWordId(int keyWordId) {
		this.keyWordId = keyWordId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + keyWordId;
		result = prime * result + reportId;
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
		ReportKeyWordPk other = (ReportKeyWordPk) obj;
		if (keyWordId != other.keyWordId)
			return false;
		if (reportId != other.reportId)
			return false;
		return true;
	}
}
