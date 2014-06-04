package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;

@MessagesQualifier
@Stateless
public class MessagesServ implements IServiceLocal<Message>, IServiceRemote<Message>{

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
		Message msgSearched = null;
		
		/***************************    search by ID    *********************************************/	
		if(searchBy == "ID"){
			TypedQuery<Message> query = em.createNamedQuery("Message.findById", Message.class);
			query.setParameter("id",((Message) object).getId());
			msgSearched = query.getSingleResult();
		}
		/***************************    search by Content   ***************************************/
		else if (searchBy == "CONTENT"){
			
			TypedQuery<Message> query = em.createNamedQuery("Message.findByContent", Message.class);
			query.setParameter("content",((Message) object).getContent());
			msgSearched = query.getSingleResult();
			
		}
		
		
		
		
		return msgSearched;
		
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

	/* (non-Javadoc)
	 * @see org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal#retrieveList(java.lang.Object, java.lang.String)
	 */
	@Override
	public List<Message> retrieveList(Object object, String searchBy) {
	List<Message> returnLsit = new ArrayList<Message>();
	returnLsit = null;
		
		/***************************    search by receiver  ***************************************/
		if(searchBy == "idProj"){
			TypedQuery<Message> query = em.createNamedQuery("Message.findByIncludedRef", Message.class);
			query.setParameter("includedRef",((Message) object).getIncludedRef());
			returnLsit  = query.getResultList();
		}
		
		
		return returnLsit;
	}

}
