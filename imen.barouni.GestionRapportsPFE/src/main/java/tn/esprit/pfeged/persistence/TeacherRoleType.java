package tn.esprit.pfeged.persistence;

public enum TeacherRoleType {
	ENCADRANT("Encadrant"),
	RAPPORTEUR("Rapporteur"),
	PRESIDENT("Président du jury");
	
	private String description;
	
	private TeacherRoleType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
