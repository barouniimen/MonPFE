package org.esprit.gestion.rapports.services.facades.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.EventState;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.MessageType;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.SubmissionEvent;
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.SubmissionEventQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISubmissionFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISubmissionFacadeRemote;

@Stateless
public class SubmissionEventFacade implements ISubmissionFacadeLocal,
		ISubmissionFacadeRemote {

	@Inject
	@SubmissionEventQualifier
	IServiceLocal<SubmissionEvent> submitEventServ;

	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;

	@Inject
	@UserMessageQualifier
	IServiceLocal<UserMessage> userMsgServ;

	@EJB
	IProjectFacadeLocal projFacade;

	@Override
	public boolean sessionOpened() {

		SubmissionEvent event = new SubmissionEvent();
		event.setState(EventState.STARTED);
		event = (SubmissionEvent) submitEventServ.retrieve(event, "state");

		if (event != null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<SubmissionEvent> listAllSubmitEvent() {
		List<SubmissionEvent> allSubmitEvent = new ArrayList<SubmissionEvent>();
		allSubmitEvent = submitEventServ.retrieveList(null, "ALL");

		return allSubmitEvent;
	}

	@Override
	public void createSubmitEvent(SubmissionEvent submitEvent,
			Administrator sender) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
		// create submitEvent
		submitEvent.setState(EventState.STARTED);
		submitEventServ.create(submitEvent);

		// notify concerned students
		List<Project> projInProcess = new ArrayList<Project>();
		projInProcess = projFacade.listProjectsInProcess();

		for (int i = 0; i < projInProcess.size(); i++) {

			long diff = submitEvent.getDueDate().getTime()
					- projInProcess.get(i).getStartDate().getTime();

			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

			int diffMonth = diffDays / 30;

			if (diffMonth > 5) {
				// send message to student
				Message msgToStudent = new Message();
				String start = dateFormat.format(submitEvent.getStartDate());
				String due = dateFormat.format(submitEvent.getDueDate());
				msgToStudent
						.setContent("Bonjour,\n\n Une session de dépôt est programmée à partir du "
								+ start
								+ " jusqu'au: "
								+ due
								+ ".\n\n Cordialement \n\n Direction de Stages et PFE");

				msgToStudent.setIdReceiver(projInProcess.get(i).getStudent()
						.getId());
				msgToStudent.setIdSender(sender.getId());
				Date sendingDate = new Date();
				System.out.println("current date " + sendingDate);
				msgToStudent.setSendingDate(sendingDate);
				msgToStudent.setSubject("Ouverture d'une session de dépôt");
				msgToStudent.setType(MessageType.SUBMIT_EVENT_NOTIF);

				msgServ.create(msgToStudent);

				UserMessagePK pkStudent = new UserMessagePK();
				pkStudent.setMessageId(msgToStudent.getId());
				pkStudent.setUserId(projInProcess.get(i).getStudent().getId());
				UserMessage studentMsgCx = new UserMessage();
				studentMsgCx.setAccess(MessageAccess.TOREAD);
				studentMsgCx.setPk(pkStudent);

				userMsgServ.create(studentMsgCx);

			}

		}

	}

}
