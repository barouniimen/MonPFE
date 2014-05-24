package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.persistence.Student;

@Local
public interface IClassGroupFacadeLocal {

	
	public ClassGroup findByAcademicYearForStudent(Student student, String AcademicYear);
	public String addClassGroup(ClassGroup classGroupe);
}
