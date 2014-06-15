package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;

@MessagesQualifier
@Stateless
public class MessagesServ implements IServiceLocal<Message>,
		IServiceRemote<Message> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		em.merge(object);
	}

	@Override
	public void create(Object object) {
		em.persist(object);
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		Message msgSearched = null;

		/*************************** search by ID *********************************************/
		if (searchBy == "ID") {
			TypedQuery<Message> query = em.createNamedQuery("Message.findById",
					Message.class);
			query.setParameter("id", ((Message) object).getId());
			msgSearched = query.getSingleResult();
		}
		/*************************** search by Content ***************************************/
		else if (searchBy == "CONTENT") {

			TypedQuery<Message> query = em.createNamedQuery(
					"Message.findByContent", Message.class);
			query.setParameter("content", ((Message) object).getContent());
			msgSearched = query.getSingleResult();

		}
		else if(searchBy == "ToChangeState"){
			
			TypedQuery<Message> query = em.createNamedQuery(
					"Message.findMsgToChangeState", Message.class);
			query.setParameter("type", ((Message) object).getType());
			query.setParameter("idReceiver", ((Message) object).getIdReceiver());
			query.setParameter("responseState", ((Message) object).getResponseState());
			query.setParameter("includedRef", ((Message) object).getIncludedRef());
			
			msgSearched = query.getSingleResult();
			if(msgSearched.equals(null)){
				return null;
			}
			
		}

		return msgSearched;

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
	public List<Message> retrieveList(Object object, String searchBy) {
		List<Message> returnLsit = new ArrayList<Message>();
		returnLsit = null;

		/*************************** search by idProj ***************************************/
		if (searchBy == "idProj") {
			TypedQuery<Message> query = em.createNamedQuery(
					"Message.findByIncludedRef", Message.class);
			query.setParameter("includedRef",
					((Message) object).getIncludedRef());
			returnLsit = query.getResultList();
		}
		/*************************** search by receiver ***************************************/
		else if (searchBy == "idRec") {
			TypedQuery<Message> query = em.createNamedQuery(
					"Message.findByIdReciever", Message.class);
			query.setParameter("idReceiver", ((Message) object).getIdReceiver());
			returnLsit = query.getResultList();
			
		}
		/*************************** search by sender ***************************************/
		else if (searchBy == "idSender") {
			TypedQuery<Message> query = em.createNamedQuery(
					"Message.findByIdSender", Message.class);
			query.setParameter("idSender", ((Message) object).getIdSender());
			returnLsit = query.getResultList();
		}
		return returnLsit;
	}

}
