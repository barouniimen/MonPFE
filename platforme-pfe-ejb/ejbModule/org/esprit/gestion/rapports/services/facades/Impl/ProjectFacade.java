package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.AssignResponseState;
import org.esprit.gestion.rapports.persistence.Comments;
import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.MessageType;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.persistence.ProjectDomainPK;
import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.ReportKeyWord;
import org.esprit.gestion.rapports.persistence.ReportKeyWordPk;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.AdminQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.CommentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.CompanyCoachQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectDomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportKeyWordQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
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
	@CommentQualifier
	IServiceLocal<Comments> commServ;

	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;

	@Inject
	@CompanyCoachQualifier
	IServiceLocal<CompanyCoach> compCoachServ;

	@Inject
	@ReportKeyWordQualifier
	IServiceLocal<ReportKeyWord> reportKeyWordServ;

	@Inject
	@ReportQualifier
	IServiceLocal<Report> reportServ;

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

	@Inject
	@UserMessageQualifier
	IServiceLocal<UserMessage> userMsgServ;

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

		System.out.println("update student done!!!!!!!!!!!!!!!!!!");

		for (int i = 0; i < proj.getProjectDomains().size(); i++) {
			ProjectDomain projDom = new ProjectDomain();
			projDom = proj.getProjectDomains().get(i);
			projDomServ.delete(projDom);
		}

		List<TeacherRole> listTeacherRole = new ArrayList<TeacherRole>();
		TeacherRole role = new TeacherRole();

		role.setProject(proj);
		listTeacherRole = teachRoleServ.retrieveList(role, "proj");

		if (!(listTeacherRole.isEmpty())) {
			for (int comptTrole = 0; comptTrole < listTeacherRole.size(); comptTrole++) {

				// notify teacher
				Message msg = new Message();
				msg.setContent("Bonjour,\n\n Le projet: "
						+ proj.getTopic()
						+ "affecté à l'étudiant(e): "
						+ studentToDelete.getLastName()
						+ " "
						+ studentToDelete.getFirstName()
						+ " a été supprimé de la plateforme.\n\n Cordialement\n\n Direction Des Dtages et PFE");
				msg.setIdReceiver(listTeacherRole.get(comptTrole).getPk()
						.getTeacherId());
				msg.setIdSender(-1);
				Date sendingDate = new Date();
				msg.setSendingDate(sendingDate);
				msg.setSubject("Suppression de projet");
				msg.setType(MessageType.ByADMIN);

				msgServ.create(msg);

				UserMessagePK pk = new UserMessagePK();
				pk.setMessageId(msg.getId());
				pk.setUserId(listTeacherRole.get(comptTrole).getPk()
						.getTeacherId());

				UserMessage cx = new UserMessage();
				cx.setAccess(MessageAccess.TOREAD);
				cx.setPk(pk);

				userMsgServ.create(cx);

				// delete
				teachRoleServ.delete(listTeacherRole.get(comptTrole));
			}
		}

		List<Report> listReport = new ArrayList<Report>();
		listReport = proj.getReports();

		if (!(listReport.isEmpty())) {

			for (int comptReport = 0; comptReport < listReport.size(); comptReport++) {

				ReportKeyWordPk pk = new ReportKeyWordPk();

				pk.setReportId(listReport.get(comptReport).getId());

				ReportKeyWord repKw = new ReportKeyWord();
				repKw.setPk(pk);

				List<ReportKeyWord> listRepKw = new ArrayList<ReportKeyWord>();
				listRepKw = reportKeyWordServ.retrieveList(repKw, "repID");

				if (!(listRepKw.isEmpty())) {

					for (int comptKeyW = 0; comptKeyW < listRepKw.size(); comptKeyW++) {
						reportKeyWordServ.delete(listRepKw.get(comptKeyW));
					}

				}

				List<Comments> listComment = new ArrayList<Comments>();
				Comments comment = new Comments();
				comment.setReport(listReport.get(comptReport));

				listComment = commServ.retrieveList(comment, "report");

				if (!(listComment.isEmpty())) {
					for (int comptComment = 0; comptComment < listComment
							.size(); comptComment++) {
						commServ.delete(listComment.get(comptComment));
					}
				}

				listComment = commServ.retrieveList(comment, "report");

				reportServ.delete(listReport.get(comptReport));
			}
		}
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

	@Override
	public boolean allawdedPeriodToSubmit(int idStudent, int period) {

		Student st = new Student();
		st.setId(idStudent);

		st = (Student) studentServ.retrieve(st, "ID");

		Project proj = new Project();
		proj = st.getProject();

		if (proj != null) {
			Date currentDate = new Date();

			long diff = currentDate.getTime() - proj.getStartDate().getTime();

			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

			int diffMonth = diffDays / 30;

			if (diffMonth > period) {
				return true;
			}

			else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public List<Project> listProjCoached(int idCoach) {
		List<Project> listProj = new ArrayList<Project>();

		List<TeacherRole> listrolesCoach = new ArrayList<TeacherRole>();
		TeacherRole tr = new TeacherRole();
		tr.setRole(TeacherRoleType.ENCADRANT);
		TeacherRolePK pk = new TeacherRolePK();
		pk.setTeacherId(idCoach);
		tr.setPk(pk);
		listrolesCoach = teachRoleServ.retrieveList(tr, "RoleAndCoach");

		for (int i = 0; i < listrolesCoach.size(); i++) {
			Project proj = new Project();
			proj.setId(listrolesCoach.get(i).getPk().getProjectId());
			proj = (Project) projServ.retrieve(proj, "ID");
			listProj.add(proj);
		}

		return listProj;
	}

	@Override
	public List<Project> listProjCorrector(int idCoach) {
		List<Project> listProj = new ArrayList<Project>();

		List<TeacherRole> listrolesCoach = new ArrayList<TeacherRole>();
		TeacherRole tr = new TeacherRole();
		tr.setRole(TeacherRoleType.RAPPORTEUR);
		TeacherRolePK pk = new TeacherRolePK();
		pk.setTeacherId(idCoach);
		tr.setPk(pk);
		listrolesCoach = teachRoleServ.retrieveList(tr, "RoleAndCoach");

		for (int i = 0; i < listrolesCoach.size(); i++) {
			Project proj = new Project();
			proj.setId(listrolesCoach.get(i).getPk().getProjectId());
			proj = (Project) projServ.retrieve(proj, "ID");
			listProj.add(proj);
		}

		return listProj;
	}
}
