package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum EventState {
	PLANIFIED("Planifier"),
	CONFIRMED("Confirmer"),
	COMMUNICATED("Communiquer");
	
	private String description;
	
	private EventState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
