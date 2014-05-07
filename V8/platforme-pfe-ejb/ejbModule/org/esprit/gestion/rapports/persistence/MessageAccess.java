package org.esprit.gestion.rapports.persistence;
/**
 * 
 * @author Imen Barouni
 *le MessageAccess sert à déterminer si le message a été lu ou pas pour savoir comment l'afficher
 *
 */
public enum MessageAccess {
	TOREAD("boite de réception, non lu"),
	SENT("boite d'envoi"),
	SEEN("boite de réception, lu");
	
	private String description;
	
	private MessageAccess(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	
}
