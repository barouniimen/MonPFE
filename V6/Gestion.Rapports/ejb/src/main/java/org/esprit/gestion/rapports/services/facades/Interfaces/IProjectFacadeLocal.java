package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;


@Local
public interface IProjectFacadeLocal {
	/**
	 * Coach = encadrant
	 * @param teacher
	 * @param project
	 * @return 
	 * returns true if operation success 
	 * false if the connection already exists
	 */
	public boolean assignCoachToProject(Teacher teacher, Project project);
	
	/**
	 * 
	 * @param teacher
	 * @param project
	 * @return
	 * returns true if operation success
	 * false if the connection is not found
	 */
	public boolean cancelCoachToProject(Teacher teacher, Project project);
	
	/**
	 * Corrector = rapporteur
	 * @param teacher
	 * @param project
	 * @return
	 */
	public boolean assignCorrectorToProject(Teacher teacher, Project project);
	
	public boolean cancelCorrectorToProject(Teacher teacher, Project project);
}
