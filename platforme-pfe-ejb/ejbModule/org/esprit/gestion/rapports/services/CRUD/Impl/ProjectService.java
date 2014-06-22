package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;

@ProjectQualifier
@Stateless
public class ProjectService implements IServiceLocal<Project>,
		IServiceRemote<Project> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		System.out.println("delete: " + ((Project) object).getId());
		em.remove(object);
	}

	@Override
	public void create(Object object) {
		em.persist(object);
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		Project projectSearched = null;

		/*************************** search by ID *********************************************/
		if (searchBy.equals("ID")) {
			TypedQuery<Project> query = em.createNamedQuery("Project.findById",
					Project.class);
			query.setParameter("id", ((Project) object).getId());
			projectSearched = query.getSingleResult();
		}

		/******************************* By Topic ***************************************************/
		else if (searchBy.equals("TOPIC")) {
			TypedQuery<Project> query = em.createNamedQuery(
					"Project.findByTopic", Project.class);
			query.setParameter("topic", ((Project) object).getTopic());
			projectSearched = query.getSingleResult();

		}
		else if(searchBy.equals("ST")){
			TypedQuery<Project> query = em.createNamedQuery(
					"Project.findByStudent", Project.class);
			query.setParameter("student", ((Project) object).getStudent());
			projectSearched = query.getSingleResult();
		}
		
		return projectSearched;
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
	public List<Project> retrieveList(Object object, String searchBy) {
		List<Project> projectSearched = null;

		/****************************** By ValidationState *********************************************/
		if (searchBy == "VS") {
			TypedQuery<Project> query = em.createNamedQuery(
					"Project.findByValidationState", Project.class);
			query.setParameter("validationState",
					((Project) object).getValidationState());
			projectSearched = query.getResultList();

		}

		return projectSearched;
	}

}
