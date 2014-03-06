package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.Calendar;

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
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeRemote;

@Stateless
public class MessageFacade implements IMessageFacadeLocal, IMessageFacadeRemote {

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;
	
	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;
	
	@Inject
	@UserMessageQualifier
	IServiceLocal<UserMessage> userMsgServ;
	
	@Override
	public boolean send(String content, Object additionnalObj, int idSender,
			int idReciever, String operation) {
		
		
		boolean returnValue = false;
		
		/************************ Affectation Projet *****************************/
		if (operation == "ProjCoach") {
			/**
			 * projCoach <=> affecter un coach à un projet
			 * if operation == projCoach:
			 * idReciever = idCoach
			 * additionalObj = Project
			 * idSender = null (admin)
			 * */
			System.out.println("entrée méthode.........................");
			Project project = (Project) additionnalObj;
			Student student = project.getStudent();
			CompanyCoach compcoach = project.getCompanycoach();
			Company comp = compcoach.getCompany();
			String topic = project.getTopic();
			String studentName = student.getFirstName() + " "+ student.getLastName();
			String compName = comp.getName();
			//content = ("Vous avez été affecté comme encadrant au projet intitulé: "+topic+" réalisé par :"+studentName+" pour le compte de :"+compName).toString();
		
			System.out.println("initialisation done..................");
			
			//Rechercher le nom du reciever
			Teacher coachSearched = new Teacher();
			coachSearched.setId(idReciever);
			System.out.println("retrieving teacher....................");
			coachSearched = (Teacher) teacherServ.retrieve(coachSearched, "ID");
			System.out.println("teacher found..........................");
			//String receiver = (coachSearched.getFirstName()+" "+coachSearched.getLastName()).toString();
			
			//initialisation du message
			System.out.println("init message......................");
			Message message = new Message();
			message.setContent("content");
			message.setReceiver("receiver");
			message.setSender("Administrateur");
			MessageType type = MessageType.RESPONSE_REQUIRED;
			message.setType(type);
			Calendar calendar = Calendar.getInstance();
			message.setSendingDate(calendar);
			message.setSubject("Affectation en encadrement [URGENT]");
			System.out.println("init message done.................");
			
			//Persistence: Message
			System.out.println("persit message.................");
			msgServ.create(message);
			System.out.println("persist message done....................");
			
			//récupérer l'id
			System.out.println("retrieve id message................");
			Message msgFound = (Message) msgServ.retrieve(message, "content");
			System.out.println("id found = ....."+msgFound.getId());
			
			//initialisation:  UserMessage (liaison avec user)
			System.out.println("init pk liaison userMsg...................");
			UserMessagePK pk = new UserMessagePK();
			System.out.println("new userMsgPk done.............");
			pk.setUserId(idReciever);
			System.out.println("setuserId done................");
			pk.setMessageId(message.getId());
			System.out.println("setMesgId done...................");
			MessageAccess access=MessageAccess.TOREAD;
			System.out.println("access enum done...............");
			UserMessage userMsg = new UserMessage(pk, access);
			System.out.println("new userMsg done.............");
//			userMsg.setAccess(access);
//			userMsg.setPk(pk);
			
			//Persistence: UserMessage
			System.out.println("persist laison userMsg....................");
			userMsgServ.create(userMsg);
			System.out.println("persistence done......................");
			returnValue = true;
		}

		return returnValue;
	}

	@Override
	public boolean receive(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeState(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

}
