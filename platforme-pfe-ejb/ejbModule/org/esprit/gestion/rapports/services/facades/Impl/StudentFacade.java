package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Registration;
import org.esprit.gestion.rapports.persistence.RegistrationPK;
import org.esprit.gestion.rapports.persistence.StorageSpace;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.RegistrationQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StorageSpaceQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeRemote;

@Stateless
public class StudentFacade implements IStudentFacadeLocal, IStudentFacadeRemote {

	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;

	@Inject
	@RegistrationQualifier
	IServiceLocal<Registration> registrationServ;

	@Inject
	@StorageSpaceQualifier
	IServiceLocal<StorageSpace> storageSpaceServ;

	@Override
	public List<Student> listStudentsWithoutProject() {

		// init vars----------------------------------------------------
		List<Student> returnList = new ArrayList<Student>();
		List<Student> allStudentsList = new ArrayList<Student>();
		int j = 0;

		// retrieve all students----------------------------------------
		allStudentsList = studentServ.retrieveList(null, "ALL");

		// find students without projects
		for (int i = 0; i < allStudentsList.size(); i++) {
			if (allStudentsList.get(i).getProject() == null) {
				returnList.add(allStudentsList.get(i));	
				j++;
			}
		}
		if (j == 0) {
			returnList = null;
		}

		return returnList;
	}

	@Override
	public String addStudent(Student student, int classId, String academicYear) {
		String resultState = "init";
		List<Student> allStudents = new ArrayList<Student>();

		allStudents = studentServ.retrieveList(null, "ALL");

		for (int i = 0; i < allStudents.size(); i++) {
			if (allStudents.get(i).getRegistrationNumber().toLowerCase()
					.equals(student.getRegistrationNumber().toLowerCase())) {
				resultState = "regNbreExist";
			} else if (allStudents.get(i).getLogin().toLowerCase().equals(student.getLogin().toLowerCase())) {
				resultState = "loginExist";
			} else if (allStudents.get(i).getPassword().toLowerCase()
					.equals(student.getPassword().toLowerCase())) {
				resultState = "passExist";
			} else {
				resultState = "reussi";
			}
		}

		if (resultState == "reussi") {
			// create storage space
			StorageSpace space = student.getStorageSpace();
			storageSpaceServ.create(space);
			student.setStorageSpace(space);

			// create student
			studentServ.create(student);
			
			// find student id
			int studentId = student.getId();
			
			RegistrationPK pk = new RegistrationPK();
			pk.setClassGroupId(classId);
			pk.setUserId(studentId);
			Registration reg = new Registration();
			reg.setAcademicYear(academicYear);
			reg.setRegistrationPK(pk);

			// create registration

			registrationServ.create(reg);

		}

		return resultState;
	}
}
