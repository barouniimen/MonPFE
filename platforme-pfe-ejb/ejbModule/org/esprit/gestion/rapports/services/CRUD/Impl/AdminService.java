package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.AdminQualifier;

@Stateless
@AdminQualifier
public class AdminService implements IServiceLocal<Administrator>, IServiceRemote<Administrator>{

	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public void create(Object object) {
		em.persist(object);
		
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public List<Administrator> retrieveList(Object object, String searchBy) {
		List<Administrator> returnList = new ArrayList<Administrator>();
		if(searchBy=="ALL"){
			
			TypedQuery<Administrator> query = em.createNamedQuery(
					"Administrator.findAll", Administrator.class);
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
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
		
	}

}
