package org.esprit.gestion.rapports.services.facades.Impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeRemote;

@Stateless
public class ProjectFacade implements IProjectFacadeLocal, IProjectFacadeRemote {

	@Inject
	@TecherRoleQualifier
	IServiceLocal<TeacherRole> roleServ;

	@Override
	public boolean assignCoachToProject(Teacher teacher, Project project) {
		//TODO send message to coach
		//TODO activate waiting
		return false;
	}

	@Override
	public boolean cancelCoachToProject(Teacher teacher, Project project) {
		boolean result=false;
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRole roleSeached;
		pk.setProjectId(project.getId());
		pk.setTeacherId(teacher.getId());
		roleSeached= (TeacherRole) roleServ.retrieve(pk, "PK");
		if (roleSeached == null) {
			result = false;
		} else{
			roleServ.delete(roleSeached);
			result = true;
		}
		return result;
	}

	@Override
	public boolean assignCorrectorToProject(Teacher teacher, Project project) {
		return false;
	}

	@Override
	public boolean cancelCorrectorToProject(Teacher teacher, Project project) {
		boolean result=false;
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRole roleSeached;
		pk.setProjectId(project.getId());
		pk.setTeacherId(teacher.getId());
		roleSeached= (TeacherRole) roleServ.retrieve(pk, "PK");
		if (roleSeached == null) {
			result = false;
		} else{
			roleServ.delete(roleSeached);
			result = true;
		}
		return result;
	}

}
