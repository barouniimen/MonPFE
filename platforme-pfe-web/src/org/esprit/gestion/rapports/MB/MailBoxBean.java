package org.esprit.gestion.rapports.MB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;


@ManagedBean(name="mailbox")
@ViewScoped
public class MailBoxBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//EJB
	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;
	
	private int nbreMsgToRead;
	private int nbrMsgSent;
	private int nbrRequestMsg;
	private int nbrTeachersMsg;
	private List<Message> msgReceived;
	private Message msg;
	
	
	/******************************* Init Bean *************************************/
	
	@PostConstruct
	public void init(){
		nbreMsgToRead = 0;
		nbrMsgSent = 0;
		nbrRequestMsg = 0;
		nbrTeachersMsg = 0;
		
		//init msgList
		/*msgReceived = new ArrayList<Message>();
		msgReceived = null;
		setMsg(new Message());
		msg.setReceiver("administrateur");
		msgReceived = msgServ.retrieveList(msg, "RECEIVER");
		System.out.println("msg= "+msgReceived.get(0));
		*/
		
	}
	
	
	/******************************* Constructor ************************************/
	
	public MailBoxBean() {
		super();
		
	}
	
	/**************************** Getter && Setter **********************************/
	
	public int getNbreMsgToRead() {
		return nbreMsgToRead;
	}

	public void setNbreMsgToRead(int nbreMsgToRead) {
		this.nbreMsgToRead = nbreMsgToRead;
	}

	public int getNbrMsgSent() {
		return nbrMsgSent;
	}

	public void setNbrMsgSent(int nbrMsgSent) {
		this.nbrMsgSent = nbrMsgSent;
	}

	public int getNbrRequestMsg() {
		return nbrRequestMsg;
	}

	public void setNbrRequestMsg(int nbrRequestMsg) {
		this.nbrRequestMsg = nbrRequestMsg;
	}

	public int getNbrTeachersMsg() {
		return nbrTeachersMsg;
	}

	public void setNbrTeachersMsg(int nbrTeachersMsg) {
		this.nbrTeachersMsg = nbrTeachersMsg;
	}


	public List<Message> getMsgReceived() {
		return msgReceived;
	}


	public void setMsgReceived(List<Message> msgReceived) {
		this.msgReceived = msgReceived;
	}


	public Message getMsg() {
		return msg;
	}


	public void setMsg(Message msg) {
		this.msg = msg;
	}

}
