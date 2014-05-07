package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class TeachingUnitDomainPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int teachingUnitId;
	private int domainId;
	
	
	
	public TeachingUnitDomainPK() {
		super();
	}
	
	public TeachingUnitDomainPK(int teachingUnitId, int domainId) {
		super();
		this.teachingUnitId = teachingUnitId;
		this.domainId = domainId;
	}
	
	public int getTeachingUnitId() {
		return teachingUnitId;
	}
	
	public void setTeachingUnitId(int teachingUnitId) {
		this.teachingUnitId = teachingUnitId;
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
		result = prime * result + teachingUnitId;
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
		TeachingUnitDomainPK other = (TeachingUnitDomainPK) obj;
		if (domainId != other.domainId)
			return false;
		if (teachingUnitId != other.teachingUnitId)
			return false;
		return true;
	}
	
	
	
	
}
