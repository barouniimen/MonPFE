package edu.esprit.pfe.store.ejb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GraduationProjectTechnologiesPK implements Serializable {
	
	@Column(name = "REPORT_ID_PK")
	private int idReportPK;
	@Column(name = "TECHNO_ID_PK")
	private int idTechnoPK;
	private static final long serialVersionUID = 1L;

	public int getIdReportPK() {
		return idReportPK;
	}

	public void setIdReportPK(int idReportPK) {
		this.idReportPK = idReportPK;
	}

	public int getIdTechnoPK() {
		return idTechnoPK;
	}

	public void setIdTechnoPK(int idTechnoPK) {
		this.idTechnoPK = idTechnoPK;
	}

	public GraduationProjectTechnologiesPK(int idReportPK, int idTechnoPK) {
		super();
		this.idReportPK = idReportPK;
		this.idTechnoPK = idTechnoPK;
	}

	public GraduationProjectTechnologiesPK() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idReportPK;
		result = prime * result + idTechnoPK;
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
		GraduationProjectTechnologiesPK other = (GraduationProjectTechnologiesPK) obj;
		if (idReportPK != other.idReportPK)
			return false;
		if (idTechnoPK != other.idTechnoPK)
			return false;
		return true;
	}

}
