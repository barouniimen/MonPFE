package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;

@Stateless
@UserMessageQualifier
public class UserMessageServ implements IServiceLocal<UserMessage>, IServiceRemote<UserMessage>{

	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public void create(Object object) {
		em.persist(object);
}

	@Override
	public Object retrieve(Object object, String searchBy) {
		// TODO Auto-generated method stub
		UserMessage userMsgSearched = null;
		
		/*********************** Search by pk ***************************/
		if (searchBy == "PK") {
			TypedQuery<UserMessage> query = em.createNamedQuery(
					"UserMessage.findByuserIdandMessageId", UserMessage.class);
			query.setParameter("messageId", ((UserMessage) object).getPk().getMessageId());
			query.setParameter("userId", ((UserMessage) object).getPk().getUserId());

					userMsgSearched = query.getSingleResult();

			
		}
		
		
		return userMsgSearched;
}

	@Override
	public List<UserMessage> retrieveList(Object object, String searchBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object object) {
		em.merge(object);
		
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
