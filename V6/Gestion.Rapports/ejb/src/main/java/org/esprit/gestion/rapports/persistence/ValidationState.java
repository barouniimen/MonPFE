package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum ValidationState {
	VALID("Valide"),
	NONVALID("Non valide"),
	WAITING("En attente de validation");
	
	private String description;
	
	private ValidationState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
