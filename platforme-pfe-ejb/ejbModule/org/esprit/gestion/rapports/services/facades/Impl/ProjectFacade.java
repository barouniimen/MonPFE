package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.AssignResponseState;
import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageType;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.persistence.ProjectDomainPK;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.AdminQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.CompanyCoachQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectDomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeRemote;
import org.esprit.gestion.rapports.utils.AssignState;

@Stateless
public class ProjectFacade implements IProjectFacadeLocal, IProjectFacadeRemote {

	@Inject
	@UserQualifier
	IServiceLocal<User> userServ;

	@Inject
	@TecherRoleQualifier
	IServiceLocal<TeacherRole> teachRoleServ;

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

	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;

	@EJB
	IMessageFacadeLocal msgFacade;

	@Inject
	@DomainQualifier
	IServiceLocal<Domain> domServ;

	@Inject
	@AdminQualifier
	IServiceLocal<Administrator> adminServ;

	@Override
	public void assignCoachToProject(Teacher teacher, int iDproject, User sender) {

		int idReciever = teacher.getId();

		msgFacade.sendAffectCoach(iDproject, idReciever, sender.getId());
	}

	@Override
	public void cancelCoachToProject(Teacher teacher, Project project,
			int senderId) {

		int coachId = teacher.getId();

		teacher = (Teacher) teacherServ.retrieve(teacher, "ID");
		project = (Project) projServ.retrieve(project, "ID");

		TeacherRolePK pk = new TeacherRolePK();
		TeacherRole roleSeached = new TeacherRole();
		pk.setProjectId(project.getId());
		pk.setTeacherId(teacher.getId());
		roleSeached.setPk(pk);

		roleSeached = (TeacherRole) roleServ.retrieve(pk, "PK");

		if (roleSeached != null) {
			roleServ.delete(roleSeached);
		}

		msgFacade.sendcancelCoachToProject(project, coachId, senderId);

		AssignState affStates = new AssignState();
		affStates = findCoachAssignement(project.getId());

		Message msg = new Message();

		AssignResponseState wainting = AssignResponseState.WAITING;
		AssignResponseState accepted = AssignResponseState.ACCEPTED;

		if (affStates.getResponseState().equals(wainting)
				|| affStates.getResponseState().equals(accepted)) {

			msg.setId(affStates.getIdMsg());
			msg = (Message) msgServ.retrieve(msg, "ID");
			AssignResponseState aff = AssignResponseState.CANCELED;
			msg.setResponseState(aff);
			msgServ.update(msg);

		}

	}

	@Override
	public void assignCorrectorToProject(Teacher teacher, int iDproject,
			User sender) {
		
		int idReciever = teacher.getId();
		msgFacade.sendAffectCorrector(iDproject, idReciever, sender.getId());
	}

	@Override
	public List<Project> listProjectsInProcess() {
		List<Project> returnList = new ArrayList<Project>();
		Project project = new Project();

		ValidationState valid = ValidationState.VALID;
		project.setValidationState(valid);

		returnList = projServ.retrieveList(project, "VS");

		ValidationState enAttenteDepot = ValidationState.WAITINGDEPO;
		project.setValidationState(enAttenteDepot);
		List<Project> projListEnAttente = projServ.retrieveList(project, "VS");

		returnList.addAll(projListEnAttente);

		return returnList;
	}

