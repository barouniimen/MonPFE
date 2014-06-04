package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.utils.AssignState;

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
	public void assignCoachToProject(Teacher teacher, int iDproject, User sender);

	/**
	 * 
	 * @param teacher
	 * @param project
	 * @return returns true if operation success false if the connection is not
	 *         found
	 */
	public void cancelCoachToProject(Teacher teacher, Project project, int senderId);

	/**
	 * Corrector = rapporteur
	 * 
	 * @param teacher
	 * @param project
	 * @return
	 */
	public void assignCorrectorToProject(Teacher teacher, int iDproject, User sender);



	public void deleteProject(Project projectToDelete);
	
	public AssignState findCoachAssignement(int idProj);
	
	public void cancelCorrectorToProject(Teacher teacher, Project project, int senderId);
	
	public AssignState findCorrectorAssignement(int idProj);
}
