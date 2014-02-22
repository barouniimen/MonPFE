package tn.esprit.pfeged.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
		@NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
		@NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
		@NamedQuery(name = "Message.findByContent", query = "SELECT m FROM Message m WHERE m.content = :content"),
		@NamedQuery(name = "Message.findByReceiver", query = "SELECT m FROM Message m WHERE m.receiver = :receiver"),
		@NamedQuery(name = "Message.findBySender", query = "SELECT m FROM Message m WHERE m.sender = :sender"),
		@NamedQuery(name = "Message.findBySendingDate", query = "SELECT m FROM Message m WHERE m.sendingDate = :sendingDate"),
		@NamedQuery(name = "Message.findByState", query = "SELECT m FROM Message m WHERE m.state = :state"),
		@NamedQuery(name = "Message.findBySubject", query = "SELECT m FROM Message m WHERE m.subject = :subject") })
public class Message implements Serializable {

	private int id;
	private String subject;
	private String content;
	private String receiver;
	private String sender;
	private Calendar sendingDate;
	private MessageState state;
	private List<UserMessage> messages;
	private static final long serialVersionUID = 1L;

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
	public List<UserMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<UserMessage> messages) {
		this.messages = messages;
	}

	@Enumerated(EnumType.STRING)
	public MessageState getState() {
		return state;
	}

	public void setState(MessageState state) {
		this.state = state;
	}
}
