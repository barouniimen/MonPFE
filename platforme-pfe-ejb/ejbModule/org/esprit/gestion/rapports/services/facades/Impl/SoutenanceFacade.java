package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.PresentationEvent;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISoutenanceFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISoutenanceFacadeRemote;

@Stateless
public class SoutenanceFacade implements ISoutenanceFacadeLocal,
		ISoutenanceFacadeRemote {

	@EJB
	IMessageFacadeLocal msgFacade;
	
	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;
	
	
	@Override
	public void suggestSoutenance(Project project, Date date,
			Teacher presidentJury) {
		
		// *** affecter président de jury (et lui envoyer la date)
		int idReciever = presidentJury.getId();
		msgFacade.sendAffectPresidentJury(project, idReciever, date);

		// ***notifier le coach et le rapporteur en message à réponse obligatoire
		
		
		
	}

	@Override
	public void confirmSoutenance(Project project, Date date,
			int classroomId, Teacher chefJury) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public void cancelSoutenance(PresentationEvent soutenance) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public void editSoutenanceResult(PresentationEvent soutenance) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public List<Project> listProjectToplanSoutenance() {
		List<Project> returnList = new ArrayList<Project>();
		returnList = null;
		
		ValidationState state = ValidationState.WAITINGSOUT;
		Project proj = new Project();
		proj.setValidationState(state);
		returnList = projServ.retrieveList(proj, "VS");
		
		return returnList;
	}

}
