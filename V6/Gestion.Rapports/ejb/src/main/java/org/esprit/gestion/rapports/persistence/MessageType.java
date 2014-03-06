package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *le MessageType sert à filtrer les message par senderType et pour détecter si réponse_requise dans quel cas
 *il faut afficher un formulaire
 */
public enum MessageType {
	ADMIN("message de l'admin"),
	STUDENT("message d'un étudiant"),
	COACH("message d'un encadrant"),
	CORRECTOR("message d'un rapporteur"),
	TEACHER("message d'un enseignant"),
	RESPONSE_REQUIRED("Reponse requise");
	
	private String description;
	
	private MessageType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
