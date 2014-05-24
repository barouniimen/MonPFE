package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Registration;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.RegistrationQualifier;

@RegistrationQualifier
@Stateless
public class RegistrationServ implements IServiceLocal<Registration>, IServiceRemote<Registration>{
	
	@PersistenceContext
	 EntityManager em;
	
	@Override
	public void create(Object object) {
		em.persist(object);
		
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		
		Registration searchResult = new Registration();
		searchResult = null;
		
		/*************************** searchBy Student and academic year *****************************/
		if(searchBy == "ST&AC"){
			
			TypedQuery<Registration> query = em.createNamedQuery("Registration.findByAcademicYandStudentId", Registration.class);
			query.setParameter("academicYear",((Registration) object).getAcademicYear());
			query.setParameter("userId",((Registration) object).getRegistrationPK().getUserId());
			searchResult = query.getSingleResult();
		}
		
		
		return searchResult;
	}

	@Override
	public List<Registration> retrieveList(Object object, String searchBy) {
		// TODO Auto-generated method stub
		return null;
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
