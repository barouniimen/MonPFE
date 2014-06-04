package org.esprit.gestion.rapports.config;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;

/**
 * Session Bean implementation class EPSInitializerBean
 */

@Singleton
@Startup
public class Tests {

	@Inject
	@UserQualifier
	IServiceLocal<User> userServ;

	@PostConstruct
	public void createData() {
		User user = new User();
		user.setFirstName("admin");
		user.setLastName("admin");
		user = (User) userServ.retrieve(user, "NAME");

		if (user == null) {

			Administrator admin = new Administrator();
			admin.setFirstName("admin");
			admin.setLastName("admin");
			admin.setLogin("admin");
			admin.setPassword("admin");
			userServ.create(admin);
		}

	}

	public Tests() {
	}
}
