package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class TeachingUnitDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TeachingUnitDomainPK pk;
	private TeachingUnit teachingUnit;
	private Domain domain;
	
	
	public TeachingUnitDomain(TeachingUnitDomainPK pk) {
		super();
		this.pk = pk;
		
	}
	public TeachingUnitDomain() {
		super();
			
	}
	
	@EmbeddedId
	public TeachingUnitDomainPK getPk() {
		return pk;
	}
	
	public void setPk(TeachingUnitDomainPK pk) {
		this.pk = pk;
	}
	
	@ManyToOne
	@JoinColumn(name = "teachingUnitId", referencedColumnName = "id", insertable = false, updatable = false)
	public TeachingUnit getTeachingUnit() {
		return teachingUnit;
	}
	
	public void setTeachingUnit(TeachingUnit teachingUnit) {
		this.teachingUnit = teachingUnit;
	}
	
	@ManyToOne
	@JoinColumn(name = "domainId", referencedColumnName = "id", insertable = false, updatable = false)
	public Domain getDomain() {
		return domain;
	}
	
	public void setDomain(Domain domain) {
		this.domain = domain;
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
		TeachingUnitDomain other = (TeachingUnitDomain) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
	

}
