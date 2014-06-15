package org.esprit.gestion.rapports.services.CRUD.Impl;

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
		KeyWord kw = em.find(KeyWord.class, 20);
		if (kw != null) {
			System.out.println("found key wor!!!!!!!!!!!!!!!!!!!!!!!"
					+ kw.getId());
		} else {
			System.out.println("!!!!!!!!!!!!!!not found!!!!!!!!!!!");
		}
		return kw;
	}

	@Override
	public void update(Object object) {
		em.merge(object);

	}

	@Override
	public void delete(int id) {
		System.out.println("delete begin!!!!!!!!!!!!!!!!");
		KeyWord kw = null;
		TypedQuery<KeyWord> query = em.createNamedQuery("Keyword.findById",
				KeyWord.class);
		query.setParameter("id", id);
		try {
			kw = query.getSingleResult();
		} catch (Exception ex) {
		}
		em.remove(kw);
		System.out.println("delete end!!!!!!!!!!!!!!!!");

	}

	/* (non-Javadoc)
	 * @see org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal#retrieveList(java.lang.Object, java.lang.String)
	 */
	@Override
	public List<KeyWord> retrieveList(Object object, String searchBy) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

}
