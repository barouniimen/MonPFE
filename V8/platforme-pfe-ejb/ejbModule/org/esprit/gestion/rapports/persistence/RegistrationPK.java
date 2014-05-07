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
	private String academicYear;
	private static final long serialVersionUID = 1L;
	
	

	public RegistrationPK(int userId, int classGroupId, String academicYear) {
		super();
		this.userId = userId;
		this.classGroupId = classGroupId;
		this.academicYear = academicYear;
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

	
	public String getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((academicYear == null) ? 0 : academicYear.hashCode());
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
		if (academicYear == null) {
			if (other.academicYear != null)
				return false;
		} else if (!academicYear.equals(other.academicYear))
			return false;
		if (classGroupId != other.classGroupId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
}
