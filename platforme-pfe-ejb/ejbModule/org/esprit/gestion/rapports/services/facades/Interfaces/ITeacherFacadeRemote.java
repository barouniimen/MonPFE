package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Teacher;

@Remote
public interface ITeacherFacadeRemote {
	
	public String addTeacher(Teacher teacher);

}
