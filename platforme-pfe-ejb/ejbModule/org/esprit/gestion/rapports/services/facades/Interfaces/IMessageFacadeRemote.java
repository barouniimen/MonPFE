package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.MessageType;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.utils.MessageStats;

@Remote
public interface IMessageFacadeRemote {

	public void send(String content, String subject, int idSender,
			int idReciever);

	public void sendAffectCoach(int iDproject, int idReciever, int idSender);

	public void sendAffectCorrector(int iDproject, int idReciever, int idSender);

	public void sendAffectPresidentJury(Project project, int idReciever,
			Date soutenanceDate);

	public void sendcancelCoachToProject(Project project, int coachId,
			int senderId);

	public void sendCoachAccept(int idProject, int idCoach,
			int idStudent,int idAdmin);

	public void sendcancelCorrectorToProject(Project project, int correctorId,
			int senderId);

	public void sendCorrectorAccept(int idProject, int idCorrector,
			int idStudent,int idAdmin);

	public void changeAccess(int msgId,int userId, MessageAccess access);
	
	public List<Message> listMsgToRead(int idUser);
	
	public List<Message> listMsgSeen(int idUser);
	
	public List<Message> listMsgSent(int idUser);
	
	public List<Message> listMsgByType(int idUser, MessageType type);
	
	public MessageStats listNbrMsg(int idUser);
	
	public void sendAskNewKeyWord(String newCategory, String newKeyWord, int idSender, Student sender);
}
