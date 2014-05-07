package org.esprit.gestion.rapports.services.facades.Impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IUserFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IUserFacadeRemote;


@Stateless
public class UserFacade implements IUserFacadeLocal, IUserFacadeRemote{

	@Inject
	@UserQualifier
	IServiceLocal<User> userServ;
	
	@Override
	public User findAdmin() {
		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("admin");
		admin = (User) userServ.retrieve(admin, "LP");
		return admin;
	}
	
	

}
