package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum MessageState {
	READ("Lu"),
	NONREAD("Non lu"),
	RESPONSE_REQUIRED("Reponse requise");
	
	private String description;
	
	private MessageState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
