package org.esprit.gestion.rapports.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.KeyWordCategory;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.persistence.Registration;
import org.esprit.gestion.rapports.persistence.Speciality;
import org.esprit.gestion.rapports.persistence.StorageSpace;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.persistence.TeachingUnitDomain;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ClassGroupeQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.CompanyCoachQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.KWcategoryQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.KeyWordsQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectDomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.RegistrationQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.SpecialityQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StorageSpaceQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeachUnitDomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeachingUnitQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeLocal;

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
	@ProjectDomainQualifier
	IServiceLocal<ProjectDomain> projDomServ;

	@Inject
	@KWcategoryQualifier
	IServiceLocal<KeyWordCategory> kwcategServ;

	@Inject
	@RegistrationQualifier
	IServiceLocal<Registration> registrationServ;

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

	@Inject
	@TeachUnitDomainQualifier
	IServiceLocal<TeachingUnitDomain> tunitDomServ;

	@Inject
	@ClassGroupeQualifier
	IServiceLocal<ClassGroup> classGrpeServ;

	@Inject
	@UserQualifier
	IServiceLocal<User> userServ;

	@Inject
	@KeyWordsQualifier
	IServiceLocal<KeyWord> keyWServ;

	@Inject
	@SpecialityQualifier
	IServiceLocal<Speciality> specServ;

	@EJB
	IMessageFacadeLocal msgFacade;

	@EJB
	IProjectFacadeLocal projFacade;

	@EJB
	ICoachFacadeLocal coachFacade;

	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;

	@Inject
	@DomainQualifier
	IServiceLocal<Domain> domServ;
	
	@EJB
	IStudentFacadeLocal stFacade;

	@PostConstruct
	public void createData() {
		/*// ***créer admin
		User admin = new User();
		
		admin.setFirstName("admin");
		admin.setLastName("admin");
		admin.setLogin("admin");
		admin.setPassword("admin");
		userServ.create(admin);*/

		/*// ************créer TeachingUnit
		TeacherGrade grade = TeacherGrade.AD;
		TeachingUnit tu = new TeachingUnit("Unité mobile", "el ghazela");
		tUnitServ.create(tu);

		// **********créer domain + connexion avec teaching unit
		Domain dom = new Domain();
		dom.setDomainName("Java");
		domServ.create(dom);

		dom = (Domain) domServ.retrieve(dom, "NAME");

		TeachingUnitDomain tud = new TeachingUnitDomain();
		TeachingUnitDomainPK pktud = new TeachingUnitDomainPK();
		tu = (TeachingUnit) tUnitServ.retrieve(tu, "NAME");
		pktud.setTeachingUnitId(tu.getId());
		pktud.setDomainId(dom.getId());
		tud.setPk(pktud);
		tunitDomServ.create(tud);

		// *************créer Teacher
		Teacher t = new Teacher("salah", "ben ali", "ss", "ss",
				"salah@esprit.tn", 2022284, grade, 5, tu);
		teacherServ.create(t);*/

	/*	// **************Créer StorageSpace
		StorageSpace storageSpace = new StorageSpace(50);
		storageServ.create(storageSpace);

		// ***créer speciality!!!!!!!!!!!!!!!!!!!!
		Speciality spaciality = new Speciality();
		spaciality.setTitle("BI");
		specServ.create(spaciality);
		
		spaciality=(Speciality) specServ.retrieve(spaciality, "TITLE");

		// créer classGroup!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ClassGroup classgrp = new ClassGroup();
		classgrp.setReference("5infini4");
		classgrp.setSpeciality(spaciality);
		classGrpeServ.create(classgrp);

		// **************Créer Student
		Student student = new Student("maroua", "maroua", "maroua",
				"maroua", "maroua@esprit.tn", 42203132, "0351013",
				storageSpace);
		studentServ.create(student);
		
		// **********créer domain + connexion avec teaching unit
				Domain dom = new Domain();
				dom.setDomainName("androide");
				domServ.create(dom);

				dom = (Domain) domServ.retrieve(dom, "NAME");
				
		// créer association classgroup-student (registration)!!!!!!!!!!!!!!!!!!!!!!!!
		String academicYear = "2012-2013";
		student = (Student) studentServ.retrieve(student, "LP");
		System.out.println("student:   "+student.getId());
		classgrp = (ClassGroup) classGrpeServ.retrieve(classgrp, "REF");
		System.out.println("classgrpe:   "+classgrp.getId());
		Registration registration = new Registration();
		RegistrationPK pkreg = new RegistrationPK();
		pkreg.setClassGroupId(classgrp.getId());
		pkreg.setUserId(student.getId());
		pkreg.setAcademicYear(academicYear);
		registration.setRegistrationPK(pkreg);
		
		registrationServ.create(registration);
		System.out.println("ok1111");

		// ***************créer company
		Company company = new Company("el ghazela", "esprittech");

		// **************créer companyCoach
		CompanyCoach companyCoach = new CompanyCoach("foulen", "ben foulen",
				null, null, "email", 21324, "directeur", company);
		compCoachServ.create(companyCoach);
		System.out.println("ok2222222");
		
		// ************créer project
		
		// lier companyCoach au projet
		
		ValidationState validationState = ValidationState.WAITINGSOUT;
		Date startDate = new Date();
		
		Project proj = new Project(startDate, academicYear, "topic 3", validationState, companyCoach, student);
		projectServ.create(proj);
		System.out.println("ok333333333333333");
		
		ProjectDomain projDom = new ProjectDomain();
		ProjectDomainPK pkPD = new ProjectDomainPK();
		proj = (Project) projectServ.retrieve(proj, "TOPIC");
		System.out.println("project: "+proj.getId());
		pkPD.setDomainId(dom.getId());
		pkPD.setProjId(proj.getId());
		projDom.setPk(pkPD);
		projDomServ.create(projDom);
		System.out.println("jusque là ok");*/
	/*************************************************************************************************************/	
		/*// ************créer TeachingUnit
				TeacherGrade grade2 = TeacherGrade.AT;
				TeachingUnit tu2 = new TeachingUnit("Unité jee", "el ghazela");
				tUnitServ.create(tu2);

				// **********créer domain + connexion avec teaching unit
				Domain dom2 = new Domain();
				dom2.setDomainName(".net");
				domServ.create(dom2);

				dom2 = (Domain) domServ.retrieve(dom2, "NAME");

				TeachingUnitDomain tud2 = new TeachingUnitDomain();
				TeachingUnitDomainPK pktud2 = new TeachingUnitDomainPK();
				tu2 = (TeachingUnit) tUnitServ.retrieve(tu2, "NAME");
				pktud2.setTeachingUnitId(tu2.getId());
				pktud2.setDomainId(dom2.getId());
				tud2.setPk(pktud2);
				tunitDomServ.create(tud2);

				// *************créer Teacher
				Teacher t2 = new Teacher("medamine", "medamine", "medamine", "medamine",
						"hedi@esprit.tn", 20005684, grade2, 5, tu2);
				teacherServ.create(t2);
				Teacher t3 = new Teacher("heythem", "hédi", "teacher", "teacher",
						"hedi@esprit.tn", 20225684, grade2, 5, tu2);
				teacherServ.create(t3);

		

				// ***créer speciality!!!!!!!!!!!!!!!!!!!!
				Speciality spaciality2 = new Speciality();
				spaciality2.setTitle("Electromécanique");
				specServ.create(spaciality2);
				
				
				spaciality2=(Speciality) specServ.retrieve(spaciality2, "TITLE");

				// créer classGroup!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				ClassGroup classgrp2 = new ClassGroup();
				classgrp2.setReference("5EM8");
				classgrp2.setSpeciality(spaciality2);
				classGrpeServ.create(classgrp2);

				// **************Créer Student
				Student student2 = new Student("aloulou", "ben ali", "ali",
						"ali", "ali@esprit.tn", 40053132, "03M2013",
						storageSpace);
				studentServ.create(student2);

				// créer association classgroup-student (registration)!!!!!!!!!!!!!!!!!!!!!!!!
				student2 = (Student) studentServ.retrieve(student2, "LP");
				classgrp2 = (ClassGroup) classGrpeServ.retrieve(classgrp2, "REF");
				Registration registration2 = new Registration();
				RegistrationPK pkreg2 = new RegistrationPK();
				pkreg2.setClassGroupId(classgrp2.getId());
				pkreg2.setUserId(student2.getId());
				registration2.setRegistrationPK(pkreg2);
				
				
				pkreg2.setAcademicYear(academicYear);
				registrationServ.create(registration2);

				// ***************créer company
				Company company2 = new Company("el ghazela", "orange");

				// **************créer companyCoach
				CompanyCoach companyCoach2 = new CompanyCoach("foulen", "ben foulen",
						null, null, "email", 21324, "directeur", company2);
				compCoachServ.create(companyCoach2);
				
				// ************créer project
				
				// lier companyCoach au projet
				
				ValidationState validationState2 = ValidationState.WAITING;
				Project proj2 = new Project(startDate, academicYear, "topic 2", validationState2, companyCoach2, student2);
				projectServ.create(proj2);
				
				
				ProjectDomain projDom2 = new ProjectDomain();
				ProjectDomainPK pkPD2 = new ProjectDomainPK();
				proj2 = (Project) projectServ.retrieve(proj2, "TOPIC");
				pkPD2.setDomainId(dom2.getId());
				pkPD2.setProjId(proj2.getId());
				projDom2.setPk(pkPD2);
				projDomServ.create(projDom2);
				
			
				
				
				//***créer keyWCategory
				
				KeyWordCategory kwc1 = new KeyWordCategory();
				kwc1.setCategoryName("Technologie");
				
				
				KeyWordCategory kwc2 = new KeyWordCategory();
				kwc2.setCategoryName("Méthodologie");
				
				kwcategServ.create(kwc2);
				kwcategServ.create(kwc1);

								
				
				//*****créer keyWords
				KeyWord kw = new KeyWord();
				kw.setCategory(kwc2);
				kw.setName("Scrum");
				keyWServ.create(kw);
				
				KeyWord kw2 = new KeyWord();
				kw2.setCategory(kwc1);
				kw2.setName("JEE");
				keyWServ.create(kw2);*/
		
		
		//affecter coach � projet
		/*Teacher t = new Teacher();
		t.setId(621);
		t= (Teacher) teacherServ.retrieve(t, "ID");
		System.out.println("teacher!!!! "+t.getFirstName());
		
		Project p = new Project();
		p.setId(636);
		p= (Project) projectServ.retrieve(p, "ID");
		
		Message m = new Message();
		m.setId(672);
		m= (Message) msgServ.retrieve(m, "ID");
		
		coachFacade.CoachProjectAccept(t, m);*/
	}

	public Tests() {
	}
}
