package org.esprit.gestion.rapports.config;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Company;
import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.StorageSpace;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherGrade;
import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.CompanyCoachQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StorageSpaceQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeachingUnitQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;

/**
 * Session Bean implementation class EPSInitializerBean
 */

@Singleton
@Startup
public class Tests {

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;

	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projectServ;

	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;

	@Inject
	@CompanyCoachQualifier
	IServiceLocal<CompanyCoach> compCoachServ;

	@Inject
	@StorageSpaceQualifier
	IServiceLocal<StorageSpace> storageServ;

	@Inject
	@TeachingUnitQualifier
	IServiceLocal<TeachingUnit> tUnitServ;
	
	@EJB
	IMessageFacadeLocal msgFacade;
	
	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;

	@PostConstruct
	public void createData() {
/*
		// ************créer TeachingUnit
		TeacherGrade grade = TeacherGrade.AD;
		TeachingUnit tu = new TeachingUnit("nameUnit", "localization");
		System.out.println("teacherUnit persist..................");
		tUnitServ.create(tu);
		System.out.println("teacherUnit persist  done.....................");
		
		// *************créer Teacher
		Teacher t = new Teacher("firstName", "lastName", "login", "password",
				"email", 1235456, grade, 5, tu);
		System.out.println("Teacher persist........................");
		teacherServ.create(t);
		System.out.println("Teacher persist done........................");
		
		// **************Créer StorageSpace
		StorageSpace storageSpace = new StorageSpace(30);
		System.out.println("storage space persist........................");
		storageServ.create(storageSpace);
		System.out.println("storage persist done........................");

		// **************Créer Student
		Student student = new Student("firstName", "lastName", "login",
				"password", "email", 465453132, "840444415", storageSpace);
		System.out.println("student persist......................");
		studentServ.create(student);
		System.out.println("student persist done...................");

		// ***************créer company
		Company company = new Company("adress", "name");
		
		// **************créer companyCoach
		CompanyCoach companyCoach = new CompanyCoach("firstName", "lastName",
				"login", "password", "email", 21324, "position", company);
		System.out.println("companycoach persist..................");
		compCoachServ.create(companyCoach);
		System.out.println("companycoach persist done.....................");
		
		// ************créer project
		Project proj = new Project("2013-2014", "topic", student);
		// lier companyCoach au projet
		proj.setCompanycoach(companyCoach);
		System.out.println("project persist...................");
		projectServ.create(proj);
		System.out.println("project persist done...............");

		msgFacade.send(null, proj, 0, 127, "ProjCoach");*/
		Message message = new Message("subjecttest", "contenttest", "receiver", "sender", null, null);
		msgServ.create(message);
		Message msgFound = (Message) msgServ.retrieve(message, "content");
		System.out.println("message ID......"+msgFound.getId());
	}

	public Tests() {
	}
}
