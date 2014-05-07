package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICorrectorFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICorrectorFacadeRemote;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;

@Stateless
public class CorrectorFacade implements ICorrectorFacadeLocal, ICorrectorFacadeRemote{

	@Inject
	@TecherRoleQualifier
	IServiceLocal<TeacherRole> roleServ;
	

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;
	
	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;
	
	@Inject
	@UserMessageQualifier
	IServiceLocal<UserMessage> userMsgServ;
	
	@EJB
	IMessageFacadeLocal msgFacade;
	
	
	
	@Override
	public List<Teacher> listerCorrectorDisponibles() {
		List<Teacher> teacherList;
		Teacher teacher = new Teacher();
		teacherList = teacherServ.retrieveList(teacher, "ALL");
		return teacherList;
		//TODO ajouter un filtre sur le domaine (voir si on choisit le rapporteur par domaine)
	}
	
	
	@Override
	public void CorrectorProjectAccept(Teacher teacher,Message message) {
		
		int projectId = message.getIncludedRef();
		TeacherRole tRole = new TeacherRole();
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRoleType role = TeacherRoleType.RAPPORTEUR;
		pk.setProjectId(projectId);
		pk.setTeacherId(teacher.getId());
		tRole.setPk(pk);
		tRole.setRole(role);
		
		
		//mise à jour de l'association = lu (seen)		
		UserMessage userMsg = new UserMessage();
		UserMessagePK userMpk = new UserMessagePK();
		userMpk.setMessageId(message.getId());
		userMpk.setUserId(teacher.getId());
		MessageAccess access = MessageAccess.SEEN;
		userMsg.setAccess(access);
		userMsg.setPk(userMpk);
		userMsgServ.update(userMsg);

		// associer le coach au projet
			roleServ.create(tRole);	
			
		// notifier le student concerné et l'admin (notification de l'admin à l'intérieur de la fonction send)
			
				//***chercher le projet en question
				Project project = new Project();
				project.setId(projectId);
				project = (Project) projServ.retrieve(project, "ID");
				//***chercher le student concerné
				Student student = project.getStudent();
				//***envoyer le message
				String content = "(Mr/Mme): " + teacher.getFirstName()+" "+teacher.getLastName()+" sera le rapporteur du projet de fin d'étude :"+project.getTopic()+" référencé : "+project.getId();
				msgFacade.sendCorrectorAccept(content, project, teacher.getId(), student.getId());
	}
	
	

}
