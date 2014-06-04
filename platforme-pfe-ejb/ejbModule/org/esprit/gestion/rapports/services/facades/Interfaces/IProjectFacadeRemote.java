package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.utils.AssignState;

@Remote
public interface IProjectFacadeRemote {
	
	
	public String addProjectToDB(Project project, boolean isNewComCoach);
	
	public List<Project> listProjectsToManage();
	
	/**
	 * Coach = encadrant
	 * Send a message to the coach with the project informations
	 * @param teacher
	 * @param project
	 * @return 
	 * returns true if operation success, else false 
	 */
	public void assignCoachToProject(Teacher teacher, int iDproject, User sender);
	
	/**
	 * 
	 * @param teacher
	 * @param project
	 * @return
	 * returns true if operation success, else false 
	 */
	public void cancelCoachToProject(Teacher teacher, Project project, int senderId);
	
	/**
	 * Corrector = rapporteur
	 * send a message to the corrector with the project informations
	 * @param teacher
	 * @param project
	 * @return
	 */
	public void assignCorrectorToProject(Teacher teacher, int iDproject, User sender);
	
	public void cancelCorrectorToProject(Teacher teacher, Project project, int senderId);
	
	public void deleteProject(Project projectToDelete);
	
	public AssignState findCoachAssignement(int idProj);
	

}
