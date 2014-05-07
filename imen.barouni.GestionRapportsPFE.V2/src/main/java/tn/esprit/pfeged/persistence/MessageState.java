package tn.esprit.pfeged.persistence;

public enum MessageState {
	READ("Lu"),
	NONREAD("Non lu"),
	RESPONSE_REQUIRED("Réponse requise");
	
	private String description;
	
	private MessageState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
