package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue(value = "CompagyCoach")
public class CompanyCoach extends User implements Serializable {

	private String position;
	private List<Project> projects;
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

	@OneToMany(mappedBy="coach")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}
