package tn.esprit.pfeged.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserMessage implements Serializable {

	private UserMessagePK pk;
	private User user;
	private Message message;
	private static final long serialVersionUID = 1L;

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
