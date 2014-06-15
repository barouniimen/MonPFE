package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Imen Barouni
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
		@NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
		@NamedQuery(name = "Message.findByIdReciever", query = "SELECT m FROM Message m WHERE m.idReceiver = :idReceiver"),
		@NamedQuery(name = "Message.findByIdSender", query = "SELECT m FROM Message m WHERE m.idSender = :idSender"),
		@NamedQuery(name = "Message.findByContent", query = "SELECT m FROM Message m WHERE m.content = :content"),
		@NamedQuery(name = "Message.findByIncludedRef", query = "SELECT m FROM Message m WHERE m.includedRef = :includedRef"),
		@NamedQuery(name = "Message.findBySendingDate", query = "SELECT m FROM Message m WHERE m.sendingDate = :sendingDate"),
		@NamedQuery(name = "Message.findByType", query = "SELECT m FROM Message m WHERE m.type = :type"),
		@NamedQuery(name = "Message.findMsgToChangeState", query = "SELECT m FROM Message m WHERE m.type = :type AND m.idReceiver = :idReceiver AND m.responseState = :responseState AND m.includedRef = :includedRef"),
		@NamedQuery(name = "Message.findBySubject", query = "SELECT m FROM Message m WHERE m.subject = :subject") })
public class Message implements Serializable {

	private int id;
	private int includedRef;
	private String subject;
	private String content;
	private int idReceiver;
	private int idSender;
	private Date sendingDate;
	private MessageType type;
	private AssignResponseState responseState;
	private String declineCause;
	private List<UserMessage> userMessages;
	private static final long serialVersionUID = 1L;

	public Message(int includedRef, String subject, String content,
			int idReceiver, int idSender, Date sendingDate, MessageType type,
			AssignResponseState responseState, String declineCause) {
		super();

		this.includedRef = includedRef;
		this.subject = subject;
		this.content = content;
		this.idReceiver = idReceiver;
		this.idSender = idSender;
		this.sendingDate = sendingDate;
		this.type = type;
		this.responseState = responseState;
		this.declineCause = declineCause;

	}

	public Message() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendingDate() {
		return this.sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}

	@OneToMany(mappedBy = "message")
	public List<UserMessage> getUserMessages() {
		return userMessages;
	}

	public void setUserMessages(List<UserMessage> userMessages) {
		this.userMessages = userMessages;
	}

	@Enumerated(EnumType.STRING)
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public int getIncludedRef() {
		return includedRef;
	}

	public void setIncludedRef(int includedRef) {
		this.includedRef = includedRef;
	}

	@Enumerated(EnumType.STRING)
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

	public int getIdSender() {
		return idSender;
	}

	public void setIdSender(int idSender) {
		this.idSender = idSender;
	}

	public int getIdReceiver() {
		return idReceiver;
	}

	public void setIdReceiver(int idReceiver) {
		this.idReceiver = idReceiver;
	}

}
