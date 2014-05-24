package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.PresentationEvent;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

@Remote
public interface ISoutenanceFacadeRemote {
	
	public List<Project> listProjectToplanSoutenance(); 
	
	public void suggestSoutenance(Project project, Date date, Teacher presidentJury);
	
	public void confirmSoutenance(Project project, Date date, int classroomId, Teacher chefJury);
	
	public void cancelSoutenance(PresentationEvent soutenance);
	
	
	/**
	 * state de soutenance = validé/non validé ...
	 * state de project = validé / non validé ....
	 * si validé, state de rapport = archivé
	 * @param soutenance
	 */
	public void editSoutenanceResult(PresentationEvent soutenance);
}
