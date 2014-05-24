package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProjectDomainPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int projId;
	private int domainId;
	
	public ProjectDomainPK(int projId, int domainId) {
		super();
		this.projId = projId;
		this.domainId = domainId;
	}

	public ProjectDomainPK() {
		super();
	}

	public int getProjId() {
		return projId;
	}

	public void setProjId(int projId) {
		this.projId = projId;
	}

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + domainId;
		result = prime * result + projId;
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
		ProjectDomainPK other = (ProjectDomainPK) obj;
		if (domainId != other.domainId)
			return false;
		if (projId != other.projId)
			return false;
		return true;
	}
	
	
}
