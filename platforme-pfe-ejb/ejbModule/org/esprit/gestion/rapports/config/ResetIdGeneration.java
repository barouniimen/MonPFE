package org.esprit.gestion.rapports.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ResetIdGeneration {
	@PersistenceContext
	EntityManager em;
	public void reset(){
		
		
		em
	    .createNativeQuery("ALTER TABLE Message AUTO_INCREMENT = 1")
	    .executeUpdate(); 
	}
}
