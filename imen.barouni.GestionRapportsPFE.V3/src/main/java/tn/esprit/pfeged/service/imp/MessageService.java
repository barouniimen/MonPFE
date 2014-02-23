package tn.esprit.pfeged.service.imp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.pfeged.persistence.Message;
import tn.esprit.pfeged.service.interfaces.IServiceLocal;
import tn.esprit.pfeged.service.interfaces.IServiceRemote;


@Stateless
public class MessageService implements IServiceLocal<Message>, IServiceRemote<Message> {

    @PersistenceContext
    EntityManager em;

    public MessageService() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Object object) {
		em.persist(object);
	}

	@Override
	public Object retrieve(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object object) {
		TypedQuery<Message> query = em.createNamedQuery("Message.findByReceiver", Message.class);
		query.setParameter("receiver", ((Message)object).getReceiver());
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

}
