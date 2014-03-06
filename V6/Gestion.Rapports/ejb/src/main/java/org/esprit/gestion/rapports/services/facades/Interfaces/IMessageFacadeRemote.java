package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Message;

@Remote
public interface IMessageFacadeRemote {
	
	/**
	 * il faut préciser message.state:
	 * if state = réponse requise: afficher sous forme de poop-up (accept-decline)
	 * @return
	 */
	public boolean send(String content, Object additionnalObj, int idSender, int idReciever, String operation);

	public boolean receive(Message message);
	
	public boolean changeState(Message message);
}
