package edu.esprit.pfe.store.ejb.config;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import edu.esprit.pfe.store.ejb.domain.Option;
import edu.esprit.pfe.store.ejb.domain.SysAdmin;
import edu.esprit.pfe.store.ejb.services.AuthenticationServiceLocal;
import edu.esprit.pfe.store.ejb.services.OptionsServiceLocal;

/**
 * Session Bean implementation class EPSInitializerBean
 */
@Singleton
@Startup
public class EPSInitializerBean {

	@EJB
	private AuthenticationServiceLocal authentication;
	
	@EJB
	private OptionsServiceLocal optionsService;

	@PostConstruct
	public void createData() {
		if (!authentication.loginExists("admin")) {
			authentication.createUser(new SysAdmin("Ben Salah", "Ali",
					"ali.bensalah@esprit.tn", "(+216) 99 77 44 02", "admin",
					"adminpwd", "esprit007"));
		}
		
		if(optionsService.getAll().size()==0){
			
			optionsService.createOption(new Option("INFORMATIQUE"));
			optionsService.createOption(new Option("TELECOM"));
			optionsService.createOption(new Option("ELECTROMECATNIQUE"));
			optionsService.createOption(new Option("CIVILE"));
		}
	}

	public EPSInitializerBean() {
	}

}
