package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.AssignResponseState;
import org.esprit.gestion.rapports.persistence.Company;
import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.MessageType;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.AdminQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeRemote;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.utils.AssignState;
import org.esprit.gestion.rapports.utils.MessageStats;

@Stateless
public class MessageFacade implements IMessageFacadeLocal, IMessageFacadeRemote {

	@EJB
	IProjectFacadeLocal projFacade;

	@Inject
	@UserQualifier
	IServiceLocal<User> userServ;

	@Inject
	@AdminQualifier
	IServiceLocal<Administrator> adminServ;

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;

	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;

	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;

	@Inject
	@UserMessageQualifier
	IServiceLocal<UserMessage> userMsgServ;

	@Inject
	@TecherRoleQualifier
	IServiceLocal<TeacherRole> teacherRoleServ;

	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;

	@Override
	public void send(String content, String subject, int idSender,
			int idReciever) {

		MessageType type = null;

		// rechercher sender
		if (idSender == 0) {
			type = MessageType.ByVISITOR;
		}

		else {
			User sender = new User();
			sender.setId(idSender);
			sender = (User) userServ.retrieve(sender, "ID");
			if (sender instanceof Administrator) {
				type = MessageType.ByADMIN;
			} else if (sender instanceof Teacher) {

				type = MessageType.ByTEACHER;
			} else if (sender instanceof Student) {

				type = MessageType.BySTUDENT;
			}
		}

		// initialiser message
		Date sendingDate = new Date();
		Message message = new Message(0, subject, content, idReciever,
				idSender, sendingDate, type, null, null);

		// persister message
		msgServ.create(message);

		// rÃ©cupÃ©rer id message
		msgServ.retrieve(message, "CONTENT");

		// initialiser userMessage connexion reciever
		UserMessage recieverMsg = new UserMessage();
		UserMessagePK pk = new UserMessagePK();
		pk.setMessageId(message.getId());
		pk.setUserId(idReciever);
		MessageAccess accessReciever = MessageAccess.TOREAD;
		recieverMsg.setAccess(accessReciever);
		recieverMsg.setPk(pk);
		// persister userMessage connexion
		userMsgServ.create(recieverMsg);

		// initialiser userMessage connexion sender
		UserMessage senderMsg = new UserMessage();
		UserMessagePK pkSender = new UserMessagePK();
		pkSender.setMessageId(message.getId());
		pkSender.setUserId(idReciever);
		MessageAccess accessSender = MessageAccess.SENT;
		senderMsg.setAccess(accessSender);
		senderMsg.setPk(pkSender);
		// persister userMessage connexion
		userMsgServ.create(senderMsg);
	}

