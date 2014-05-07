package tn.esprit.pfeged.persistence;

public enum TeacherRoleType {
	ENCADRANT("Encadrant"),
	RAPPORTEUR("Rapporteur"),
	PRESIDENT("Pr�sident du jury");
	
	private String description;
	
	private TeacherRoleType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
