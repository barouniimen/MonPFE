package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.User;

@Remote
public interface IUserFacadeRemote {

	public User findAdmin();
}
