package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.Date;

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

		// r√©cup√©rer id message
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

	@Override
	public boolean read(Message message) {
		// TODO changer √©tat du message √† lu
		return false;
	}

	@Override
	public boolean changeState(Message message) {
		// TODO changer √©tat du message (optionnel)
		return false;
	}

	@Override
	/**
	 * projCoach <=> affecter un coach √† un projet if operation ==
	 * projCoach: idReciever = idCoach additionalObj = Project idSender
	 * = null (admin)
	 * */
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

				content = "Vous avez \u00E9t\u00E9 affect\u00E9 comme encadrant au projet intitul\u00E9 ["
						+ topic
						+ "] r\u00E9alis\u00E9 par ["
						+ studentName
						+ "] pour le compte de [" + compName + "]. ";

			}

		}

		else {
			content = "Vous avez \u00E9t\u00E9 affect\u00E9 comme encadrant au projet intitul\u00E9: "
					+ topic + " r\u00E9alis\u00E9 par :" + studentName;
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
		message.setSubject("Affectation en encadrement [URGENT]");
		message.setIncludedRef(project.getId());
		AssignResponseState responseState = AssignResponseState.WAITING;
		message.setResponseState(responseState);
		message.setIdReceiver(idReciever);
		message.setIdSender(idSender);

		// Persistence: Message
		msgServ.create(message);

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

		String contentCoach = "(Mr/Mme)."
				+ t.getLastName()
				+ " "
				+ t.getFirstName()
				+ ". Votre affectation en tant qu'encadrant pour le projet intitulÈ "
				+ "[" + project.getTopic() + "]" + " rÈalisÈ par " + "["
				+ project.getStudent().getLastName() + " "
				+ project.getStudent().getFirstName() + "], a ÈtÈ annulÈe.";
		Message msgCoach = new Message(project.getId(),
				"Affectation en encadrement annul\u00E9e [URGENT]",
				contentCoach, coachId, senderId, sendingDate, type, null, null);

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
			String content = "(Mr/Mme)."
					+ t.getLastName()
					+ " "
					+ t.getFirstName()
					+ " n'est plus votre encadrant. Un autre encadrant vous sera affectÈ prochainement.";
			Message msg = new Message(project.getId(),
					"Affectation d'encadrant annul\u00E9e [URGENT]", content,
					project.getStudent().getId(), senderId, sendingDate, type,
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
	public void sendCoachAccept(String content, Project project, int idSender,
			int idReciever) {

		// Send message to student
		// *** Rechercher le nom du reciever (Student)
		Student studentSearched = new Student();
		studentSearched.setId(idReciever);
		studentSearched = (Student) studentServ.retrieve(studentSearched, "ID");

		// *** Sender

		// *** initialisation du message
		Message message = new Message();
		message.setContent(content);

		MessageType type = MessageType.ByCOACH;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Votre encadrant [URGENT]");
		message.setIncludedRef(project.getId());

		// ***Persistence: Message
		msgServ.create(message);

		// ***r√©cup√©rer l'id
		message = (Message) msgServ.retrieve(message, "CONTENT");

		// ***initialisation: UserMessage (liaison avec reciever)
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(idReciever);
		pk.setMessageId(message.getId());
		MessageAccess access = MessageAccess.TOREAD;
		UserMessage userMsg = new UserMessage(pk, access);

		// ***Persistence: UserMessage
		userMsgServ.create(userMsg);

		// ***initialisation: UserMessage (liaison avec sender)
		UserMessagePK pksender = new UserMessagePK();
		pksender.setUserId(idSender);
		pksender.setMessageId(message.getId());
		MessageAccess accessSender = MessageAccess.SENT;
		UserMessage userMsgSender = new UserMessage(pksender, accessSender);

		// ***Persistence: UserMessage sender
		userMsgServ.create(userMsgSender);

		// Send message to admin
		// *** initialisation du message
		Message messageAdmin = new Message();
		messageAdmin.setContent("Rapport d'affectation en encadrement: "
				+ content + ".");

		messageAdmin.setType(type);
		messageAdmin.setSendingDate(calendar);
		messageAdmin.setSubject("Acceptation d'encadrant");
		messageAdmin.setIncludedRef(project.getId());

		// ***Persistence: Message
		msgServ.create(messageAdmin);

		// ***r√©cup√©rer l'id
		messageAdmin = (Message) msgServ.retrieve(messageAdmin, "CONTENT");

		// ***initialisation: UserMessage (liaison avec reciever)
		User user = new User("admin", "admin", null, null, null, 0);
		user = (User) userServ.retrieve(user, "NAME");
		int idAdmin = user.getId();
		pk.setUserId(idAdmin);
		pk.setMessageId(messageAdmin.getId());
		UserMessage adminMsg = new UserMessage(pk, access);

		// ***Persistence: UserMessage
		userMsgServ.create(adminMsg);

		// ***initialisation: UserMessage (liaison avec sender)
		pksender.setMessageId(messageAdmin.getId());
		UserMessage adminMsgSender = new UserMessage(pksender, accessSender);

		// ***Persistence: UserMessage sender
		userMsgServ.create(adminMsgSender);

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

				content = "Vous avez \u00E9t\u00E9 affect\u00E9 comme rapporteur au projet intitul\u00E9 ["
						+ topic
						+ "] r\u00E9alis\u00E9 par ["
						+ studentName
						+ "] pour le compte de [" + compName + "]. ";

			}

		}

		else {
			content = "Vous avez \u00E9t\u00E9 affect\u00E9 comme rapporteur au projet intitul\u00E9: "
					+ topic + " r\u00E9alis\u00E9 par :" + studentName;
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
		message.setSubject("Affectation en tant que rapporteur [URGENT]");
		message.setIncludedRef(project.getId());
		AssignResponseState responseState = AssignResponseState.WAITING;
		message.setResponseState(responseState);
		message.setIdReceiver(idReciever);
		message.setIdSender(idSender);

		// Persistence: Message
		msgServ.create(message);

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
	public void sendCorrectorAccept(String content, Project project,
			int idSender, int idReciever) {
		// TODO √† tester

		// Send message to student
		// *** Rechercher le nom du reciever (Student)
		Student studentSearched = new Student();
		studentSearched.setId(idReciever);
		studentSearched = (Student) studentServ.retrieve(studentSearched, "ID");

		// *** initialisation du message
		Message message = new Message();
		message.setContent(content);

		MessageType type = MessageType.ByCORRECTOR;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Votre rapporteur [URGENT]");
		message.setIncludedRef(project.getId());

		// ***Persistence: Message
		msgServ.create(message);

		// ***r√©cup√©rer l'id
		message = (Message) msgServ.retrieve(message, "CONTENT");

		// ***initialisation: UserMessage (liaison avec reciever)
		UserMessagePK pk = new UserMessagePK();
		pk.setUserId(idReciever);
		pk.setMessageId(message.getId());
		MessageAccess access = MessageAccess.TOREAD;
		UserMessage userMsg = new UserMessage(pk, access);

		// ***Persistence: UserMessage
		userMsgServ.create(userMsg);

		// ***initialisation: UserMessage (liaison avec sender)
		UserMessagePK pksender = new UserMessagePK();
		pksender.setUserId(idSender);
		pksender.setMessageId(message.getId());
		MessageAccess accessSender = MessageAccess.SENT;
		UserMessage userMsgSender = new UserMessage(pksender, accessSender);

		// ***Persistence: UserMessage sender
		userMsgServ.create(userMsgSender);

		// Send message to admin
		// *** initialisation du message
		Message messageAdmin = new Message();
		messageAdmin.setContent("Rapport d'affectation de rapporteur: "
				+ content + ".");
		messageAdmin.setType(type);
		messageAdmin.setSendingDate(calendar);
		messageAdmin.setSubject("Acceptation d'affectation");
		messageAdmin.setIncludedRef(project.getId());

		// ***Persistence: Message
		msgServ.create(messageAdmin);

		// ***r√©cup√©rer l'id
		messageAdmin = (Message) msgServ.retrieve(messageAdmin, "CONTENT");

		// ***initialisation: UserMessage (liaison avec reciever)
		User user = new User("admin", "admin", null, null, null, 0);
		user = (User) userServ.retrieve(user, "NAME");
		int idAdmin = user.getId();
		pk.setUserId(idAdmin);
		pk.setMessageId(messageAdmin.getId());
		UserMessage adminMsg = new UserMessage(pk, access);

		// ***Persistence: UserMessage
		userMsgServ.create(adminMsg);

		// ***initialisation: UserMessage (liaison avec sender)
		pksender.setMessageId(messageAdmin.getId());
		UserMessage adminMsgSender = new UserMessage(pksender, accessSender);

		// ***Persistence: UserMessage sender
		userMsgServ.create(adminMsgSender);

	}

	@Override
	public void sendAffectPresidentJury(Project project, int idReciever,
			Date soutenanceDate) {
		// TODO √† tester

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
		message.setSubject("Affectation pour pr\u00E9sider un jury de soutenance PFE [URGENT]");
		message.setIncludedRef(project.getId());

		// Persistence: Message
		msgServ.create(message);

		// r√©cup√©rer l'id
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

		String contentCorrector = "(Mr/Mme)."
				+ t.getLastName()
				+ " "
				+ t.getFirstName()
				+ ". Votre affectation en tant que rapporteur pour le projet intitulÈ "
				+ "[" + project.getTopic() + "]" + " rÈalisÈ par " + "["
				+ project.getStudent().getLastName() + " "
				+ project.getStudent().getFirstName() + "], a ÈtÈ annulÈe.";
		Message msgCorrector = new Message(project.getId(),
				"Affectation en tant que rapporteur annul\u00E9e [URGENT]",
				contentCorrector , correctorId, senderId, sendingDate, type, null, null);

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

}
