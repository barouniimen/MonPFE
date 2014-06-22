package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Comments;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.CommentQualifier;

@CommentQualifier
@Stateless
public class CommentServ implements IServiceLocal<Comments>,
		IServiceRemote<Comments> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void create(Object object) {
		em.persist(object);

	}

	@Override
	public Object retrieve(Object object, String searchBy) {

		return null;
	}

	@Override
	public List<Comments> retrieveList(Object object, String searchBy) {
		List<Comments> list = new ArrayList<Comments>();

		if (searchBy.equals("report")) {
			TypedQuery<Comments> query = em.createNamedQuery(
					"Comments.findByReport", Comments.class);
			query.setParameter("report",
					((Comments) object).getReport());
			list = query.getResultList();
		}

		return list;
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
		try{
			em.remove(object);
		}
		catch(Exception e){
			System.out.println("not deleted!!!!!!!!!!!!!");
		}

	}

}
