package tn.esprit.pfeged.persistence;

public enum ReportState {
	VALID_ENCADRANT("Validé par l'encadrant"),
	VALID_RAPPORTEUR("Validé par le rapporteur"),
	DEPOSED("Déposé"),
	WAITING("En attente d'archivage"),
	ARCHIVED("Archivé");
	
	private String description;
	
	private ReportState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
