package edu.esprit.pfe.store.ejb.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import edu.esprit.pfe.store.ejb.domain.User;

@Stateless
public class AuthenticationService implements AuthenticationServiceRemote , AuthenticationServiceLocal{

	@PersistenceContext
	private EntityManager em;
	
	public AuthenticationService() {
	}
    
	public void createUser(User user) {
		em.persist(user);
	}
	
	public User authenticate(String login, String password) {
		User found = null;
		String jpql = "select u from User u where u.login=:login and u.password=:password";
		Query query = em.createQuery(jpql);
		query.setParameter("login", login);
		query.setParameter("password", password);
		try{
			found = (User) query.getSingleResult();
		}catch(Exception ex){
		}
		return found;
	}

	public List<User> findAllUsers() {
		return em.createQuery("select u from User u").getResultList();
	}


	public boolean loginExists(String login) {
		boolean exists = false;
		String jpql = "select case when (count(u) > 0)  then true else false end from User u where u.login=:login";
		Query query = em.createQuery(jpql);
		query.setParameter("login", login);
		exists = (Boolean)query.getSingleResult();
		return exists;
	}

}
