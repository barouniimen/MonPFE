package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Student;

/**
 * @author Imen Barouni
 *
 * 
 */

@Local
public interface IStudentFacadeLocal {

	public List<Student> listStudentsWithoutProject();
	
}
