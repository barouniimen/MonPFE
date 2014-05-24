package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeRemote;

@Stateless
public class MessageFacade implements IMessageFacadeLocal, IMessageFacadeRemote {

	@Inject
	@UserQualifier
	IServiceLocal<User> userServ;

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

	@Override
	public void send(String content, String subject, int idSender,
			int idReciever) {
		String senderName = null;
		String receiverName = null;
		MessageType type = null;

		// rechercher sender
		if (idSender == 0) {
			senderName = "Visiteur";
			type = MessageType.ByVISITOR;
		}

		else {
			User sender = new User();
			sender.setId(idSender);
			sender = (User) userServ.retrieve(sender, "ID");
			if (sender.getFirstName() == "admin") {
				senderName = "administrateur";
				type = MessageType.ByADMIN;
			} else if (sender instanceof Teacher) {
				senderName = sender.getFirstName() + " " + sender.getLastName();
				type = MessageType.ByTEACHER;
			} else if (sender instanceof Student) {
				senderName = sender.getFirstName() + " " + sender.getLastName();
				type = MessageType.BySTUDENT;
			}
		}
		// rechercher reciever
		User reciever = new User();
		reciever.setId(idReciever);
		reciever = (User) userServ.retrieve(reciever, "ID");
		if (reciever.getLogin() == "admin") {
			receiverName = "administrateur";
		} else {
			receiverName = reciever.getFirstName() + " "
					+ reciever.getLastName();
		}

		// initialiser message
		Date sendingDate = new Date();
		Message message = new Message(subject, content, receiverName, senderName, sendingDate, type);
				

		// persister message
		msgServ.create(message);

		// récupérer id message
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
		// TODO changer état du message à lu
		return false;
	}

	@Override
	public boolean changeState(Message message) {
		// TODO changer état du message (optionnel)
		return false;
	}

