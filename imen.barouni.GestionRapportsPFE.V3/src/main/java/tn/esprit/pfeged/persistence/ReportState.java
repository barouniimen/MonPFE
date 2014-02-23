package tn.esprit.pfeged.persistence;

public enum ReportState {
	VALID_ENCADRANT("Valid� par l'encadrant"),
	VALID_RAPPORTEUR("Valid� par le rapporteur"),
	DEPOSED("D�pos�"),
	WAITING("En attente d'archivage"),
	ARCHIVED("Archiv�");
	
	private String description;
	
	private ReportState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