	public void sendAffectCoach(int iDproject, int idReciever, int idSender) {

		// retrieve project
		Project project = new Project();
		project.setId(iDproject);
		project = (Project) projServ.retrieve(project, "ID");

		Student student = project.getStudent();

		String topic = project.getTopic();

		String studentName = student.getFirstName() + " "
				+ student.getLastName();

		CompanyCoach compcoach = project.getCompanycoach();

		String content = null;

		if (compcoach != null) {

			if (compcoach.getCompany() != null) {
				Company comp = compcoach.getCompany();

				String compName = comp.getName();

				content = "Bonjour,\n Vous avez \u00E9t\u00E9 affect\u00E9 comme encadrant au projet intitul\u00E9 ["
						+ topic
						+ "] r\u00E9alis\u00E9 par ["
						+ studentName
						+ "] pour le compte de ["
						+ compName
						+ "]. \n\n\n Cordialement \n Direction des stages et PFE";

			}

		}

		else {
			content = "Bonjour, \n Vous avez \u00E9t\u00E9 affect\u00E9 comme encadrant au projet intitul\u00E9: "
					+ topic
					+ " r\u00E9alis\u00E9 par : ["
					+ studentName
					+ "]. \n\n\n Cordialement \n Direction des Stages et PFE";
		}

		// Rechercher le nom du reciever (Teacher)
		Teacher coachSearched = new Teacher();
		coachSearched.setId(idReciever);
		coachSearched = (Teacher) teacherServ.retrieve(coachSearched, "ID");

		// initialisation du message
		Message message = new Message();
		message.setContent(content);
		MessageType type = MessageType.COACHASSIGN;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Affectation en encadrement");
		message.setIncludedRef(project.getId());
		AssignResponseState responseState = AssignResponseState.WAITING;
		message.setResponseState(responseState);
		message.setIdReceiver(idReciever);
		message.setIdSender(idSender);

		// Persistence: Message
		msgServ.create(message);

		// initialisation: UserMessage (liaison avec coach)
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(idReciever);
		pk.setMessageId(message.getId());
		MessageAccess access = MessageAccess.TOREAD;
		UserMessage userMsg = new UserMessage(pk, access);

		// Persistence: UserMessage
		userMsgServ.create(userMsg);

		// initialisation: UserMessage (liaison avec admin)
		UserMessagePK pkAdmin = new UserMessagePK();
		pkAdmin.setUserId(idSender);
		pkAdmin.setMessageId(message.getId());
		MessageAccess accessAdmin = MessageAccess.SENT;
		UserMessage adminMsg = new UserMessage(pkAdmin, accessAdmin);

		// Persistence: UserMessage
		userMsgServ.create(adminMsg);

	}

	@Override
	public void sendcancelCoachToProject(Project project, int coachId,
			int senderId) {

		/********************** Notifier student ***********************************/
		// initialiser le message

		Date sendingDate = new Date();
		MessageType type = MessageType.ByADMIN;

		// Find coach
		Teacher t = new Teacher();
		t.setId(coachId);
		t = (Teacher) teacherServ.retrieve(t, "ID");

		// find project
		project = (Project) projServ.retrieve(project, "ID");

		/***************************** Notify COACH ************************************/

		String contentCoach = "Bonjour (Mr/Mme)."
				+ t.getLastName()
				+ " "
				+ t.getFirstName()
				+ ", \n Votre affectation en tant qu'encadrant pour le projet intitulé "
				+ "["
				+ project.getTopic()
				+ "]"
				+ " réalisé par "
				+ "["
				+ project.getStudent().getLastName()
				+ " "
				+ project.getStudent().getFirstName()
				+ "], a été annulée. \n\n\n Cordialement Direction des Stages et PFE";
		Message msgCoach = new Message(project.getId(),
				"Affectation en encadrement annul\u00E9e", contentCoach,
				coachId, senderId, sendingDate, type, null, null);

		// persister message
		msgServ.create(msgCoach);

		// create connexion userMessage (student)
		UserMessage coachMsg = new UserMessage();
		UserMessagePK pkCoach = new UserMessagePK();
		pkCoach.setMessageId(msgCoach.getId());
		pkCoach.setUserId(project.getStudent().getId());
		coachMsg.setPk(pkCoach);
		MessageAccess access = MessageAccess.TOREAD;
		coachMsg.setAccess(access);

		// persister userMessage (student)
		userMsgServ.create(coachMsg);

		/***************************** Notify STUDENT ************************************/

		// Find all msg with included ref proj ID
		AssignState affStates;
		affStates = new AssignState();
		affStates = projFacade.findCoachAssignement(project.getId());
		AssignResponseState accepted = AssignResponseState.ACCEPTED;

		if (affStates != null && affStates.getResponseState().equals(accepted)) {

			// notify student
			String content = "Bonjour, \n(Mr/Mme)."
					+ t.getLastName()
					+ " "
					+ t.getFirstName()
					+ " n'est plus votre encadrant. Un autre encadrant vous sera affecté prochainement. \n\n\n Cordialement \n Direction des stages et PFE";
			Message msg = new Message(project.getId(),
					"Affectation d'encadrant annul\u00E9e", content, project
							.getStudent().getId(), senderId, sendingDate, type,
					null, null);

			// persister message
			msgServ.create(msg);

			// create connexion userMessage (student)
			UserMessage studentMsg = new UserMessage();
			UserMessagePK pkSt = new UserMessagePK();
			pkSt.setMessageId(msg.getId());
			pkSt.setUserId(project.getStudent().getId());
			studentMsg.setPk(pkSt);
			MessageAccess accessCoach = MessageAccess.TOREAD;
			studentMsg.setAccess(accessCoach);

			// persister userMessage (student)
			userMsgServ.create(studentMsg);

		}

	}

