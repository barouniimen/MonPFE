package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;

@StudentQualifier
@Stateless
public class StudentService implements IServiceLocal<Student>,
		IServiceRemote<Student> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		em.remove(object);
	}

	@Override
	public void create(Object object) {
		em.persist(object);
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		Student studentSearched = null;

		/*************************** search by ID *********************************************/
		if (searchBy.equals("ID")) {
			TypedQuery<Student> query = em.createNamedQuery("Student.findById",
					Student.class);
			query.setParameter("id", ((Student) object).getId());
			studentSearched = query.getSingleResult();
		}

		/*********************** search by login & password **************************/
		else if (searchBy.equals("LP")) {
			TypedQuery<Student> query = em.createNamedQuery(
					"Student.findByLoginAndPassword", Student.class);
			query.setParameter("login", ((Student) object).getLogin());
			query.setParameter("password", ((Student) object).getPassword());
			studentSearched = query.getSingleResult();
		}

		return studentSearched;
	}

	@Override
	public void update(Object object) {
		em.merge(object);
	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public List<Student> retrieveList(Object object, String searchBy) {

		List<Student> returnList = new ArrayList<Student>();
		returnList = null;

		/****************************** find All ***************************************/
		if (searchBy.equals("ALL")) {
			TypedQuery<Student> query = em.createNamedQuery("Student.findAll",
					Student.class);
			returnList = query.getResultList();
		} else if (searchBy == "ProjNotNull") {
			TypedQuery<Student> query = em.createNamedQuery(
					"Student.findByProjectExist", Student.class);
			returnList = query.getResultList();

		} else if (searchBy == "projNull") {
			TypedQuery<Student> query = em.createNamedQuery(
					"Student.findByProjectNull", Student.class);
			returnList = query.getResultList();

		}

		return returnList;
	}

}
