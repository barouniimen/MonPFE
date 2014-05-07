package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProjectDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProjectDomainPK pk;
	private Project project;
	private Domain domain;
	
	
	
	
	
	public ProjectDomain() {
		super();
	}
	public ProjectDomain(ProjectDomainPK pk) {
		super();
		this.pk = pk;
	}
	
	@EmbeddedId
	public ProjectDomainPK getPk() {
		return pk;
	}
	public void setPk(ProjectDomainPK pk) {
		this.pk = pk;
	}
	
	@ManyToOne
	@JoinColumn(name = "projectId", referencedColumnName = "id", insertable = false, updatable = false)
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
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
		ProjectDomain other = (ProjectDomain) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
	
	
	

}
