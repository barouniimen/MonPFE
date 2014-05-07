package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
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

	@EJB
	IMessageFacadeLocal msgFacade;

	
	@Override
	public void assignCoachToProject(Teacher teacher, Project project) {
		int idReciever = teacher.getId();
		msgFacade.sendAffectCoach( project,idReciever);
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
	public String addProjectToDB(Project project, Student student) {
		String operationResult = null;
		
		//add project
		System.out.println("to add proj facade");
		System.out.println("project: \n topic: "+project.getTopic());
	//	System.out.println("validaiton satate: "+project.getValidationState());
		System.out.println("startdate: "+project.getStartDate());
	//	System.out.println("acad year: "+project.getAcademicYear());
		System.out.println("domain 0: "+project.getProjectDomains().get(0));
		
		System.out.println("student ");
		System.out.println("studnet name: "+student.getLastName());
		System.out.println("first name: "+student.getFirstName());
		
		
		//add connection to student
		
	
	return operationResult;
	}

}
