package org.esprit.gestion.rapports.services.CRUD.Impl;

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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal#
	 * retrieveList(java.lang.Object, java.lang.String)
	 */
	@Override
	public List<TeachingUnit> retrieveList(Object object, String searchBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
