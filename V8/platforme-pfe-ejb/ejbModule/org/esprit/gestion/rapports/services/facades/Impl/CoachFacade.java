package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.persistence.TeachingUnitDomain;
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeRemote;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;

/**
 * View (Gérer rapports)=> tableau: étudiant - projet - encadrant - rapporteur
 * bouton éditer encadrant: 1- listerCoachDisponibles (tel que coaching hours
 * <x?) 2- Affichage de la liste 3- sélectionner un coach =>
 * assignCoachToProject
 */
@Stateless
public class CoachFacade implements ICoachFacadeLocal, ICoachFacadeRemote {


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
	public List<Teacher> listerCoachDisponibles(int coachingHoursMin, List<String> projectDomains) {
		List<Teacher> filtredList = new ArrayList<Teacher>();
		int nbTeacherFiltred = 0;
		List<Teacher> teacherList = new ArrayList<Teacher>();
		Teacher teacher = new Teacher();
		teacher.setCoachingHours(coachingHoursMin);
		teacherList = teacherServ.retrieveList(teacher, "HOURS");
		
		
		
		/****************filter par teaching unit  (domain coach = domain project)*********************/
		
		//******parcourir la liste des teacher disponibles
		for (int teacherIt = 0; teacherIt < teacherList.size(); teacherIt++) {
			Teacher t = teacherList.get(teacherIt);
			//****recuperer tous les domaines du teacher (selon son unit)
			List<TeachingUnitDomain> teacherDomains = t.getTeachingUnit().getTeachingUnitDomains();
			
			//****parcourir les domaine du teacher, si egale au domaine du projet, ajouter a� la filtered list
			for (int teacherDomIt = 0; teacherDomIt < teacherDomains.size(); teacherDomIt++) {
				TeachingUnitDomain teachDomCx = teacherDomains.get(teacherDomIt);
				
				for (int projDomIt = 0; projDomIt < projectDomains.size(); projDomIt++) {
					
					String projDom =projectDomains.get(projDomIt);
					
					//chercher le nom du domaine du teacher
					Domain domt = new Domain();
					domt.setId(teachDomCx.getPk().getDomainId());
					domt = (Domain) domainServ.retrieve(domt, "ID");
					String teachDom = domt.getDomainName();
					
					
					if(projDom == teachDom ){
						filtredList.add(t);
						nbTeacherFiltred = nbTeacherFiltred+1;
					}
				}
			}
		}
		if(nbTeacherFiltred == 0){
			Teacher notFound = new Teacher();
			notFound.setFirstName("Aucun enseignant trouv\u00E9 dans le domaine du projet");
			filtredList.add(notFound);
		}
		return filtredList;
		//TODO a� tester (et voir c quoi ce warning sur la filtredlist)!!!!!
	}

	@Override
	public void CoachProjectAccept(Teacher teacher, Message message) {
	
		int projectId = message.getIncludedRef();
		TeacherRole tRole = new TeacherRole();
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRoleType role = TeacherRoleType.ENCADRANT;
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
				String content = "(Mr/Mme): " + teacher.getFirstName()+" "+teacher.getLastName()+" encadrera la réalisation du projet de fin d'étude :"+project.getTopic()+" référencé : "+project.getId();
				msgFacade.sendCoachAccept(content, project, teacher.getId(), student.getId());
		
		//coachingHours + 1	
				teacher.setCoachingHours(teacher.getCoachingHours()+1);
				teacherServ.update(teacher);
	}

}
