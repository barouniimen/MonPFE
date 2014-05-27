package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Teacher;

@Local
public interface ITeacherFacadeLocal {
	
	public String addTeacher(Teacher teacher);

}
