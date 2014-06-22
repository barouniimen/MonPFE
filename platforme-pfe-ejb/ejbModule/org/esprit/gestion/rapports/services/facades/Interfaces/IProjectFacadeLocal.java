package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.utils.AssignState;

@Local
public interface IProjectFacadeLocal {

	public String addProjectToDB(Project project, boolean isNewComCoach);

	public List<Project> listProjectsInProcess();

	public List<Project> listProjectsDeposed();

	public List<Project> listProjectsWaitingSout();

	public List<Project> listProjectsSoutResult();

	public void assignCoachToProject(Teacher teacher, int iDproject, User sender);

	public void cancelCoachToProject(Teacher teacher, Project project,
			int senderId);

	public void assignCorrectorToProject(Teacher teacher, int iDproject,
			User sender);

	public void deleteProject(Project projectToDelete);

	public AssignState findCoachAssignement(int idProj);

	public void cancelCorrectorToProject(Teacher teacher, Project project,
			int senderId);

	public AssignState findCorrectorAssignement(int idProj);

	public List<TeacherRole> findTeacherRolesToProj(int idProj);

	public boolean allawdedPeriodToSubmit(int idStudent, int period);

	public List<Project> listProjCoached(int idCoach);
	
	public List<Project> listProjCorrector(int idCoach);
}
