package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
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
public class UserMessageServ implements IServiceLocal<UserMessage>,
		IServiceRemote<UserMessage> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void create(Object object) {
		em.persist(object);

	}

	@Override
	public Object retrieve(Object object, String searchBy) {
	
		UserMessage userMsgSearched = null;

		/*********************** Search by pk ***************************/
		if (searchBy == "PK") {
			try {
				TypedQuery<UserMessage> query = em.createNamedQuery(
						"UserMessage.findByuserIdandMessageId",
						UserMessage.class);
				query.setParameter("messageId", ((UserMessage) object).getPk()
						.getMessageId());
				query.setParameter("userId", ((UserMessage) object).getPk()
						.getUserId());

				userMsgSearched = query.getSingleResult();
				
			} catch (javax.persistence.NoResultException ex) {
				
				return null;
			}

		}

		return userMsgSearched;
	}

	@Override
	public List<UserMessage> retrieveList(Object object, String searchBy) {
		List<UserMessage> userMsgSearched = new ArrayList<UserMessage>();

		/*********************** Search by userId ***************************/
		if (searchBy == "userId") {
			TypedQuery<UserMessage> query = em.createNamedQuery(
					"UserMessage.findByuserId", UserMessage.class);
			query.setParameter("userId", ((UserMessage) object).getPk()
					.getUserId());

			userMsgSearched = query.getResultList();

		}

		return userMsgSearched;
	}

	@Override
	public void update(Object object) {
		System.out.println("updating user msg");
		
		em.merge(object);

		System.out.println("updated!!!!!");
	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public void delete(Object object) {
	em.remove(object);

	}

}
