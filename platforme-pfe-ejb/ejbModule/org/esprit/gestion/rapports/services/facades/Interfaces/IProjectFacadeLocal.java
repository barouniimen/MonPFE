package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

@Local
public interface IProjectFacadeLocal {

	public String addProjectToDB(Project project, boolean isNewComCoach);

	public List<Project> listProjectsToManage();

	/**
	 * Coach = encadrant
	 * 
	 * @param teacher
	 * @param project
	 * @return returns true if operation success false if the connection already
	 *         exists
	 */
	public void assignCoachToProject(Teacher teacher, Project project);

	/**
	 * 
	 * @param teacher
	 * @param project
	 * @return returns true if operation success false if the connection is not
	 *         found
	 */
	public void cancelCoachToProject(Teacher teacher, Project project);

	/**
	 * Corrector = rapporteur
	 * 
	 * @param teacher
	 * @param project
	 * @return
	 */
	public void assignCorrectorToProject(Teacher teacher, Project project);

	public void cancelCorrectorToProject(Teacher teacher, Project project);

	public void deleteProject(Project projectToDelete);
}
