package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

@Local
public interface IStorageSpaceFacadeLocal {

	public int allocatedSpace(int idStudent);
	
	public int spaceInUse(int idStudent);
}
