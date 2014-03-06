package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Message;

@Local
public interface IMessageFacadeLocal {

	public boolean send(String content,  Object additionnalObj, int idSender, int idReciever, String operation);
	
	/**
	 * il faut préciser message.state:
	 * if state = réponse requise: afficher sous forme de poop-up (accept-decline)
	 * @return
	 */
	public boolean receive(Message message);
	
	public boolean changeState(Message message);
}