	@Override
	public void sendCoachAccept(int idProject, int idCoach, int idStudent,
			int idAdmin) {

		// retrieve coach
		Teacher coach = new Teacher();
		coach.setId(idCoach);
		coach = (Teacher) teacherServ.retrieve(coach, "ID");

		// Send message to student
		Student studentSearched = new Student();
		studentSearched.setId(idStudent);
		studentSearched = (Student) studentServ.retrieve(studentSearched, "ID");

		// init message
		Message message = new Message();

		String contentStudent = "Bonjour,\n Votre encadrant est M/Mme: ["
				+ coach.getLastName() + " " + coach.getFirstName()
				+ "] .\n\n\n Cordialement\n Direction des Stages et PFE ";
		message.setContent(contentStudent);

		MessageType type = MessageType.ByADMIN;
		message.setType(type);

		Date calendar = new Date();
		message.setSendingDate(calendar);

		message.setSubject("Affectation d'encadrant");
		message.setIncludedRef(idProject);
		message.setIdReceiver(idStudent);
		message.setIdSender(idAdmin);

		AssignResponseState responseState = AssignResponseState.ACCEPTED;
		message.setResponseState(responseState);

		// Persist Message
		msgServ.create(message);

		// init connection UserMessage (student)
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(idStudent);
		pk.setMessageId(message.getId());
		MessageAccess access = MessageAccess.TOREAD;
		UserMessage userMsg = new UserMessage(pk, access);

		// Persist UserMessage
		userMsgServ.create(userMsg);

		// init UserMessage (admin)
		UserMessagePK pksender = new UserMessagePK();
		pksender.setUserId(idAdmin);
		pksender.setMessageId(message.getId());
		MessageAccess accessSender = MessageAccess.SENT;
		UserMessage userMsgSender = new UserMessage(pksender, accessSender);

		// Persist UserMessage sender
		userMsgServ.create(userMsgSender);

		// update response state
		Message msgToUpdate = new Message();
		msgToUpdate.setIdReceiver(idCoach);
		msgToUpdate.setType(MessageType.COACHASSIGN);
		msgToUpdate.setResponseState(AssignResponseState.WAITING);
		msgToUpdate.setIncludedRef(idProject);

		msgToUpdate = (Message) msgServ.retrieve(msgToUpdate, "ToChangeState");

		if (msgToUpdate != null) {
			msgToUpdate.setResponseState(AssignResponseState.ACCEPTED);
			msgServ.update(msgToUpdate);
		}

	}