	@Override
	/**
	 * projCoach <=> affecter un coach à un projet if operation ==
	 * projCoach: idReciever = idCoach additionalObj = Project idSender
	 * = null (admin)
	 * */
	public void sendAffectCoach(Project project, int idReciever) {

		Student student = project.getStudent();
		CompanyCoach compcoach = project.getCompanycoach();
		Company comp = compcoach.getCompany();
		String topic = project.getTopic();
		String studentName = student.getFirstName() + " "
				+ student.getLastName();
		String compName = comp.getName();
		String content = ("Vous avez \u00E9t\u00E9 affect\u00E9 comme encadrant au projet intitul\u00E9: "
				+ topic
				+ " r\u00E9alis\u00E9 par :"
				+ studentName
				+ " pour le compte de :" + compName + ". ").toString();

		// Rechercher le nom du reciever (Teacher)
		Teacher coachSearched = new Teacher();
		coachSearched.setId(idReciever);
		coachSearched = (Teacher) teacherServ.retrieve(coachSearched, "ID");
		String receiver = (coachSearched.getFirstName() + " " + coachSearched
				.getLastName()).toString();

		// initialisation du message
		Message message = new Message();
		message.setContent(content);
		message.setReceiver(receiver);
		message.setSender("Administrateur");
		MessageType type = MessageType.COACHASSIGN;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Affectation en encadrement [URGENT]");
		message.setIncludedRef(project.getId());

		// Persistence: Message
		msgServ.create(message);

		// récupérer l'id
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
	public void sendcancelCoachToProject(String content, Project project,
			int coachId) {

		/********************** Notifier student ***********************************/
		// initialiser le message
		String receiver = project.getStudent().getFirstName() + " "
				+ project.getStudent().getLastName();
		Date sendingDate = new Date();
		MessageType type = MessageType.ByADMIN;
		Message msg = new Message("Affectation d'encadrant annul\u00E9e [URGETN]",
				content, receiver, "administrateur", sendingDate, type);

		// persister message
		msgServ.create(msg);

		// récupérer id msg
		msg = (Message) msgServ.retrieve(msg, "ID");

		// créer connexion userMessage (student)
		UserMessage userMsg = new UserMessage();
		UserMessagePK pk = new UserMessagePK();
		pk.setMessageId(msg.getId());
		pk.setUserId(project.getStudent().getId());
		userMsg.setPk(pk);
		MessageAccess access = MessageAccess.TOREAD;
		userMsg.setAccess(access);

		// persister userMessage (student)
		userMsgServ.create(userMsg);

		/***************************** Notifier le COACH ************************************/

		// créer connexion userMessage (coach)
		UserMessage coachMsg = new UserMessage();
		UserMessagePK pk2 = new UserMessagePK();
		pk2.setMessageId(msg.getId());
		pk2.setUserId(coachId);
		userMsg.setPk(pk2);
		coachMsg.setAccess(access);

		// persister userMessage (coach)
		userMsgServ.create(coachMsg);
	}

	@Override
	public void sendCoachAccept(String content, Project project, int idSender,
			int idReciever) {

		// Send message to student
		// *** Rechercher le nom du reciever (Student)
		Student studentSearched = new Student();
		studentSearched.setId(idReciever);
		studentSearched = (Student) studentServ.retrieve(studentSearched, "ID");
		String receiver = (studentSearched.getFirstName() + " " + studentSearched
				.getLastName()).toString();

		// *** Sender

		String sender = "Message automatique";

		// *** initialisation du message
		Message message = new Message();
		message.setContent(content);
		message.setReceiver(receiver);
		message.setSender(sender);
		MessageType type = MessageType.ByCOACH;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Votre encadrant [URGENT]");
		message.setIncludedRef(project.getId());

		// ***Persistence: Message
		msgServ.create(message);

		// ***récupérer l'id
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
		messageAdmin.setReceiver("administrateur");
		messageAdmin.setSender(sender);
		messageAdmin.setType(type);
		messageAdmin.setSendingDate(calendar);
		messageAdmin.setSubject("Acceptation d'encadrant");
		messageAdmin.setIncludedRef(project.getId());

		// ***Persistence: Message
		msgServ.create(messageAdmin);

		// ***récupérer l'id
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
	public void sendAffectCorrector(Project project, int idReciever) {

		Student student = project.getStudent();
		CompanyCoach compcoach = project.getCompanycoach();
		Company comp = compcoach.getCompany();
		String topic = project.getTopic();
		String studentName = student.getFirstName() + " "
				+ student.getLastName();
		String compName = comp.getName();
		String content = ("Vous avez \u00E9t\u00E9 affect\u00E9 comme rapporteur au projet intitul\u00E9: "
				+ topic
				+ " r\u00E9alis\u00E9 par :"
				+ studentName
				+ " pour le compte de :"
				+ compName
				+ ". Le projet est r\u00E9f\u00E9renc\u00E9 sous : " + project.getId())
				.toString();

		// Rechercher le nom du reciever (Teacher)
		Teacher correctorSearched = new Teacher();
		correctorSearched.setId(idReciever);
		correctorSearched = (Teacher) teacherServ.retrieve(correctorSearched,
				"ID");
		String receiver = (correctorSearched.getFirstName() + " " + correctorSearched
				.getLastName()).toString();

		// initialisation du message
		Message message = new Message();
		message.setContent(content);
		message.setReceiver(receiver);
		message.setSender("Administrateur");
		MessageType type = MessageType.CORRECTORASSIGN;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Affectation rapporteur [URGENT]");
		message.setIncludedRef(project.getId());

		// Persistence: Message
		msgServ.create(message);

		// récupérer l'id
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
	public void sendcancelCorrectorToProject(String content, Project project,
			int correctorId) {
		// TODO à tester
		/********************** Notifier student ***********************************/
		// initialiser le message
		String receiver = project.getStudent().getFirstName() + " "
				+ project.getStudent().getLastName();
		Date sendingDate = new Date();
		MessageType type = MessageType.ByADMIN;
		Message msg = new Message("Affectation de rapporteur annul\u00E9e [URGETN]",
				content, receiver, "administrateur", sendingDate, type);

		// persister message
		msgServ.create(msg);

		// récupérer id msg
		msg = (Message) msgServ.retrieve(msg, "ID");

		// créer connexion userMessage (student)
		UserMessage userMsg = new UserMessage();
		UserMessagePK pk = new UserMessagePK();
		pk.setMessageId(msg.getId());
		pk.setUserId(project.getStudent().getId());
		userMsg.setPk(pk);
		MessageAccess access = MessageAccess.TOREAD;
		userMsg.setAccess(access);

		// persister userMessage (student)
		userMsgServ.create(userMsg);

		/***************************** Notifier le COACH ************************************/

		// créer connexion userMessage (coach)
		UserMessage correctorMsg = new UserMessage();
		UserMessagePK pk2 = new UserMessagePK();
		pk2.setMessageId(msg.getId());
		pk2.setUserId(correctorId);
		userMsg.setPk(pk2);
		correctorMsg.setAccess(access);

		// persister userMessage (coach)
		userMsgServ.create(correctorMsg);

	}

	@Override
	public void sendCorrectorAccept(String content, Project project,
			int idSender, int idReciever) {
		// TODO à tester

		// Send message to student
		// *** Rechercher le nom du reciever (Student)
		Student studentSearched = new Student();
		studentSearched.setId(idReciever);
		studentSearched = (Student) studentServ.retrieve(studentSearched, "ID");
		String receiver = (studentSearched.getFirstName() + " " + studentSearched
				.getLastName()).toString();

		// Sender
		String sender = "Message Automatique";

		// *** initialisation du message
		Message message = new Message();
		message.setContent(content);
		message.setReceiver(receiver);
		message.setSender(sender);
		MessageType type = MessageType.ByCORRECTOR;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Votre rapporteur [URGENT]");
		message.setIncludedRef(project.getId());

		// ***Persistence: Message
		msgServ.create(message);

		// ***récupérer l'id
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
		messageAdmin.setReceiver("administrateur");
		messageAdmin.setSender(sender);
		messageAdmin.setType(type);
		messageAdmin.setSendingDate(calendar);
		messageAdmin.setSubject("Acceptation d'affectation");
		messageAdmin.setIncludedRef(project.getId());

		// ***Persistence: Message
		msgServ.create(messageAdmin);

		// ***récupérer l'id
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
		// TODO à tester

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
		String receiver = (pJurySearched.getFirstName() + " " + pJurySearched
				.getLastName()).toString();

		// initialisation du message
		Message message = new Message();
		message.setContent(content);
		message.setReceiver(receiver);
		message.setSender("Administrateur");
		MessageType type = MessageType.PJURYASSIGN;
		message.setType(type);
		Date calendar = new Date();
		message.setSendingDate(calendar);
		message.setSubject("Affectation pour pr\u00E9sider un jury de soutenance PFE [URGENT]");
		message.setIncludedRef(project.getId());

		// Persistence: Message
		msgServ.create(message);

		// récupérer l'id
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

}
