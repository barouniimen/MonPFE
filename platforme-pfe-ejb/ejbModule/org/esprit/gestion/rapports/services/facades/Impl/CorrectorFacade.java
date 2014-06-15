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
import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.persistence.TeachingUnitDomain;
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
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
	@DomainQualifier
	IServiceLocal<Domain> domainServ;
	
	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;
	
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
		
	}
	
	
	@Override
	public void CorrectorProjectAccept(int idCorrector, int idMsg) {
	// retrieve msg
		Message msg = new Message();
		msg.setId(idMsg);
		msg = (Message) msgServ.retrieve(msg, "ID");

		// retrieve coach
		Teacher corrector = new Teacher();
		corrector.setId(idCorrector);
		corrector = (Teacher) teacherServ.retrieve(corrector, "ID");

		// associer le coach au projet
		int projectId = msg.getIncludedRef();
		TeacherRole tRole = new TeacherRole();
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRoleType role = TeacherRoleType.RAPPORTEUR;
		pk.setProjectId(projectId);
		pk.setTeacherId(corrector.getId());
		tRole.setPk(pk);
		tRole.setRole(role);
		roleServ.create(tRole);

		// update message to seen
		UserMessage userMsg = new UserMessage();
		UserMessagePK userMpk = new UserMessagePK();
		userMpk.setMessageId(msg.getId());
		userMpk.setUserId(corrector.getId());
		MessageAccess access = MessageAccess.SEEN;
		userMsg.setAccess(access);
		userMsg.setPk(userMpk);
		userMsgServ.update(userMsg);

		// retrieve project
		Project project = new Project();
		project.setId(projectId);
		project = (Project) projServ.retrieve(project, "ID");
		// get student
		Student student = project.getStudent();

		msgFacade.sendCorrectorAccept(projectId, corrector.getId(), student.getId(),msg.getIdSender());

		
	}
	
	public List<Teacher> listCorrectorsSameDom(List<String> projectDomains){
		
		List<Teacher> filtredList = new ArrayList<Teacher>();
		int nbTeacherFiltred = 0;
		List<Teacher> teacherList = new ArrayList<Teacher>();

	
		// search teachears having less then coachingHoursMax
		teacherList = teacherServ.retrieveList(null, "ALL");
		if (teacherList.isEmpty()) {

			return null;
		}
		
		else{
		
			/**************** filter par teaching unit (domain coach = domain project) *********************/

// ******parcourir la liste des teacher disponibles
				for (int teacherIt = 0; teacherIt < teacherList.size(); teacherIt++) {
					Teacher t = teacherList.get(teacherIt);
					// ****recuperer tous les domaines du teacher (selon unit)
					TeachingUnit teachingUnit = new TeachingUnit();
					teachingUnit = t.getTeachingUnit();

					if (teachingUnit == null) {
						filtredList.add(t);
						nbTeacherFiltred = nbTeacherFiltred + 1;
					} else {

						List<TeachingUnitDomain> teacherDomains = teachingUnit
								.getTeachingUnitDomains();

	// ****parcourir les domaine du teacher, si egale au domaine duprojet, ajouter a  la filtered list
						for (int teacherDomIt = 0; teacherDomIt < teacherDomains
								.size(); teacherDomIt++) {
							
							TeachingUnitDomain teachDomCx = teacherDomains
									.get(teacherDomIt);

							for (int projDomIt = 0; projDomIt < projectDomains
									.size(); projDomIt++) {

								String projDom = projectDomains.get(projDomIt);

								// chercher le nom du domaine du teacher
								Domain domt = new Domain();
								domt.setId(teachDomCx.getPk().getDomainId());
								domt = (Domain) domainServ.retrieve(domt, "ID");
								String teachDom = domt.getDomainName();

								if (projDom == teachDom) {
									filtredList.add(t);
									nbTeacherFiltred = nbTeacherFiltred + 1;
								}
							}
						}
					}
				}
				if (nbTeacherFiltred == 0) {
					return null;
				}

			}
		
		return filtredList;
		
	}


	@Override
	public void correctorDeclineAssign(int id, int includedRef,
			String declineCause, int idAdmin, int idAssignMsg) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
		
	}

}
