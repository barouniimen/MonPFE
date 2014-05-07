package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
		@NamedQuery(name = "Message.findByContent", query = "SELECT m FROM Message m WHERE m.content = :content"),
		@NamedQuery(name = "Message.findByReceiver", query = "SELECT m FROM Message m WHERE m.receiver = :receiver"),
		@NamedQuery(name = "Message.findBySender", query = "SELECT m FROM Message m WHERE m.sender = :sender"),
		@NamedQuery(name = "Message.findBySendingDate", query = "SELECT m FROM Message m WHERE m.sendingDate = :sendingDate"),
		@NamedQuery(name = "Message.findByType", query = "SELECT m FROM Message m WHERE m.type = :type"),
		@NamedQuery(name = "Message.findBySubject", query = "SELECT m FROM Message m WHERE m.subject = :subject") })
public class Message implements Serializable {

	private int id;
	private int includedRef;
	private String subject;
	private String content;
	private String receiver;
	private String sender;
	private Calendar sendingDate;
	private MessageType type;
	private List<UserMessage> userMessages;
	private static final long serialVersionUID = 1L;

	
	
	public Message(String subject, String content, String receiver,
			String sender, Calendar sendingDate, MessageType type) {
		super();
		this.subject = subject;
		this.content = content;
		this.receiver = receiver;
		this.sender = sender;
		this.sendingDate = sendingDate;
		this.type = type;
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

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getSendingDate() {
		return this.sendingDate;
	}

	public void setSendingDate(Calendar sendingDate) {
		this.sendingDate = sendingDate;
	}

	@OneToMany(mappedBy = "message")
	public List<UserMessage> getUserMessages() {
		return userMessages;
	}

	public void setUserMessages(List<UserMessage> userMessages) {
		this.userMessages = userMessages;
	}

	@Enumerated
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

	


	
}
