package tn.esprit.pfeged.persistence;

public enum EventType {
	DEPOSIT("D�p�t"),
	PRESENTATION("Soutenance");
	
	private String description;
	
	private EventType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}	

}
