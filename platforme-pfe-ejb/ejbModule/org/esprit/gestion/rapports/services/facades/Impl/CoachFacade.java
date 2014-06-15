package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.AssignResponseState;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.MessageType;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.persistence.TeachingUnitDomain;
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeRemote;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;

/**
 * View (GÃ©rer rapports)=> tableau: Ã©tudiant - projet - encadrant - rapporteur
 * bouton Ã©diter encadrant: 1- listerCoachDisponibles (tel que coaching hours
 * <x?) 2- Affichage de la liste 3- sÃ©lectionner un coach =>
 * assignCoachToProject
 */
@Stateless
public class CoachFacade implements ICoachFacadeLocal, ICoachFacadeRemote {

	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;

	@Inject
	@DomainQualifier
	IServiceLocal<Domain> domainServ;

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;

	@Inject
	@TecherRoleQualifier
	IServiceLocal<TeacherRole> roleServ;

	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;

	@Inject
	@UserMessageQualifier
	IServiceLocal<UserMessage> userMsgServ;

	@EJB
	IMessageFacadeLocal msgFacade;

	@Override
	public List<Teacher> listerCoachSameDom(int coachingHoursMax,
			List<String> projectDomains) {
		List<Teacher> filtredList = new ArrayList<Teacher>();
		int nbTeacherFiltred = 0;
		List<Teacher> teacherList = new ArrayList<Teacher>();
		List<Teacher> teacherLessHours = new ArrayList<Teacher>();
		Teacher teacher = new Teacher();
		teacher.setCoachingHours(coachingHoursMax);

		// search teachears having less then coachingHoursMax
		teacherList = teacherServ.retrieveList(teacher, "ALL");
		if (teacherList.isEmpty()) {

			return null;
		}

		else {
			for (int i = 0; i < teacherList.size(); i++) {
				if (teacherList.get(i).getCoachingHours() <= coachingHoursMax) {
					teacherLessHours.add(teacherList.get(i));
				}

			}

			if (teacherLessHours.isEmpty()) {
				return null;
			} else {

				/**************** filter par teaching unit (domain coach = domain project) *********************/

				// ******parcourir la liste des teacher disponibles
				for (int teacherIt = 0; teacherIt < teacherLessHours.size(); teacherIt++) {
					Teacher t = teacherLessHours.get(teacherIt);
					// ****recuperer tous les domaines du teacher (selon son
					// unit)
					TeachingUnit teachingUnit = new TeachingUnit();
					teachingUnit = t.getTeachingUnit();

					if (teachingUnit == null) {
						filtredList.add(t);
						nbTeacherFiltred = nbTeacherFiltred + 1;
					} else {

						List<TeachingUnitDomain> teacherDomains = teachingUnit
								.getTeachingUnitDomains();

						// ****parcourir les domaine du teacher, si egale au
						// domaine
						// du
						// projet, ajouter a  la filtered list
						for (int teacherDomIt = 0; teacherDomIt < teacherDomains
								.size(); teacherDomIt++) {
							TeachingUnitDomain teachDomCx = teacherDomains
									.get(teacherDomIt);

							for (int projDomIt = 0; projDomIt < projectDomains
									.size(); projDomIt++) {

								String projDom = projectDomains.get(projDomIt);

								// chercher le nom du domaine du teacher
								Domain domt = new Domain();
								domt.setId(teachDomCx.getPk().getDomainId());
								domt = (Domain) domainServ.retrieve(domt, "ID");
								String teachDom = domt.getDomainName();

								if (projDom == teachDom) {
									filtredList.add(t);
									nbTeacherFiltred = nbTeacherFiltred + 1;
								}
							}
						}
					}
				}
				if (nbTeacherFiltred == 0) {
					return null;
				}

			}
		}
		return filtredList;

	}

	@Override
	public void CoachProjectAccept(int idCoach, int idMsg) {

		// retrieve msg
		Message msg = new Message();
		msg.setId(idMsg);
		msg = (Message) msgServ.retrieve(msg, "ID");

		// retrieve coach
		Teacher coach = new Teacher();
		coach.setId(idCoach);
		coach = (Teacher) teacherServ.retrieve(coach, "ID");

		// associer le coach au projet
		int projectId = msg.getIncludedRef();
		TeacherRole tRole = new TeacherRole();
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRoleType role = TeacherRoleType.ENCADRANT;
		pk.setProjectId(projectId);
		pk.setTeacherId(coach.getId());
		tRole.setPk(pk);
		tRole.setRole(role);

		roleServ.create(tRole);
		// retrieve project
		Project project = new Project();
		project.setId(projectId);
		project = (Project) projServ.retrieve(project, "ID");
		// get student
		Student student = project.getStudent();

		msgFacade.sendCoachAccept(projectId, coach.getId(), student.getId(),
				msg.getIdSender());

		// coachingHours + 1

		coach.setCoachingHours(coach.getCoachingHours() + 1);
		teacherServ.update(coach);

	}

