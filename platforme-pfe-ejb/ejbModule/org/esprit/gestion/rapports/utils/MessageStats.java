package org.esprit.gestion.rapports.utils;

public class MessageStats {
	private int nbrToRead;
	private int nbrSeen;
	private int nbrSent;
	private int nbrAssignCoach;
	private int nbrAssignCorrector;
	
	
	
	
	
	public MessageStats() {
		super();
		
	}




	public MessageStats(int nbrToRead, int nbrSeen, int nbrSent,
			int nbrAssignCoach, int nbrAssignCorrector) {
		super();
		this.nbrToRead = nbrToRead;
		this.setNbrSeen(nbrSeen);
		this.nbrSent = nbrSent;
		this.nbrAssignCoach = nbrAssignCoach;
		this.nbrAssignCorrector = nbrAssignCorrector;
	}
	
	
	
	
	public int getNbrToRead() {
		return nbrToRead;
	}
	public void setNbrToRead(int nbrToRead) {
		this.nbrToRead = nbrToRead;
	}

	public int getNbrSent() {
		return nbrSent;
	}
	public void setNbrSent(int nbrSent) {
		this.nbrSent = nbrSent;
	}
	public int getNbrAssignCoach() {
		return nbrAssignCoach;
	}
	public void setNbrAssignCoach(int nbrAssignCoach) {
		this.nbrAssignCoach = nbrAssignCoach;
	}
	public int getNbrAssignCorrector() {
		return nbrAssignCorrector;
	}
	public void setNbrAssignCorrector(int nbrAssignCorrector) {
		this.nbrAssignCorrector = nbrAssignCorrector;
	}




	public int getNbrSeen() {
		return nbrSeen;
	}




	public void setNbrSeen(int nbrSeen) {
		this.nbrSeen = nbrSeen;
	}

}
