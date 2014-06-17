package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.KeyWordsQualifier;

@KeyWordsQualifier
@Stateless
public class KeyWordService implements IServiceLocal<KeyWord>,
		IServiceRemote<KeyWord> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		em.remove(object);
	}

	@Override
	public void create(Object object) {
		em.persist(object);
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		KeyWord kw = new KeyWord();
		
		if(searchBy == "NAME"){
			TypedQuery<KeyWord> query = em.createNamedQuery(
					"Keyword.findByName", KeyWord.class);
			query.setParameter("name",
					((KeyWord) object).getName());

			kw = query.getSingleResult();
		}
		
		return kw;
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
	public List<KeyWord> retrieveList(Object object, String searchBy) {
		List<KeyWord> returnList = new ArrayList<KeyWord>();
		returnList = null;
		/************************* searcheBy = ALL ******************************/
		if (searchBy == "Categ") {
			TypedQuery<KeyWord> query = em.createNamedQuery(
					"Keyword.findByCategory", KeyWord.class);
			query.setParameter("category", ((KeyWord) object).getCategory());
			returnList = query.getResultList();
		}

		return returnList;
	}

}
