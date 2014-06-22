package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;

/**
 * @author Imen Barouni
 * 
 * 
 */

@Local
public interface IStudentFacadeLocal {

	public List<Student> listStudentsWithoutProject();
	
	public String addStudent(Student student, int classId, String academicYear);
	
	public boolean studentHaveProject(Student student);

	public List<Student> listAllStudent();
	
	public Project findStudentProj(int idStudent);

	public boolean hasCoach(int idStudent);

	public Teacher findCoach(int idStudent);

	public List<Student> listCoachStudents(int idCoach);

	public List<Student> listCorrectorStudents(int idCoach);

}
