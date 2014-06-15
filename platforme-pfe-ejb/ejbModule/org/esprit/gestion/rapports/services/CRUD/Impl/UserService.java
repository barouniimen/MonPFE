package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;

@UserQualifier
@Stateless
public class UserService implements IServiceLocal<User>, IServiceRemote<User> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void create(Object object) {

		em.persist(object);

	}

	@Override
	public void update(Object object) {
		em.merge(object);
	}

	@Override
	public void delete(int id) {
		User user = em.find(User.class, id);
		em.remove(user);
	}

	@Override
	public void delete(Object object) {
		em.remove(object);
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		User userFound = null;

		/*********************** Search by login and password ***************************/
		if (searchBy == "LP") {
			TypedQuery<User> query = em.createNamedQuery(
					"User.findByLoginAndPassword", User.class);
			query.setParameter("login", ((User) object).getLogin());
			query.setParameter("password", ((User) object).getPassword());
			try {
				userFound = query.getSingleResult();
			} catch (NoResultException nullResult) {

			} catch (NonUniqueResultException NonUnique) {

			}
		}
		/*********************** Search by first and lastName ***************************/
		else if (searchBy == "NAME") {

			try {
				TypedQuery<User> query = em.createNamedQuery(
						"User.findByFirstAndLastName", User.class);
				query.setParameter("firstName", ((User) object).getFirstName());
				query.setParameter("lastName", ((User) object).getLastName());
				userFound = query.getSingleResult();
			} catch (javax.persistence.NoResultException exeption) {

				return null;
			}
		}

		/*********************** Search by ID ***************************/
		else if (searchBy == "ID") {
			TypedQuery<User> query = em.createNamedQuery("User.findById",
					User.class);
			query.setParameter("id", ((User) object).getId());
			userFound = query.getSingleResult();
		}

		return userFound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal#
	 * retrieveList(java.lang.Object, java.lang.String)
	 */
	@Override
	public List<User> retrieveList(Object object, String searchBy) {
		/**************************** Search by ID *****************************************/
		List<User> userList = null;
		if (searchBy == "ID") {
			TypedQuery<User> query = em.createNamedQuery("User.findById",
					User.class);
			query.setParameter("id", ((User) object).getId());
			userList = query.getResultList();

		}
		return userList;
	}

}
