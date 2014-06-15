package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum ReportState {
	WAITINGDEPO("Version en attente de d\u00E9p\u00F4t"),
	DEPOSED("D\u00E9pos\u00E9"),
	VALID_RAPPORTEUR("Valid\u00E9 par le rapporteur"),
	WAITINGARCHIV("En attente d'archivage"),
	ARCHIVED("Archiv\u00E9");
	
	private String description;
	
	private ReportState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
