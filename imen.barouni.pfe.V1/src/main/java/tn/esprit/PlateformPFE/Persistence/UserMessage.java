package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class UserMessage implements Serializable {

	private UserMessagePK pk;
	private String accessProfile;
	private User user;
	private Message message;
	// TODO accessProfile pourrait être une enumération.
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

	public String getAccessProfile() {
		return this.accessProfile;
	}

	public void setAccessProfile(String accessProfile) {
		this.accessProfile = accessProfile;
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

}
