package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.PresentationEvent;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

@Local
public interface ISoutenanceFacadeLocal {

	public List<Project> listProjectToplanSoutenance(); 
	
	public void suggestSoutenance(Project project, Calendar date, Teacher presidentJury);
	
	public void confirmSoutenance(Project project, Calendar date, int classroomId, Teacher chefJury);
	
	public void cancelSoutenance(PresentationEvent soutenance);
	
	
	/**
	 * state de soutenance = validé/non validé ...
	 * state de project = validé / non validé ....
	 * si validé, state de rapport = archivé
	 * @param soutenance
	 */
	public void editSoutenanceResult(PresentationEvent soutenance);
	
}
