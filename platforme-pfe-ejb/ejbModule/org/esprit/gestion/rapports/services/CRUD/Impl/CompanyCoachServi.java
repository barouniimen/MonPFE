package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.CompanyCoachQualifier;

@CompanyCoachQualifier
@Stateless
public class CompanyCoachServi implements IServiceLocal<CompanyCoach>, IServiceRemote<CompanyCoach>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void delete(Object object) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public void create(Object object) {
	
			em.persist(object);
			
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
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
	public List<CompanyCoach> retrieveList(Object object, String searchBy) {
		List<CompanyCoach> returnList = new ArrayList<CompanyCoach>();
		returnList = null;
		
		/*************************** search ALL *************************************/
		if (searchBy.equals("ALL")) {
			TypedQuery<CompanyCoach> query = em.createNamedQuery("CompanyCoach.findAll", CompanyCoach.class);
			returnList = query.getResultList();
		}
		
		return returnList;
	}

}
