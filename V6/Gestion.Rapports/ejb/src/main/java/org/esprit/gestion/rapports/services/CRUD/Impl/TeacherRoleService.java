package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;

@Stateless
@TecherRoleQualifier
public class TeacherRoleService implements IServiceLocal<TeacherRole>,
		IServiceRemote<TeacherRole> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		em.remove(object);

	}

	@Override
	public void create(Object object) {
		em.persist(object);
		//TODO voir pourquoi il ne catch pas l'excpetion?	
		/*
		 * try { em.persist(object); } catch (EJBTransactionRolledbackException
		 * e) { Throwable t = e.getCause(); while ((t != null) && !(t instanceof
		 * ConstraintViolationException)) { t = t.getCause(); } if (t instanceof
		 * ConstraintViolationException) {
		 * System.out.println("existe pk!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); } }
		 */
	}

	@Override
	public Object retrieve(Object object, String searchBy) {

		TeacherRole roleFound = null;

		/****************** search by PK (object == TeacherRolePK) ***********************/
		if (searchBy == "PK") {
			TypedQuery<TeacherRole> query = em.createNamedQuery(
					"Teacherrole.findByTeacherIdANDProjectId",
					TeacherRole.class);
			query.setParameter("teacherId",
					((TeacherRolePK) object).getTeacherId());
			query.setParameter("projectId",
					((TeacherRolePK) object).getProjectId());
			try {
				roleFound = query.getSingleResult();
			} catch (NoResultException nullResult) {
				System.out.println("introuvable!!!!");
				roleFound = null;
			}
		}

		return roleFound;
	}

	@Override
	public List<TeacherRole> retrieveList(Object object, String searchBy) {
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	@Override
	public void update(Object object) {
		throw new UnsupportedOperationException("pas encore implémentée");

	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("pas encore implémentée");

	}

}
