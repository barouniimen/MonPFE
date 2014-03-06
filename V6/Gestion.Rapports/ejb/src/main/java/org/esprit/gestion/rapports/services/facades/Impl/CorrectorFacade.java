package org.esprit.gestion.rapports.services.facades.Impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICorrectorFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICorrectorFacadeRemote;

@Stateless
public class CorrectorFacade implements ICorrectorFacadeLocal, ICorrectorFacadeRemote{

	@Inject
	@TecherRoleQualifier
	IServiceLocal<TeacherRole> roleServ;
	
	@Override
	public boolean CorrectorProjectAccept(Teacher teacher, Project project) {
		boolean result=false;
		TeacherRole tRole = new TeacherRole();
		TeacherRolePK pk = new TeacherRolePK();
		TeacherRole roleSeached;
		TeacherRoleType role = TeacherRoleType.RAPPORTEUR;
		pk.setProjectId(project.getId());
		pk.setTeacherId(teacher.getId());
		tRole.setPk(pk);
		tRole.setRole(role);
		
		roleSeached= (TeacherRole) roleServ.retrieve(pk, "PK");
		if (roleSeached == null) {
			roleServ.create(tRole);
			//TODO send message to admin
			//TODO send message to student
			//TODO send message to coach
			result = true;
		} else{
			result = false;
		}
		return result;
	}
	
	

}
