package tn.esprit.pfeged.persistence;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class RegistrationPK implements Serializable {

	private int userId;
	private int classGroupId;
	private Calendar academicYear;
	private static final long serialVersionUID = 1L;

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

	@Temporal(TemporalType.DATE)
	public Calendar getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(Calendar academicYear) {
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
