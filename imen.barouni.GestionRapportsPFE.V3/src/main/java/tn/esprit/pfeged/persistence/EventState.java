package tn.esprit.pfeged.persistence;

public enum EventState {
	PLANIFIED("Planifi�"),
	CONFIRMED("Confirm�"),
	COMMUNICATED("Communiqu�");
	
	private String description;
	
	private EventState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
