package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.Date;
import java.util.List;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeServ;

public class CoachFacadeServ implements ICoachFacadeServ{

	@Override
	public boolean assignCoachToProject(Teacher teacher, Project project,Date date) {
		
		
		return false;
	}

	@Override
	public boolean acceptRole(Teacher teacher, Project project, Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean declineRole(Teacher teacher, Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelCoachAssign(Teacher teacher) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Teacher> listCoachs() {
		// TODO Auto-generated method stub
		return null;
	}

}
