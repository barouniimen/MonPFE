package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "Domain.findAll", query = "SELECT t FROM Domain t"),
		@NamedQuery(name = "Domain.findById", query = "SELECT t FROM Domain t WHERE t.id = :id"),
		@NamedQuery(name = "Domain.findByName", query = "SELECT t FROM Domain t WHERE t.domainName = :domainName")})
public class Domain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String domainName;
	private List<ProjectDomain> projectDomains;
	private List<TeachingUnitDomain> teachingUnitDomains;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@OneToMany(mappedBy = "domain")
	public List<ProjectDomain> getProjectDomains() {
		return projectDomains;
	}

	public void setProjectDomains(List<ProjectDomain> projectDomains) {
		this.projectDomains = projectDomains;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	@OneToMany(mappedBy = "domain")
	public List<TeachingUnitDomain> getTeachingUnitDomains() {
		return teachingUnitDomains;
	}

	public void setTeachingUnitDomains(
			List<TeachingUnitDomain> teachingUnitDomains) {
		this.teachingUnitDomains = teachingUnitDomains;
	}

	@Override
	public String toString() {
		return domainName ;
	}
}
