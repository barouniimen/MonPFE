package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;

@Remote
public interface IProjectFacadeRemote {
	
	
	public String addProjectToDB(Project project, Student student);
	
	public List<Project> listProjectsToManage();
	
	/**
	 * Coach = encadrant
	 * Send a message to the coach with the project informations
	 * @param teacher
	 * @param project
	 * @return 
	 * returns true if operation success, else false 
	 */
	public void assignCoachToProject(Teacher teacher, Project project);
	
	/**
	 * 
	 * @param teacher
	 * @param project
	 * @return
	 * returns true if operation success, else false 
	 */
	public void cancelCoachToProject(Teacher teacher, Project project);
	
	/**
	 * Corrector = rapporteur
	 * send a message to the corrector with the project informations
	 * @param teacher
	 * @param project
	 * @return
	 */
	public void assignCorrectorToProject(Teacher teacher, Project project);
	
	public void cancelCorrectorToProject(Teacher teacher, Project project);
}
