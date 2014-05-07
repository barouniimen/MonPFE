package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;

@DomainQualifier
@Stateless
public class DomainServ implements IServiceLocal<Domain>, IServiceRemote<Domain>{

	@PersistenceContext
	EntityManager em;

	
	
	@Override
	public void create(Object object) {
	 em.persist(object);
		
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		Domain domainFound = null;
		
		/*********************** Search by ID ***************************/
		if (searchBy == "ID") {
			TypedQuery<Domain> query = em.createNamedQuery(
					"Domain.findById", Domain.class);
			query.setParameter("id", ((Domain) object).getId());
			domainFound = query.getSingleResult();
	}
		/***********************   search by NAME ****************************************/
		else if (searchBy == "NAME"){
			TypedQuery<Domain> query = em.createNamedQuery(
					"Domain.findByName", Domain.class);
			query.setParameter("domainName", ((Domain) object).getDomainName());
			domainFound = query.getSingleResult();
			
		}

		
		return domainFound;
	}

	@Override
	public List<Domain> retrieveList(Object object, String searchBy) {
		List<Domain> returnList = new ArrayList<Domain>();
		returnList = null;
		
		/*********************** Search ALL ***************************/
		if (searchBy == "ALL") {
			TypedQuery<Domain> query = em.createNamedQuery(
					"Domain.findAll", Domain.class);
			returnList = query.getResultList();
	}
		return returnList;
	}

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub
		
	}

}