	@Override
	public List<Teacher> listAllCoach(int coachingHoursMax) {

		List<Teacher> teacherList = new ArrayList<Teacher>();
		List<Teacher> teacherLessHours = new ArrayList<Teacher>();
		Teacher teacher = new Teacher();

		teacher.setCoachingHours(coachingHoursMax);

		// search teachears having less then coachingHoursMax
		teacherList = teacherServ.retrieveList(teacher, "ALL");

		if (teacherList.isEmpty()) {

			return null;
		}

		else {
			for (int i = 0; i < teacherList.size(); i++) {
				if (teacherList.get(i).getCoachingHours() <= coachingHoursMax) {
					teacherLessHours.add(teacherList.get(i));

				}

			}

			if (teacherLessHours.isEmpty()) {
				return null;
			} else
				return teacherLessHours;

		}
	}

	@Override
	public void coachDeclineAssign(int idCoach, int includedRef,
			String declineCause, int idAdmin, int idAssignMsg) {

		// update assignResponseState on msg of assign
		Message msgToUpdate = new Message();
		msgToUpdate.setId(idAssignMsg);

		msgToUpdate = (Message) msgServ.retrieve(msgToUpdate, "ID");

		msgToUpdate.setResponseState(AssignResponseState.REFUSED);
		msgToUpdate.setDeclineCause(declineCause);

		msgServ.update(msgToUpdate);

		// retrieve coach
		Teacher coach = new Teacher();
		coach.setId(idCoach);

		coach = (Teacher) teacherServ.retrieve(coach, "ID");

		// retrieve student(from project)

		Project proj = new Project();
		proj.setId(includedRef);
		proj = (Project) projServ.retrieve(proj, "ID");

		// send message to admin
		Message msg = new Message();
		msg.setContent("Bonjour, \n Mr/Mme."
				+ coach.getLastName()
				+ " "
				+ coach.getFirstName()
				+ " a décliné son affectation en tant qu'encadrant pour l'étudiant: "
				+ proj.getStudent().getLastName() + " "
				+ proj.getStudent().getFirstName()
				+ " inscrit sous le numéro: ["
				+ proj.getStudent().getRegistrationNumber()
				+ "]. La cause de la déclinaison est: '" + declineCause
				+ ". \n Ceci est un message automatique. \n Cordialement");
		msg.setDeclineCause(declineCause);
		msg.setIdReceiver(idAdmin);
		msg.setIdSender(idCoach);
		msg.setIncludedRef(proj.getId());
		Date sendingDate = new Date();
		msg.setSendingDate(sendingDate);
		msg.setSubject("Affectation déclinée");
		msg.setType(MessageType.COACHASSIGN);
		msg.setResponseState(AssignResponseState.REFUSED);

		msgServ.create(msg);

		// cx msg admin
		UserMessage cxAdmin = new UserMessage();
		UserMessagePK pkAdmin = new UserMessagePK();
		pkAdmin.setMessageId(msg.getId());
		pkAdmin.setUserId(idAdmin);

		cxAdmin.setAccess(MessageAccess.TOREAD);
		cxAdmin.setPk(pkAdmin);

		userMsgServ.create(cxAdmin);

		// cx sender
		UserMessage cxCoach = new UserMessage();
		UserMessagePK pkCoach = new UserMessagePK();
		pkCoach.setMessageId(msg.getId());
		pkCoach.setUserId(idCoach);

		cxCoach.setAccess(MessageAccess.SENT);
		cxCoach.setPk(pkCoach);

		userMsgServ.create(cxCoach);

	}

	@Override
	public List<Project> listProjectsCoached(int idCoach) {
			
		List<Project> returnList = new ArrayList<Project>();
		
		List<TeacherRole> listTeachRoles = new ArrayList<TeacherRole>();

		TeacherRole tr = new TeacherRole();
		TeacherRolePK pk = new TeacherRolePK();
		pk.setTeacherId(idCoach);
		tr.setPk(pk);
		tr.setRole(TeacherRoleType.ENCADRANT);

		listTeachRoles = roleServ
				.retrieveList(tr, "RoleAndCoach");
		
		if(listTeachRoles.isEmpty()){
			return null;
		}
		
		else{
			
			for (int i = 0; i < listTeachRoles.size(); i++) {
				Project proj = new Project();
				proj.setId(listTeachRoles.get(i).getPk().getProjectId());
				proj = (Project) projServ.retrieve(proj, "ID");
				
				returnList.add(proj);
				
			}
			

			return returnList;
		}
		
		
	}
}
