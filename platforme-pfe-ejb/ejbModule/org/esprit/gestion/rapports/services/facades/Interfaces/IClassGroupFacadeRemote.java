package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.persistence.Student;

@Remote
public interface IClassGroupFacadeRemote {
	
	public ClassGroup findByAcademicYearForStudent(Student student, String AcademicYear);
	public String addClassGroup(ClassGroup classGroupe);

}
