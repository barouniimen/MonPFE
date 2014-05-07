package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *le MessageType sert à filtrer les message par senderType et pour détecter si réponse_requise dans quel cas
 *il faut afficher un formulaire
 */
public enum MessageType {
	COACHASSIGN("affecter un encadrant à un projet, réponse requise"),
	CORRECTORASSIGN("affecter un rapporteur à un projet, réponse requise"),
	ByADMIN("message de l'admin"),
	BySTUDENT("message d'un étudiant"),
	ByCOACH("message d'un encadrant"),
	ByCORRECTOR("message d'un rapporteur"),
	ByTEACHER("message d'un enseignant"),
	ByVISITOR("envoyé par un visitor non enregistré à la BD"),
	PJURYASSIGN("affecter un président de jury au projet");
	
	private String description;
	
	private MessageType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
