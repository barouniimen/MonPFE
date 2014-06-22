package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

@Remote
public interface IStorageSpaceFacadeRemote {

	public int allocatedSpace(int idStudent);

	public int spaceInUse(int idStudent);

}
