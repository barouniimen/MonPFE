package edu.esprit.pfe.store.ejb.services;

import java.util.List;
import javax.ejb.Local;
import edu.esprit.pfe.store.ejb.domain.User;

@Local
public interface AuthenticationServiceLocal {
	
	void createUser(User user);
	List<User> findAllUsers();
	User authenticate(String login, String password);
	boolean loginExists(String login);
}
