package edu.esprit.pfe.store.ejb.services;

import java.util.List;
import javax.ejb.Remote;
import edu.esprit.pfe.store.ejb.domain.User;

@Remote
public interface AuthenticationServiceRemote {
	
	void createUser(User user);
	List<User> findAllUsers();
	User authenticate(String login, String password);
}