	@Override
	public String addProjectToDB(Project project, boolean isNewComCoach) {
		String operationResult = null;
		List<ProjectDomain> projDomList = new ArrayList<ProjectDomain>();
		projDomList = project.getProjectDomains();

		if (isNewComCoach) {
			// create company coach
			CompanyCoach compCoach = new CompanyCoach();
			compCoach = project.getCompanycoach();
			compCoachServ.create(compCoach);
		}

		// create project
		projServ.create(project);

		// create cx with student
		Student student = new Student();
		student = project.getStudent();
		student.setProject(project);
		studentServ.update(student);

		// create connexion with domains
		for (int i = 0; i < projDomList.size(); i++) {

			// find domain id
			Domain domain = new Domain();
			int idDomain;
			domain.setDomainName(projDomList.get(i).getDomain().getDomainName());
			domain = (Domain) domServ.retrieve(domain, "NAME");
			idDomain = domain.getId();
			ProjectDomain projDom = new ProjectDomain();
			// create projectDom pk (domID + projID)
			ProjectDomainPK pk = new ProjectDomainPK();
			pk.setDomainId(idDomain);
			pk.setProjId(project.getId());
			projDom.setPk(pk);
			// create projDom
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

		for (int i = 0; i < proj.getProjectDomains().size(); i++) {
			ProjectDomain projDom = new ProjectDomain();
			projDom = proj.getProjectDomains().get(i);
			projDomServ.delete(projDom);
		}

		// TODO notify coach if affected && delete affectations if there is

		projServ.delete(proj);
	}

	@Override
	public AssignState findCoachAssignement(int idProj) {

		boolean coachFound = false;

		Message msg = new Message();
		msg.setIncludedRef(idProj);

		List<Message> msgForIdProjList = new ArrayList<Message>();
		AssignState affState = new AssignState();

		msgForIdProjList = msgServ.retrieveList(msg, "idProj");

		if (msgForIdProjList.isEmpty()) {

			return null;
		}

		else {

			for (int i = 0; i < msgForIdProjList.size(); i++) {

				if (msgForIdProjList.get(i).getType()
						.equals(MessageType.COACHASSIGN)) {
					coachFound = true;

					System.out.println("coach found!!!!!!!");

					affState.setIdMsg(msgForIdProjList.get(i).getId());
					affState.setIdProj(msgForIdProjList.get(i).getIncludedRef());
					affState.setResponseState(msgForIdProjList.get(i)
							.getResponseState());
					affState.setSendingDate(msgForIdProjList.get(i)
							.getSendingDate());

					// find user kind
					User user = new User();
					user.setId(msgForIdProjList.get(i).getIdReceiver());
					user = (User) userServ.retrieve(user, "ID");

					if (user instanceof Teacher) {

						Teacher t = new Teacher();
						t.setId(user.getId());

						t = (Teacher) teacherServ.retrieve(t, "ID");

						affState.setTeacherName(t.getFirstName() + " "
								+ t.getLastName());
						affState.setIdTeacher(msgForIdProjList.get(i)
								.getIdReceiver());

					}

					else if (user instanceof Administrator) {
						affState.setTeacherName("admin");
						affState.setIdTeacher(user.getId());
					}

				}
			}

			if (coachFound) {
				return affState;
			} else {
				return null;
			}

		}

	}

	public AssignState findCorrectorAssignement(int idProj) {
		boolean correctorFound = false;

		Message msg = new Message();
		msg.setIncludedRef(idProj);

		List<Message> msgForIdProjList = new ArrayList<Message>();
		AssignState affState = new AssignState();

		msgForIdProjList = msgServ.retrieveList(msg, "idProj");

		if (msgForIdProjList.isEmpty()) {

			return null;
		}

		else {
			MessageType corrector = MessageType.CORRECTORASSIGN;

			for (int i = 0; i < msgForIdProjList.size(); i++) {

				if (msgForIdProjList.get(i).getType().equals(corrector)) {
					correctorFound = true;
					affState.setIdMsg(msgForIdProjList.get(i).getId());
					affState.setIdProj(msgForIdProjList.get(i).getIncludedRef());
					affState.setResponseState(msgForIdProjList.get(i)
							.getResponseState());
					affState.setSendingDate(msgForIdProjList.get(i)
							.getSendingDate());

					// search teacher
					Teacher t = new Teacher();
					t.setId(msgForIdProjList.get(i).getIdReceiver());
					t = (Teacher) teacherServ.retrieve(t, "ID");
					affState.setTeacherName(t.getFirstName() + " "
							+ t.getLastName());
					affState.setIdTeacher(msgForIdProjList.get(i)
							.getIdReceiver());

				}
			}

			if (correctorFound) {
				return affState;
			} else {
				return null;
			}
		}

	}

	@Override
	public void cancelCorrectorToProject(Teacher teacher, Project project,
			int senderId) {

		int correctorId = teacher.getId();

		teacher = (Teacher) teacherServ.retrieve(teacher, "ID");
		project = (Project) projServ.retrieve(project, "ID");

		TeacherRolePK pk = new TeacherRolePK();
		TeacherRole roleSeached = new TeacherRole();
		pk.setProjectId(project.getId());
		pk.setTeacherId(teacher.getId());
		roleSeached.setPk(pk);

		roleSeached = (TeacherRole) roleServ.retrieve(pk, "PK");

		if (roleSeached != null) {
			roleServ.delete(roleSeached);
		}

		AssignState affStates = new AssignState();
		affStates = findCorrectorAssignement(project.getId());

		Message msg = new Message();

		AssignResponseState wainting = AssignResponseState.WAITING;
		AssignResponseState accepted = AssignResponseState.ACCEPTED;

		if (affStates.getResponseState().equals(wainting)
				|| affStates.getResponseState().equals(accepted)) {

			msg.setId(affStates.getIdMsg());
			msg = (Message) msgServ.retrieve(msg, "ID");
			AssignResponseState aff = AssignResponseState.CANCELED;
			msg.setResponseState(aff);
			msgServ.update(msg);

		}

		msgFacade.sendcancelCorrectorToProject(project, correctorId, senderId);
	}

	@Override
	public List<TeacherRole> findTeacherRolesToProj(int idProj) {
		List<TeacherRole> returnList = new ArrayList<TeacherRole>();
		TeacherRole tr = new TeacherRole();
		TeacherRolePK pk = new TeacherRolePK();
		pk.setProjectId(idProj);
		tr.setPk(pk);

		returnList = teachRoleServ.retrieveList(tr, "projId");

		if (returnList.isEmpty()) {

			return null;
		}

		else {
			return returnList;
		}
	}

	@Override
	public List<Project> listProjectsDeposed() {
		List<Project> returnList = new ArrayList<Project>();
		Project project = new Project();

		
		project.setValidationState(ValidationState.DEPOSED);

		returnList = projServ.retrieveList(project, "VS");

		return returnList;
	}

	@Override
	public List<Project> listProjectsWaitingSout() {
		List<Project> returnList = new ArrayList<Project>();
		Project project = new Project();

		
		project.setValidationState(ValidationState.WAITINGSOUT);

		returnList = projServ.retrieveList(project, "VS");

		return returnList;
	}

	@Override
	public List<Project> listProjectsSoutResult() {
		List<Project> returnList = new ArrayList<Project>();
		Project project = new Project();

		
		project.setValidationState(ValidationState.SOUTVALID);

		returnList = projServ.retrieveList(project, "VS");
		
		
		project.setValidationState(ValidationState.SOUTNONVALID);
		List<Project> projList = projServ.retrieveList(project, "VS");

		returnList.addAll(projList);

		return returnList;
	}
}
