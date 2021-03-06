package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Registration;
import org.esprit.gestion.rapports.persistence.RegistrationPK;
import org.esprit.gestion.rapports.persistence.StorageSpace;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.RegistrationQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StorageSpaceQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
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

	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;

	@EJB
	IProjectFacadeLocal projFacade;

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

		if (allStudents.isEmpty()) {
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

			return "reussi";
		}

		for (int i = 0; i < allStudents.size(); i++) {
			if (allStudents.get(i).getRegistrationNumber().toLowerCase()
					.equals(student.getRegistrationNumber().toLowerCase())) {
				resultState = "regNbreExist";
			} else if (allStudents.get(i).getLogin().toLowerCase()
					.equals(student.getLogin().toLowerCase())) {
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
			System.out.println("storage sp!!!");
			student.setStorageSpace(space);

			// create student
			studentServ.create(student);
			System.out.println("student created!!!!");

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
			System.out.println("reg!!!!!!!!!!!");

		}

		return resultState;
	}

	@Override
	public boolean studentHaveProject(Student student) {
		boolean hasProj;

		student = (Student) studentServ.retrieve(student, "ID");
		if (student.getProject() == null) {
			hasProj = false;
		}

		else
			hasProj = true;

		return hasProj;
	}

	@Override
	public List<Student> listAllStudent() {
		List<Student> returnList = new ArrayList<Student>();
		returnList = studentServ.retrieveList(null, "ALL");

		return returnList;
	}

	@Override
	public Project findStudentProj(int idStudent) {
		Student st = new Student();
		st.setId(idStudent);
		st = (Student) studentServ.retrieve(st, "ID");
		Project proj = new Project();
		proj = st.getProject();

		return proj;

	}

	@Override
	public boolean hasCoach(int idStudent) {
		Student st = new Student();
		st.setId(idStudent);

		st = (Student) studentServ.retrieve(st, "ID");

		Project proj = new Project();
		proj = st.getProject();

		proj = (Project) projServ.retrieve(proj, "ID");

		List<TeacherRole> roles = new ArrayList<TeacherRole>();
		roles = proj.getTeacherRoles();

		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getRole().equals(TeacherRoleType.ENCADRANT)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Teacher findCoach(int idStudent) {
		System.out.println("on hasCoach!!!!!!!!!!!!!");

		Teacher t = new Teacher();
		boolean found = false;

		Student st = new Student();
		st.setId(idStudent);

		st = (Student) studentServ.retrieve(st, "ID");

		Project proj = new Project();
		proj = st.getProject();

		proj = (Project) projServ.retrieve(proj, "ID");

		List<TeacherRole> roles = new ArrayList<TeacherRole>();
		roles = proj.getTeacherRoles();

		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getRole().equals(TeacherRoleType.ENCADRANT)) {
				found = true;
				t.setId(roles.get(i).getPk().getTeacherId());
				t = (Teacher) teacherServ.retrieve(t, "ID");

			}
		}

		if (found == false) {
			System.out.println("result hasCoach" + found);
			return null;
		} else {

			System.out.println("rexsult hasCoach" + t);
			return t;
		}

	}

	@Override
	public List<Student> listCoachStudents(int idCoach) {
		List<Student> studentList = new ArrayList<Student>();

		List<Project> lisProj = new ArrayList<Project>();

		lisProj = projFacade.listProjCoached(idCoach);

		for (int i = 0; i < lisProj.size(); i++) {
			Student st = new Student();
			st = lisProj.get(i).getStudent();
			st = (Student) studentServ.retrieve(st, "ID");

			studentList.add(st);
		}

		return studentList;
	}

	@Override
	public List<Student> listCorrectorStudents(int idCoach) {
		List<Student> studentList = new ArrayList<Student>();

		List<Project> lisProj = new ArrayList<Project>();

		lisProj = projFacade.listProjCorrector(idCoach);

		for (int i = 0; i < lisProj.size(); i++) {
			Student st = new Student();
			st = lisProj.get(i).getStudent();
			st = (Student) studentServ.retrieve(st, "ID");
			studentList.add(st);
		}

		return studentList;
	}
}
