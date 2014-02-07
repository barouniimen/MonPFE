package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class TeacherRolePK implements Serializable {
	
	private int teacherId;
	private int projectId;
	private static final long serialVersionUID = 1L;
	
	public TeacherRolePK() {
	}
	
	public TeacherRolePK(int teacherId, int projectId) {
		super();
		this.teacherId = teacherId;
		this.projectId = projectId;
	}

	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + projectId;
		result = prime * result + teacherId;
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
		TeacherRolePK other = (TeacherRolePK) obj;
		if (projectId != other.projectId)
			return false;
		if (teacherId != other.teacherId)
			return false;
		return true;
	}
}
