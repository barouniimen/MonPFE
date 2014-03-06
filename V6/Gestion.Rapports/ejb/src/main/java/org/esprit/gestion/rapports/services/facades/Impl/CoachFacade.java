package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeRemote;
/**
 * View (Gérer rapports)=> tableau: étudiant - projet - encadrant - rapporteur
 * bouton éditer encadrant: 
 * 1- listerCoachDisponibles (tel que coaching hours <x?)
 * 2- Affichage de la liste
 * 3- sélectionner un coach => assignCoachToProject
 */
@Stateless
public class CoachFacade implements ICoachFacadeLocal,ICoachFacadeRemote{

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;

	@Inject
	@TecherRoleQualifier
	IServiceLocal<TeacherRole> roleServ;

	@Override
	public List<Teacher> listerCoachDisponibles(int coachingHoursMin) {
		List<Teacher> teacherList;
		Teacher teacher = new Teacher();
		teacher.setCoachingHours(coachingHoursMin);
		teacherList= teacherServ.retrieveList(teacher, "HOURS");
		return teacherList;
	}



	@Override
	public boolean CoachProjectAccept(Teacher teacher, Project project) {
		boolean result=false;
		TeacherRole tRole = new TeacherRole();
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRole roleSeached;
		TeacherRoleType role = TeacherRoleType.ENCADRANT;
		pk.setProjectId(project.getId());
		pk.setTeacherId(teacher.getId());
		tRole.setPk(pk);
		tRole.setRole(role);
		
		roleSeached= (TeacherRole) roleServ.retrieve(pk, "PK");
		if (roleSeached == null) {
			roleServ.create(tRole);
			result = true;
			//TODO send a message to student
			//TODO send message to admin
		} else{
			result = false;
		}
		return result;
	}
	
	

}
