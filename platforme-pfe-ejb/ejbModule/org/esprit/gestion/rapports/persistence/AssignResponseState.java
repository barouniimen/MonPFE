package org.esprit.gestion.rapports.persistence;

public enum AssignResponseState {
	WAITING("En attente de r\u00E9ponse"),
	ACCEPTED("Affectation acc\u00E9pt\u00E9e"),
	CANCELED("Affectation annul\u00E9e"),
	REFUSED("Affectation refus\u00E9e");
	
	private String description;
	
	private AssignResponseState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	
}
