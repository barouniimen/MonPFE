package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "UserMessage.findAll", query = "SELECT t FROM UserMessage t"),
	@NamedQuery(name = "UserMessage.findBymessageId", query = "SELECT t FROM UserMessage t WHERE t.pk.messageId = :messageId"),
	@NamedQuery(name = "UserMessage.findByuserId", query = "SELECT t FROM UserMessage t WHERE t.pk.userId = :userId"),
	@NamedQuery(name = "UserMessage.findByuserIdandMessageId", query = "SELECT t FROM UserMessage t WHERE t.pk.userId = :userId and t.pk.messageId = :messageId")})
public class UserMessage implements Serializable {

	private UserMessagePK pk;
	private MessageAccess access;
	private User user;
	private Message message;
	private static final long serialVersionUID = 1L;

	
	
	public UserMessage(UserMessagePK pk, MessageAccess access) {
		super();
		this.pk = pk;
		this.access = access;
	}

	public UserMessage() {
		super();
	}

	@EmbeddedId
	public UserMessagePK getPk() {
		return pk;
	}

	public void setPk(UserMessagePK pk) {
		this.pk = pk;
	}

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "messageId", referencedColumnName = "id", insertable = false, updatable = false)
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	@Enumerated
	public MessageAccess getAccess() {
		return access;
	}

	public void setAccess(MessageAccess access) {
		this.access = access;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserMessage other = (UserMessage) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
}
