package org.esprit.gestion.rapports.Model;

import java.util.Date;

import org.esprit.gestion.rapports.persistence.AssignResponseState;
import org.esprit.gestion.rapports.persistence.MessageType;

public class MessageModel {
	private int id;
	private int includedRef;
	private String subject;
	private String content;
	private int idReceiver;
	private String receiverName;
	private int idSender;
	private String senderName;
	private Date sendingDate;
	private MessageType type;
	private AssignResponseState responseState;
	private String declineCause;
	
	
	public MessageModel() {
		super();
		
	}
	public MessageModel(int id, int includedRef, String subject,
			String content, int idReceiver, String receiverName, int idSender,
			String senderName, Date sendingDate, MessageType type,
			AssignResponseState responseState, String declineCause) {
		super();
		this.id = id;
		this.includedRef = includedRef;
		this.subject = subject;
		this.content = content;
		this.idReceiver = idReceiver;
		this.receiverName = receiverName;
		this.idSender = idSender;
		this.senderName = senderName;
		this.sendingDate = sendingDate;
		this.type = type;
		this.responseState = responseState;
		this.declineCause = declineCause;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIncludedRef() {
		return includedRef;
	}
	public void setIncludedRef(int includedRef) {
		this.includedRef = includedRef;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIdReceiver() {
		return idReceiver;
	}
	public void setIdReceiver(int idReceiver) {
		this.idReceiver = idReceiver;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public int getIdSender() {
		return idSender;
	}
	public void setIdSender(int idSender) {
		this.idSender = idSender;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public Date getSendingDate() {
		return sendingDate;
	}
	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public AssignResponseState getResponseState() {
		return responseState;
	}
	public void setResponseState(AssignResponseState responseState) {
		this.responseState = responseState;
	}
	public String getDeclineCause() {
		return declineCause;
	}
	public void setDeclineCause(String declineCause) {
		this.declineCause = declineCause;
	}
	
	
	
	
}
