package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.StorageSpace;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.StorageSpaceQualifier;

@StorageSpaceQualifier
@Stateless
public class StorageSpaceServ implements IServiceRemote<StorageSpace>,IServiceLocal<StorageSpace>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void create(Object object) {
		try {
			em.persist(object);
		} catch (EntityExistsException exist) {
			System.out
					.println("!!!!!!!!!!!!!!!!!!!!!ID existe!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		StorageSpace returnResult;
		returnResult = null; 
		
		/*************************** if search by student **********************************/
		if(searchBy == "student"){
			TypedQuery<StorageSpace> query = em.createNamedQuery("StorageSpace.findByStudentID", StorageSpace.class);
			query.setParameter("studentid", ((StorageSpace) object).getStudent().getId());
			returnResult = query.getSingleResult();
		}
		return returnResult;
	}

	@Override
	public void update(Object object) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public void delete(Object object) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	
	@Override
	public List<StorageSpace> retrieveList(Object object, String searchBy) {
		List<StorageSpace> returnList = new ArrayList<StorageSpace>();
		
		/********************************* search all ***********************************/
		if(searchBy == "ALL"){
			TypedQuery<StorageSpace> query = em.createNamedQuery("StorageSpace.findAll", StorageSpace.class);
			returnList = query.getResultList();
		}
		
		
		
		return returnList;
	}

}
