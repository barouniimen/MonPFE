package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum ReportState {
	DEPOSED("Depose"),
	VALID_RAPPORTEUR("Valid\u00E9 par le rapporteur"),
	WAITING("En attente d'archivage"),
	ARCHIVED("Archiv\u00E9");
	
	private String description;
	
	private ReportState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
