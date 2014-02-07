package tn.esprit.PlateformPFE.Persistence;

public enum TeacherGrade {
	ST("Stagiaire"),
	AD("Assistant Délégué"),
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
