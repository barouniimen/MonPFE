package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.SubmissionEvent;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.SubmissionEventQualifier;

@SubmissionEventQualifier
@Stateless
public class SubmissionEventServ implements IServiceLocal<SubmissionEvent>,
		IServiceRemote<SubmissionEvent> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void create(Object object) {
		em.persist(object);

	}

	@Override
	public Object retrieve(Object object, String searchBy) {

		SubmissionEvent submissionEvent = new SubmissionEvent();

		if (searchBy == "state") {
			try {
				TypedQuery<SubmissionEvent> query = em.createNamedQuery(
						"SubmissionEvent.findByState", SubmissionEvent.class);
				query.setParameter("state",
						((SubmissionEvent) object).getState());
				submissionEvent = query.getSingleResult();
			} catch (javax.persistence.NoResultException ex) {
				return null;
			}

		}

		return submissionEvent;
	}

	@Override
	public List<SubmissionEvent> retrieveList(Object object, String searchBy) {
		List<SubmissionEvent> returnList = new ArrayList<SubmissionEvent>();
		
		if (searchBy == "ALL"){
				TypedQuery<SubmissionEvent> query = em.createNamedQuery("SubmissionEvent.findAll", SubmissionEvent.class);
				returnList = query.getResultList();
		}
		
		
		return returnList;
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
	public void delete(Object object) {
		em.remove(object);

	}

}