	@Override
	public void sendAffectCorrector(int iDproject, int idReciever, int idSender) {

		// retrieve project
		Project project = new Project();
		project.setId(iDproject);
		project = (Project) projServ.retrieve(project, "ID");

		Student student = project.getStudent();

		String topic = project.getTopic();

		String studentName = student.getFirstName() + " "
				+ student.getLastName();

		CompanyCoach compcoach = project.getCompanycoach();

		String content = null;

		if (compcoach != null) {

			if (compcoach.getCompany() != null) {
				Company comp = compcoach.getCompany();

				String compName = comp.getName();

				content = "Bonjour,\n Vous avez \u00E9t\u00E9 affect\u00E9 comme rapporteur au projet intitul\u00E9 ["
						+ topic
						+ "] r\u00E9alis\u00E9 par ["
						+ studentName
						+ "] pour le compte de ["
						+ compName
						+ "]. \n\n\n Cordialement \n Direction des Stages et PFE";

			}

		}

		else {
			content = "Bonjour,\n Vous avez \u00E9t\u00E9 affect\u00E9 comme rapporteur au projet intitul\u00E9: ["
					+ topic
					+ "],  r\u00E9alis\u00E9 par : ["
					+ studentName
					+ "].\n\n\n Cordialement \n Direction des stages et PFE";
		}

		// Rechercher le nom du reciever (Teacher)
		Teacher coachSearched = new Teacher();
		coachSearched.setId(idReciever);
		coachSearched = (Teacher) teacherServ.retrieve(coachSearched, "ID");

		// initialisation du message
		Message message = new Message();
		message.setContent(content);
		MessageType type = MessageType.CORRECTORASSIGN;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Affectation en tant que rapporteur");
		message.setIncludedRef(project.getId());
		AssignResponseState responseState = AssignResponseState.WAITING;
		message.setResponseState(responseState);
		message.setIdReceiver(idReciever);
		message.setIdSender(idSender);

		// Persistence: Message
		msgServ.create(message);

		// initialisation: UserMessage (liaison avec corrector)
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(idReciever);
		pk.setMessageId(message.getId());
		MessageAccess access = MessageAccess.TOREAD;
		UserMessage userMsg = new UserMessage(pk, access);

		// Persistence: UserMessage
		userMsgServ.create(userMsg);

		// initialisation: UserMessage (liaison avec admin)
		UserMessagePK pkAdmin = new UserMessagePK();
		pkAdmin.setUserId(idSender);
		pkAdmin.setMessageId(message.getId());
		MessageAccess accessAdmin = MessageAccess.SENT;
		UserMessage adminMsg = new UserMessage(pkAdmin, accessAdmin);

		// Persistence: UserMessage
		userMsgServ.create(adminMsg);

	}

	@Override
	public void sendCorrectorAccept(int idProject, int idCorrector,
			int idStudent, int idAdmin) {
		// retrieve corrector
		Teacher corrector = new Teacher();
		corrector.setId(idCorrector);
		corrector = (Teacher) teacherServ.retrieve(corrector, "ID");

		// Send message to student
		Student studentSearched = new Student();
		studentSearched.setId(idStudent);
		studentSearched = (Student) studentServ.retrieve(studentSearched, "ID");

		// init message
		Message message = new Message();

		String contentStudent = "Bonjour,\n Votre rapporteur est M/Mme: ["
				+ corrector.getLastName() + " " + corrector.getFirstName()
				+ "] .\n\n\n Cordialement\n Direction des Stages et PFE ";
		message.setContent(contentStudent);

		MessageType type = MessageType.ByADMIN;
		message.setType(type);

		Date calendar = new Date();
		message.setSendingDate(calendar);

		message.setSubject("Affectation de rapporteur");
		message.setIncludedRef(idProject);
		message.setIdReceiver(idStudent);
		message.setIdSender(idAdmin);

		AssignResponseState responseState = AssignResponseState.ACCEPTED;
		message.setResponseState(responseState);

		// Persist Message
		msgServ.create(message);

		// init connection UserMessage (student)
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(idStudent);
		pk.setMessageId(message.getId());
		MessageAccess access = MessageAccess.TOREAD;
		UserMessage userMsg = new UserMessage(pk, access);

		// Persist UserMessage
		userMsgServ.create(userMsg);

		// init UserMessage (admin)
		UserMessagePK pksender = new UserMessagePK();
		pksender.setUserId(idAdmin);
		pksender.setMessageId(message.getId());
		MessageAccess accessSender = MessageAccess.SENT;
		UserMessage userMsgSender = new UserMessage(pksender, accessSender);

		// Persist UserMessage sender
		userMsgServ.create(userMsgSender);

	}

