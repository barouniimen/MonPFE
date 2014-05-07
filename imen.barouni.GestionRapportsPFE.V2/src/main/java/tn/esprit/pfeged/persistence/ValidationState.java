package tn.esprit.pfeged.persistence;

public enum ValidationState {
	VALID("Valid�"),
	NONVALID("Non valid�"),
	WAITING("En attente de validation");
	
	private String description;
	
	private ValidationState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
