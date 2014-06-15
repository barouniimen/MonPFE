package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.TeachingUnitQualifier;

@TeachingUnitQualifier
@Stateless
public class TeachingUnitService implements IServiceLocal<TeachingUnit>,
		IServiceRemote<TeachingUnit> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public void create(Object object) {
		try {
			em.persist(object);
		} catch (EntityExistsException exist) {
			
		}
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		/************* search by NAME *****************/
		TeachingUnit tu = null;
		
		if (searchBy == "NAME") {
		TypedQuery<TeachingUnit> query = em.createNamedQuery(
				"Teachingunit.findByName", TeachingUnit.class);
		query.setParameter("name", ((TeachingUnit) object).getName());
		tu = query.getSingleResult();
		}
		return tu;
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
	public List<TeachingUnit> retrieveList(Object object, String searchBy) {
		List<TeachingUnit> returnList = new ArrayList<TeachingUnit>();
		/************************** searchBy = all *************************/
		if(searchBy=="ALL"){
			TypedQuery<TeachingUnit> query = em.createNamedQuery("Teachingunit.findAll", TeachingUnit.class);
			returnList = query.getResultList();
		}
		
		return returnList;
	}

}
