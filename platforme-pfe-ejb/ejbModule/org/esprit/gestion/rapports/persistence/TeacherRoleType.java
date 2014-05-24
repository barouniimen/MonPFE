package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum TeacherRoleType {
	ENCADRANT("Encadrant"),
	RAPPORTEUR("Rapporteur"),
	PRESIDENT("Pr\u00E9sident du jury");
	
	private String description;
	
	private TeacherRoleType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
