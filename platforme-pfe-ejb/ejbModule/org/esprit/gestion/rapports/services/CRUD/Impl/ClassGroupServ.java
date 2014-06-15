package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.ClassGroupeQualifier;

@ClassGroupeQualifier
@Stateless
public class ClassGroupServ implements IServiceLocal<ClassGroup>,
		IServiceRemote<ClassGroup> {

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
		ClassGroup classgpe = null;

		/****************** search by reference *******************/
		if (searchBy == "REF") {
			TypedQuery<ClassGroup> query = em.createNamedQuery(
					"Classgroup.findByReference", ClassGroup.class);
			query.setParameter("reference",
					((ClassGroup) object).getReference());

			classgpe = query.getSingleResult();
		} else if (searchBy == "ID") {
			TypedQuery<ClassGroup> query = em.createNamedQuery(
					"Classgroup.findById", ClassGroup.class);
			query.setParameter("id", ((ClassGroup) object).getId());

			classgpe = query.getSingleResult();
		}
		return classgpe;
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
	public List<ClassGroup> retrieveList(Object object, String searchBy) {
		List<ClassGroup> returnList = new ArrayList<ClassGroup>();
		returnList = null;
		/************************* searcheBy = ALL ******************************/
		if(searchBy == "ALL"){
			TypedQuery<ClassGroup> query = em.createNamedQuery(
					"Classgroup.findAll", ClassGroup.class);
			returnList = query.getResultList();}
			
	
		
		
		return returnList;
	}
}
