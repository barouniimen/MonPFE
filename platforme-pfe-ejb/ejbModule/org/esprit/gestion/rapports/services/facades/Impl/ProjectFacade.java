package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.persistence.ProjectDomainPK;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.CompanyCoachQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectDomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeRemote;


@Stateless
public class ProjectFacade implements IProjectFacadeLocal, IProjectFacadeRemote {

	@Inject
	@TecherRoleQualifier
	IServiceLocal<TeacherRole> roleServ;
	
	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;
	
	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;
	
	@Inject
	@ProjectDomainQualifier
	IServiceLocal<ProjectDomain> projDomServ;
	
	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;
	
	@Inject
	@CompanyCoachQualifier
	IServiceLocal<CompanyCoach> compCoachServ;

	@EJB
	IMessageFacadeLocal msgFacade;
	
	@Inject
	@DomainQualifier
	IServiceLocal<Domain> domServ;

	
	@Override
	public void assignCoachToProject(Teacher teacher, int iDproject) {
		int idReciever = teacher.getId();
		msgFacade.sendAffectCoach( iDproject,idReciever);
		
		//TODO activate waiting => une facade adminUtil (méthode qui parcours les messages envoyés,
		//non lus et compare les dates d'envoi par rapport à la date en cours
			}

	@Override
	public void cancelCoachToProject(Teacher teacher, Project project) {
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRole roleSeached;
		pk.setProjectId(project.getId());
		pk.setTeacherId(teacher.getId());
		roleSeached= (TeacherRole) roleServ.retrieve(pk, "PK");
			roleServ.delete(roleSeached);
			String content = "Mr/Mme "+teacher.getFirstName()+" "+teacher.getLastName()+" n'est plus l'encadrant du projet: "+project.getTopic()+" réalisé par :"+project.getStudent().getFirstName()+" "+project.getStudent().getLastName();
			int coachId = teacher.getId();
			msgFacade.sendcancelCoachToProject(content, project, coachId);
			//TODO à tester

	}

	@Override
	public void assignCorrectorToProject(Teacher teacher, Project project) {
		//TODO à tester
		int idReciever = teacher.getId();
		msgFacade.sendAffectCorrector(project, idReciever);
	}

	@Override
	public void cancelCorrectorToProject(Teacher teacher, Project project) {
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRole roleSeached;
		pk.setProjectId(project.getId());
		pk.setTeacherId(teacher.getId());
		roleSeached= (TeacherRole) roleServ.retrieve(pk, "PK");
		roleServ.delete(roleSeached);
		String content = "Mr/Mme "+teacher.getFirstName()+" "+teacher.getLastName()+" n'est plus le rapporteur du projet: "+project.getTopic()+" réalisé par :"+project.getStudent().getFirstName()+" "+project.getStudent().getLastName();
		int correctorId = teacher.getId();
		msgFacade.sendcancelCorrectorToProject(content, project, correctorId);
			//TODO à tester
	}

	@Override
	public List<Project> listProjectsToManage() {
		List<Project> returnList = new ArrayList<Project>();
		Project project = new Project();
		
		ValidationState nonValid = ValidationState.NONVALID;
		project.setValidationState(nonValid);
		returnList = projServ.retrieveList(project, "VS");
		ValidationState enAttente = ValidationState.WAITING;
		project.setValidationState(enAttente);
		List<Project> projListEnAttente = projServ.retrieveList(project, "VS");
		returnList.addAll(projListEnAttente); 
		ValidationState valid = ValidationState.VALID;
		project.setValidationState(valid);
		List<Project> projListValid = projServ.retrieveList(project, "VS");
		returnList.addAll(projListValid);
		
		return returnList;
		}

	@Override
	public String addProjectToDB(Project project, boolean isNewComCoach) {
		String operationResult = null;
		List<ProjectDomain> projDomList = new ArrayList<ProjectDomain>();
		projDomList = project.getProjectDomains();
		
		if(isNewComCoach){
			//create company coach
			CompanyCoach compCoach = new CompanyCoach();
			compCoach = project.getCompanycoach();
			compCoachServ.create(compCoach);
		}
		
		//create project
		projServ.create(project);
		System.out.println("id proj "+project.getId());
		System.out.println("student "+project.getStudent());

		//create cx with student
		Student student = new Student();
		student = project.getStudent();
		student.setProject(project);
		studentServ.update(student);
		
		//create connexion with domains
		for (int i = 0; i < projDomList.size(); i++) {
			
			//find domain id
			Domain domain = new Domain();
			int idDomain;
			domain.setDomainName(projDomList.get(i).getDomain().getDomainName());
			domain = (Domain) domServ.retrieve(domain, "NAME");
			idDomain = domain.getId();
			ProjectDomain projDom = new ProjectDomain();
			//create projectDom pk (domID + projID)
			ProjectDomainPK pk = new ProjectDomainPK();
			pk.setDomainId(idDomain);
			pk.setProjId(project.getId());
			projDom.setPk(pk);
			//create projDom
			projDomServ.create(projDom);
		}
		
		
	return operationResult;
	}

	@Override
	public void deleteProject(Project projectToDelete) {
		Project proj = new Project();
		proj = (Project) projServ.retrieve(projectToDelete, "ID");
		Student studentToDelete = new Student();
		studentToDelete = proj.getStudent();
		studentToDelete.setProject(null);
		studentServ.update(studentToDelete);
		System.out.println("student updated!!!");
		
		for (int i = 0; i < proj.getProjectDomains().size(); i++) {
			ProjectDomain projDom = new ProjectDomain();
			projDom = proj.getProjectDomains().get(i);
			projDomServ.delete(projDom);
		}
		
		projServ.delete(proj);
	}

}
