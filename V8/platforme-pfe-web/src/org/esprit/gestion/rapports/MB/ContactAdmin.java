package org.esprit.gestion.rapports.MB;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IUserFacadeLocal;

@ManagedBean
@SessionScoped
public class ContactAdmin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	private String topic;
	private String content;
	
	@EJB
	IMessageFacadeLocal msgFacade;
	
	@EJB
	IUserFacadeLocal userFacade;
	
	public void sendMessage(){
		//TODO à tester
		User admin = userFacade.findAdmin();
		msgFacade.send(content, topic, 0, admin.getId());
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Message envoyer", "Message envoyer avec succes"));
		
	}
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
