package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
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
		@NamedQuery(name = "Teachingunit.findAll", query = "SELECT t FROM TeachingUnit t"),
		@NamedQuery(name = "Teachingunit.findById", query = "SELECT t FROM TeachingUnit t WHERE t.id = :id"),
		@NamedQuery(name = "Teachingunit.findByLocalization", query = "SELECT t FROM TeachingUnit t WHERE t.localization = :localization"),
		@NamedQuery(name = "Teachingunit.findByName", query = "SELECT t FROM TeachingUnit t WHERE t.name = :name") })
public class TeachingUnit implements Serializable {

	private int id;
	private String name;
	private List<TeachingUnitDomain> teachingUnitDomains;
	private String localization;
	private List<Teacher> teachers;
	private static final long serialVersionUID = 1L;

	
	
	public TeachingUnit(String name, String localization) {
		super();
		this.name = name;
		this.localization = localization;
	}

	public TeachingUnit() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalization() {
		return this.localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	@OneToMany(mappedBy = "teachingUnit")
	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	
	@OneToMany(mappedBy = "teachingUnit")
	public List<TeachingUnitDomain> getTeachingUnitDomains() {
		return teachingUnitDomains;
	}

	public void setTeachingUnitDomains(List<TeachingUnitDomain> teachingUnitDomains) {
		this.teachingUnitDomains = teachingUnitDomains;
	}

	


	
}
