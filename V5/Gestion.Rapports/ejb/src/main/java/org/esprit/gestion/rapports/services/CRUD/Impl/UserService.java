package org.esprit.gestion.rapports.services.CRUD.Impl;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
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
		try {
			em.persist(object);
		} catch (EntityExistsException exist) {
			System.out
					.println("!!!!!!!!!!!!!!!!!!!!!User existe!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
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
				try {
					userFound = query.getSingleResult();
				} catch (NoResultException nullResult) {
					System.out.println("introuvable!!!!");
				}
			} catch (NonUniqueResultException NonUnique) {
				System.out.println("non unique!!!!!!!!!!!!");
			}
		}
		/**************************** Search by ID *****************************************/
		else if (searchBy == "id") {
			TypedQuery<User> query = em.createNamedQuery("User.findById",
					User.class);
			query.setParameter("id", ((User) object).getId());
			try {
				try {
					userFound = query.getSingleResult();
				} catch (NoResultException nullResult) {
					System.out.println("introuvable!!!!");
				}
			} catch (NonUniqueResultException NonUnique) {
				System.out.println("non unique!!!!!!!!!!!!");
			}
		}
		return userFound;
	}

}
