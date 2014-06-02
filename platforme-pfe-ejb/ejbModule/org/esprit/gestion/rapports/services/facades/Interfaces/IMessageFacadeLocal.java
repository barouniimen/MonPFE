package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.Date;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.Project;

@Local
public interface IMessageFacadeLocal {

	public void send(String content, String subject, int idSender, int idReciever);
	
	public void sendAffectCoach(int iDproject, int idReciever);
	
	public void sendAffectCorrector(Project project, int idReciever);
	
	public void sendAffectPresidentJury(Project project, int idReciever, Date soutenanceDate);
	
	public void sendcancelCoachToProject(String content, Project project, int coachId);

	public void sendCoachAccept(String content, Project project, int idSender, int idReciever);
	
	public void sendCorrectorAccept(String content, Project project, int idSender, int idReciever);
	
	public void sendcancelCorrectorToProject(String content, Project project, int correctorId);
	
	/**
	 * il faut préciser message.state:
	 * if state = réponse requise: afficher sous forme de poop-up (accept-decline)
	 * @return
	 */
	public boolean read(Message message);
	
	public boolean changeState(Message message);
}
