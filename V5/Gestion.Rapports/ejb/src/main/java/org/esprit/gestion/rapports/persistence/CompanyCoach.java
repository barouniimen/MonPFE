package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
/**
 * @author Imen Barouni
 *
 */
@Entity
@DiscriminatorValue(value = "CompanyCoach")
@NamedQueries({
		@NamedQuery(name = "CompanyCoach.findAll", query = "SELECT c FROM CompanyCoach c"),
		@NamedQuery(name = "CompanyCoach.findById", query = "SELECT c FROM CompanyCoach c WHERE c.id = :id"),
		@NamedQuery(name = "CompanyCoach.findByEmail", query = "SELECT c FROM CompanyCoach c WHERE c.email = :email"),
		@NamedQuery(name = "CompanyCoach.findByFirstName", query = "SELECT c FROM CompanyCoach c WHERE c.firstName = :firstName"),
		@NamedQuery(name = "CompanyCoach.findByLastName", query = "SELECT c FROM CompanyCoach c WHERE c.lastName = :lastName"),
		@NamedQuery(name = "CompanyCoach.findByLogin", query = "SELECT c FROM CompanyCoach c WHERE c.login = :login"),
		@NamedQuery(name = "CompanyCoach.findByPassword", query = "SELECT c FROM CompanyCoach c WHERE c.password = :password"),
		@NamedQuery(name = "CompanyCoach.findByPhoneNumber", query = "SELECT c FROM CompanyCoach c WHERE c.phoneNumber = :phoneNumber"),
		@NamedQuery(name = "CompanyCoach.findByAdress", query = "SELECT c FROM CompanyCoach c WHERE c.company.adress = :adress"),
		@NamedQuery(name = "CompanyCoach.findByName", query = "SELECT c FROM CompanyCoach c WHERE c.company.name = :name"),
		@NamedQuery(name = "CompanyCoach.findByPosition", query = "SELECT c FROM CompanyCoach c WHERE c.position = :position") })
public class CompanyCoach extends User implements Serializable {

	private String position;
	private List<Project> projects;
	private Company company;
	private static final long serialVersionUID = 1L;

	public CompanyCoach() {
		super();
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@OneToMany(mappedBy = "coach")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Embedded
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
