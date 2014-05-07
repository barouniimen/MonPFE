package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.esprit.gestion.rapports.persistence.TeachingUnitDomain;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.TeachUnitDomainQualifier;

@Stateless
@TeachUnitDomainQualifier
public class TeachUnitDomainServ implements IServiceLocal<TeachingUnitDomain>, IServiceRemote<TeachingUnitDomain>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void create(Object object) {
		em.persist(object);
		
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeachingUnitDomain> retrieveList(Object object, String searchBy) {
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
