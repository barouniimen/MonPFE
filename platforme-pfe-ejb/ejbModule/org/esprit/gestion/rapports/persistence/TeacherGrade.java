package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum TeacherGrade {
	ST("Stagiaire"),
	AD("Assistant D\u00E9l\u00E9gu\u00E9"),
	AT("Assistant Technologue"),
	T("Technologue"),
	PES("Professeur de l'enseignement secondaire"),
	VAC("Vacataire");
	
	private String description;
	
	private TeacherGrade(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
