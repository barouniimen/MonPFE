package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Student;

/**
 * @author Imen Barouni
 *
 * 
 * 
 */

@Remote
public interface IStudentFacadeRemote {

	public List<Student> listStudentsWithoutProject();
	public String addStudent(Student student, int classId, String academicYear);
	public boolean studentHaveProject(Student student);
	public List<Student> listAllStudent();
}
