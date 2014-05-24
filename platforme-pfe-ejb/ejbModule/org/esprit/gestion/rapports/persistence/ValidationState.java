package org.esprit.gestion.rapports.persistence;
/**
 * @author Imen Barouni
 *
 */
public enum ValidationState {
	WAITING("Sujet en attente de validation"),
	NONVALID("Sujet non valid\u00E9"),
	VALID("Sujet valid\u00E9"),
	WAITINGSOUT("En attente de soutenance"),
	SOUTVALID("Soutenu et valid\u00E9"),
	SOUTNONVALID("Soutenu et non valid\u00E9");
	
	private String description;
	
	private ValidationState(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
