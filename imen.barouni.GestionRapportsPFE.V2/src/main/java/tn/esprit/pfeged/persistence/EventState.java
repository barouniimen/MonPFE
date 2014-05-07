package tn.esprit.pfeged.persistence;

public enum EventState {
	PLANIFIED("Planifié"),
	CONFIRMED("Confirmé"),
	COMMUNICATED("Communiqué");
	
	private String description;
	
	private EventState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
