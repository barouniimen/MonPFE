package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.KeyWordCategory;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.KWcategoryQualifier;

@KWcategoryQualifier
@Stateless
public class KeyWordsCategoryServ implements IServiceLocal<KeyWordCategory>,
		IServiceRemote<KeyWordCategory> {

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
	public List<KeyWordCategory> retrieveList(Object object, String searchBy) {
		List<KeyWordCategory> returnList = new ArrayList<KeyWordCategory>();
		returnList = null;
		/************************* searcheBy = ALL ******************************/
		if (searchBy == "ALL") {
			TypedQuery<KeyWordCategory> query = em.createNamedQuery(
					"Keywordcategory.findAll", KeyWordCategory.class);
			returnList = query.getResultList();
		}

		return returnList;
	}

}