	@Override
	public void sendAffectPresidentJury(Project project, int idReciever,
			Date soutenanceDate) {

		Student student = project.getStudent();
		CompanyCoach compcoach = project.getCompanycoach();
		Company comp = compcoach.getCompany();
		String topic = project.getTopic();
		String studentName = student.getFirstName() + " "
				+ student.getLastName();
		String compName = comp.getName();
		String content = ("Vous avez \u00E9t\u00E9 affect\u00E9 comme pr\u00E9sident du jury du projet intitul\u00E9: "
				+ topic
				+ " r\u00E9alis\u00E9 par :"
				+ studentName
				+ " pour le compte de :"
				+ compName
				+ ". La soutenance aura lieu le: " + soutenanceDate + ". Un message vous sera envoy\u00E9 ult\u00E9rieurement pour confirmer la date et le lieu de la soutenance.")
				.toString();

		// Rechercher le nom du reciever (Teacher)
		Teacher pJurySearched = new Teacher();
		pJurySearched.setId(idReciever);
		pJurySearched = (Teacher) teacherServ.retrieve(pJurySearched, "ID");

		// initialisation du message
		Message message = new Message();
		message.setContent(content);
		MessageType type = MessageType.PJURYASSIGN;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Affectation pour pr\u00E9sider un jury de soutenance PFE");
		message.setIncludedRef(project.getId());

		// Persistence: Message
		msgServ.create(message);

		// rÃ©cupÃ©rer l'id
		message = (Message) msgServ.retrieve(message, "CONTENT");

		// initialisation: UserMessage (liaison avec user)
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(idReciever);
		pk.setMessageId(message.getId());
		MessageAccess access = MessageAccess.TOREAD;
		UserMessage userMsg = new UserMessage(pk, access);

		// Persistence: UserMessage
		userMsgServ.create(userMsg);

	}

	@Override
	public void sendcancelCorrectorToProject(Project project, int correctorId,
			int senderId) {
		/********************** Notifier student ***********************************/
		// initialiser le message

		Date sendingDate = new Date();
		MessageType type = MessageType.ByADMIN;

		// Find coach
		Teacher t = new Teacher();
		t.setId(correctorId);
		t = (Teacher) teacherServ.retrieve(t, "ID");

		// find project
		project = (Project) projServ.retrieve(project, "ID");

		/***************************** Notify CORRECTOR ************************************/

		String contentCorrector = "Bonjour (Mr/Mme)."
				+ t.getLastName()
				+ " "
				+ t.getFirstName()
				+ ", \n Votre affectation en tant que rapporteur pour le projet intitulé "
				+ "["
				+ project.getTopic()
				+ "]"
				+ " réalisé par "
				+ "["
				+ project.getStudent().getLastName()
				+ " "
				+ project.getStudent().getFirstName()
				+ "], a été annulée. \n\n\n Cordialement \n Direction des Stages et PFE";
		Message msgCorrector = new Message(project.getId(),
				"Affectation en tant que rapporteur annul\u00E9e",
				contentCorrector, correctorId, senderId, sendingDate, type,
				null, null);

		// persister message
		msgServ.create(msgCorrector);

		// create connexion userMessage (student)
		UserMessage correctorMsg = new UserMessage();
		UserMessagePK pkCorrector = new UserMessagePK();
		pkCorrector.setMessageId(msgCorrector.getId());
		pkCorrector.setUserId(project.getStudent().getId());
		correctorMsg.setPk(pkCorrector);
		MessageAccess access = MessageAccess.TOREAD;
		correctorMsg.setAccess(access);

		// persister userMessage (student)
		userMsgServ.create(correctorMsg);

	}

