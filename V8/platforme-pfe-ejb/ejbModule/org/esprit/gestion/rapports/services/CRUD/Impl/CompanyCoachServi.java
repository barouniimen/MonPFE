package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	@Override
	public void create(Object object) {
		try {
			em.persist(object);
			} catch (EntityExistsException exist) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!User existe!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	@Override
	public void update(Object object) {
		em.merge(object);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}


	@Override
	public List<CompanyCoach> retrieveList(Object object, String searchBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
