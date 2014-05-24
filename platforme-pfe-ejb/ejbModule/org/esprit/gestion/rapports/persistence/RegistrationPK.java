package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;
/**
 * @author Imen Barouni
 *
 */
@Embeddable
public class RegistrationPK implements Serializable {

	private int userId;
	private int classGroupId;
	private static final long serialVersionUID = 1L;
	
	

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + classGroupId;
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
		RegistrationPK other = (RegistrationPK) obj;
		if (classGroupId != other.classGroupId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	public RegistrationPK() {
		super();
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getClassGroupId() {
		return this.classGroupId;
	}

	public void setClassGroupId(int classGroupId) {
		this.classGroupId = classGroupId;
	}

	
}
