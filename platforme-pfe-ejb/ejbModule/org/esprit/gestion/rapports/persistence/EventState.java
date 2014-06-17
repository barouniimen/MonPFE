package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum EventState {
	PLANIFIED("Planifi\u00E9"),
	CONFIRMED("Confirm\u00E9"),
	COMMUNICATED("Communiqu\u00E9"),
	STARTED("A d\u00E9but\u00E9"),
	FINISHED("A pris fin");
	
	private String description;
	
	private EventState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
