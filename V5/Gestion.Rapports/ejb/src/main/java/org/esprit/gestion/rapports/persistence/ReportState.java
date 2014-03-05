package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum ReportState {
	VALID_RAPPORTEUR("Valid� par le rapporteur"),
	DEPOSED("Depose"),
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
