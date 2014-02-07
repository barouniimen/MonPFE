package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserMessagePK implements Serializable {

	private int messageId;
	private int userId;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + messageId;
		result = prime * result + userId;
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
		UserMessagePK other = (UserMessagePK) obj;
		if (messageId != other.messageId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

}
