package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *le MessageType sert √† filtrer les message par senderType et pour d√©tecter si r√©ponse_requise dans quel cas
 *il faut afficher un formulaire
 */
public enum MessageType {
	COACHASSIGN("affecter un encadrant \u00E0† un projet, r\u00E9ponse requise"),
	CORRECTORASSIGN("affecter un rapporteur \u00E0† un projet, r\u00E9ponse requise"),
	ByADMIN("message de l'admin"),
	BySTUDENT("message d'un \u00E9tudiant"),
	ByCOACH("message d'un encadrant"),
	ByCORRECTOR("message d'un rapporteur"),
	ByTEACHER("message d'un enseignant"),
	ByVISITOR("envoy\u00E9 par un visitor non enregistr\u00E9 \u00E0†† la BD"),
	PJURYASSIGN("affecter un pr\u00E9sident de jury au projet"),
	SUBMIT_EVENT_NOTIF("Ouverture d'une session de d\u00E9p\u00F4t");
	
	private String description;
	
	private MessageType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
