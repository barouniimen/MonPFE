package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum ReportState {
	DEPOSED("Depose"),
	VALID_RAPPORTEUR("Valide par le rapporteur"),
	WAITING("En attente d'archivage"),
	ARCHIVED("Archive");
	
	private String description;
	
	private ReportState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
