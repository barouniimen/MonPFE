package org.esprit.gestion.rapports.persistence;
/**
 * 
 * @author Imen Barouni
 *le MessageAccess sert à déterminer si le message a été lu ou pas pour savoir comment l'afficher
 *
 */
public enum MessageAccess {
	TOREAD("en attente de lecture"),
	SEEN("vu");
	
	private String description;
	
	private MessageAccess(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	
}
