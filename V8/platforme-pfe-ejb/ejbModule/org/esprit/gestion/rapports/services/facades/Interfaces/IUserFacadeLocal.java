package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.User;

@Local
public interface IUserFacadeLocal {
	
	public User findAdmin();

}