	@Override
	public List<Message> listMsgToRead(int idUser) {
		MessageAccess toRead = MessageAccess.TOREAD;

		List<Message> listMsg = new ArrayList<Message>();
		List<Message> listMsgToRead = new ArrayList<Message>();
		Message msg = new Message();
		msg.setIdReceiver(idUser);

		listMsg = msgServ.retrieveList(msg, "idRec");

		for (int i = 0; i < listMsg.size(); i++) {
			UserMessage cxMsg = new UserMessage();
			UserMessagePK pk = new UserMessagePK();

			pk.setUserId(idUser);
			pk.setMessageId(listMsg.get(i).getId());

			cxMsg.setPk(pk);

			cxMsg = (UserMessage) userMsgServ.retrieve(cxMsg, "PK");

			if (cxMsg != null && cxMsg.getAccess().equals(toRead)) {
				listMsgToRead.add(listMsg.get(i));
			}
		}

		return listMsgToRead;

	}

	@Override
	public List<Message> listMsgSeen(int idUser) {
		MessageAccess seen = MessageAccess.SEEN;

		List<Message> listMsg = new ArrayList<Message>();
		List<Message> listMsgSeen = new ArrayList<Message>();
		Message msg = new Message();
		msg.setIdReceiver(idUser);

		listMsg = msgServ.retrieveList(msg, "idRec");

		for (int i = 0; i < listMsg.size(); i++) {
			UserMessage cxMsg = new UserMessage();
			UserMessagePK pk = new UserMessagePK();

			pk.setUserId(idUser);
			pk.setMessageId(listMsg.get(i).getId());

			cxMsg.setPk(pk);

			cxMsg = (UserMessage) userMsgServ.retrieve(cxMsg, "PK");

			if (cxMsg != null && cxMsg.getAccess().equals(seen)) {
				listMsgSeen.add(listMsg.get(i));
			}
		}

		return listMsgSeen;

	}

	@Override
	public List<Message> listMsgSent(int idUser) {
		MessageAccess sent = MessageAccess.SENT;

		List<Message> listMsg = new ArrayList<Message>();
		List<Message> listMsgSent = new ArrayList<Message>();
		Message msg = new Message();
		msg.setIdSender(idUser);

		listMsg = msgServ.retrieveList(msg, "idSender");

		for (int i = 0; i < listMsg.size(); i++) {

			UserMessage cxMsg = new UserMessage();
			UserMessagePK pk = new UserMessagePK();
			pk.setUserId(idUser);
			pk.setMessageId(listMsg.get(i).getId());
			cxMsg.setPk(pk);

			cxMsg = (UserMessage) userMsgServ.retrieve(cxMsg, "PK");
			if (cxMsg != null && cxMsg.getAccess().equals(sent)) {
				listMsgSent.add(listMsg.get(i));
			}
		}

		return listMsgSent;
	}

	@Override
	public List<Message> listMsgByType(int idUser, MessageType type) {
		List<Message> listMsg = new ArrayList<Message>();
		List<Message> listMsgByType = new ArrayList<Message>();
		Message msg = new Message();
		msg.setIdReceiver(idUser);

		listMsg = msgServ.retrieveList(msg, "idRec");

		for (int i = 0; i < listMsg.size(); i++) {
			if (listMsg.get(i).getType().equals(type)) {
				listMsgByType.add(listMsg.get(i));
			}
		}

		return listMsgByType;
	}

