package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Speciality;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.SpecialityQualifier;

@Stateless
@SpecialityQualifier
public class SpecialityService implements IServiceLocal<Speciality>, IServiceRemote<Speciality>{

	
	@PersistenceContext
	EntityManager em;
	
	
	@Override
	public void create(Object object) {
		em.persist(object);
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		Speciality spec = null;
		
		/************************ search by Title *******************************/
		if(searchBy == "TITLE"){
			
			TypedQuery<Speciality> query = em.createNamedQuery(
					"Speciality.findByTitle", Speciality.class);
			query.setParameter("title", ((Speciality) object).getTitle());
			spec = query.getSingleResult();
		}
		
		
		return spec;
	}

	@Override
	public List<Speciality> retrieveList(Object object, String searchBy) {
		List<Speciality> returnList = new ArrayList<Speciality>();
		
		/***************************** serach all ********************************/
		if(searchBy == "ALL"){
			
			TypedQuery<Speciality> query = em.createNamedQuery(
					"Speciality.findAll", Speciality.class);
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
