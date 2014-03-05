package org.esprit.gestion.rapports.config;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.KeyWordCategory;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.KeyWordsQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;

/**
 * Session Bean implementation class EPSInitializerBean
 */

@Singleton
@Startup
public class Tests {

	@Inject
	@UserQualifier
	IServiceLocal<User> servUser;
	
	@Inject
	@KeyWordsQualifier
	IServiceLocal<KeyWord> servKeyW;
	KeyWord kw;
	KeyWordCategory categ;
	
	@PostConstruct
	public void createData() {
		

	}

	public Tests() {
	}
}
