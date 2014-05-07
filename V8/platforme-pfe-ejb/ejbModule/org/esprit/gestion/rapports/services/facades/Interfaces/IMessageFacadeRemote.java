package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.Calendar;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.Project;

@Remote
public interface IMessageFacadeRemote {
	
	/**
	 * il faut préciser message.state:
	 * if state = réponse requise: afficher sous forme de poop-up (accept-decline)
	 * @return
	 */
	public void send(String content, String subject, int idSender, int idReciever);

	public void sendAffectCoach(Project project, int idReciever);
	
	public void sendAffectCorrector( Project project, int idReciever);
	
	public void sendAffectPresidentJury(Project project, int idReciever, Calendar soutenanceDate);
	
	public void sendcancelCoachToProject(String content, Project project, int coachId);
	
	public void sendCoachAccept(String content, Project project, int idSender, int idReciever);
	
	public void sendcancelCorrectorToProject(String content, Project project, int correctorId);
	
	public void sendCorrectorAccept(String content, Project project, int idSender, int idReciever);
	
	public boolean read(Message message);
	
	public boolean changeState(Message message);
}