	@Override
	public MessageStats listNbrMsg(int idUser) {

		// init
		int nbrToRead = 0, nbrSeen = 0, nbrSent = 0, nbrAssignCoach = 0, nbrAssignCorrector = 0;

		MessageAccess toRead = MessageAccess.TOREAD;
		MessageAccess sent = MessageAccess.SENT;
		MessageAccess seen = MessageAccess.SEEN;

		MessageType assignCoach = MessageType.COACHASSIGN;
		MessageType assignCorrector = MessageType.CORRECTORASSIGN;

		List<UserMessage> cxMsgUserLsit = new ArrayList<UserMessage>();

		UserMessage usrMsg = new UserMessage();
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(idUser);
		usrMsg.setPk(pk);

		cxMsgUserLsit = userMsgServ.retrieveList(usrMsg, "userId");

		if (cxMsgUserLsit.isEmpty()) {
			MessageStats stats = new MessageStats(0, 0, 0, 0, 0);
			return stats;
		} else {

			for (int i = 0; i < cxMsgUserLsit.size(); i++) {
				if (cxMsgUserLsit.get(i).getAccess().equals(seen)) {
					nbrSeen = nbrSeen + 1;
				} else if (cxMsgUserLsit.get(i).getAccess().equals(sent)) {
					nbrSent = nbrSent + 1;

				} else if (cxMsgUserLsit.get(i).getAccess().equals(toRead)) {
					nbrToRead = nbrToRead + 1;
				}

				Message msg = new Message();
				msg.setId(cxMsgUserLsit.get(i).getPk().getMessageId());
				msg = (Message) msgServ.retrieve(msg, "ID");
				if (msg.getType().equals(assignCorrector)) {
					nbrAssignCorrector = nbrAssignCorrector + 1;
				}

				else if (msg.getType().equals(assignCoach)) {
					nbrAssignCoach = nbrAssignCoach + 1;
				}
			}

			MessageStats stats = new MessageStats(nbrToRead, nbrSeen, nbrSent,
					nbrAssignCoach, nbrAssignCorrector);
			return stats;
		}
	}

	@Override
	public void changeAccess(int msgId, int userId, MessageAccess access) {

		System.out.println("enter change access ");
		System.out.println("id user " + userId);
		System.out.println("id msg " + msgId);

		UserMessage useMsg = new UserMessage();
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(userId);
		pk.setMessageId(msgId);
		useMsg.setPk(pk);
		useMsg.setAccess(access);
		userMsgServ.update(useMsg);

	}

	@Override
	public void sendAskNewKeyWord(String newCategory, String newKeyWord,
			int idSender, Student sender) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendAskNewKeyWord(String newCategory, String newKeyWord,
			Student sender) {
		sender = (Student) studentServ.retrieve(sender, "ID");
		// init msg
		Message msg = new Message();
		msg.setContent("Bonjour,\n\n L'étudiant(e) "
				+ sender.getLastName()
				+ " "
				+ sender.getFirstName()
				+ " souhaite ajouter un nouveau mot clé pour son rapport.\n\n Il/Elle propose le mot clé: "
				+ newKeyWord + " sous la catégorie: " + newCategory
				+ ".\n\n Cordialement");
		msg.setIdSender(sender.getId());
		msg.setIncludedRef(sender.getProject().getId());
		Date date = new Date();
		msg.setSendingDate(date);
		msg.setSubject("Ajout de mot clé");
		msg.setType(MessageType.BySTUDENT);
		Administrator reciever = new Administrator();
		reciever = (adminServ.retrieveList(null, "ALL")).get(0);
		msg.setIdReceiver(reciever.getId());

		// create msg
		msgServ.create(msg);

		// create admin msg cx
		UserMessagePK pkAdmin = new UserMessagePK();
		pkAdmin.setMessageId(msg.getId());
		pkAdmin.setUserId(reciever.getId());

		UserMessage adminMsgCx = new UserMessage();
		adminMsgCx.setAccess(MessageAccess.TOREAD);
		adminMsgCx.setPk(pkAdmin);

		userMsgServ.create(adminMsgCx);

		// create student admin cx
		UserMessagePK pkStudent = new UserMessagePK();
		pkStudent.setMessageId(msg.getId());
		pkStudent.setUserId(sender.getId());
		
		UserMessage studentMsgCx = new UserMessage();
		studentMsgCx.setAccess(MessageAccess.SENT);
		studentMsgCx.setPk(pkStudent);
		
		userMsgServ.create(studentMsgCx);
	}

}
