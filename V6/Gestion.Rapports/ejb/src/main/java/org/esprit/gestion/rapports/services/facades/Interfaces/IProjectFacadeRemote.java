package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

@Remote
public interface IProjectFacadeRemote {
	/**
	 * Coach = encadrant
	 * Send a message to the coach with the project informations
	 * @param teacher
	 * @param project
	 * @return 
	 * returns true if operation success, else false 
	 */
	public boolean assignCoachToProject(Teacher teacher, Project project);
	
	/**
	 * 
	 * @param teacher
	 * @param project
	 * @return
	 * returns true if operation success, else false 
	 */
	public boolean cancelCoachToProject(Teacher teacher, Project project);
	
	/**
	 * Corrector = rapporteur
	 * send a message to the corrector with the project informations
	 * @param teacher
	 * @param project
	 * @return
	 */
	public boolean assignCorrectorToProject(Teacher teacher, Project project);
	
	public boolean cancelCorrectorToProject(Teacher teacher, Project project);
}
