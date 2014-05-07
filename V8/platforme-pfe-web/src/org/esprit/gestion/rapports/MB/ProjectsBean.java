package org.esprit.gestion.rapports.MB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.esprit.gestion.rapports.Model.ManagedProjects;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.primefaces.event.CloseEvent;

@ManagedBean
@ViewScoped
public class ProjectsBean implements Serializable {

	/************ Attributes *************/
	private static final long serialVersionUID = 1L;

	// EJB_CRUD----------------------------------------
	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;

	// EJB_Facade--------------------------------------
	@EJB
	private IProjectFacadeLocal projFacade;

	// vars ------------------------------------------
	private final static ValidationState[] listStates;
	private ManagedProjects selectedProject;
	private List<Project> listproj;
	private List<ManagedProjects> managedProj;
	private Project project;
	




	// intit listStates--------------------------------
	static {
		listStates = new ValidationState[6];
		listStates[0] = ValidationState.WAITING;
		listStates[1] = ValidationState.VALID;
		listStates[2] = ValidationState.NONVALID;
		listStates[3] = ValidationState.WAITINGSOUT;
		listStates[4] = ValidationState.SOUTNONVALID;
		listStates[5] = ValidationState.SOUTVALID;
	}

	/********************** PostConstruct ***************************/
	@PostConstruct
	public void init() {

		// retrieve project list----------------------------------
		setListproj(new ArrayList<Project>());
		setListproj(projFacade.listProjectsToManage());

		// format project List------------------------------------
		managedProj = new ArrayList<ManagedProjects>();
		managedProj = formatProjList(listproj, managedProj);

		

	}

	/********************** Create Model (format) *****************************/
	public List<ManagedProjects> formatProjList(List<Project> projlist,
			List<ManagedProjects> managedprojList) {
		String coachName = "Non affect\u00E9";
		String coachFirstName = null;
		String correctorName = "Non affect\u00E9";
		String correctorFirstName = null;
		for (int i = 0; i < projlist.size(); i++) {
			project = new Project();
			project = projlist.get(i);
			List<TeacherRole> teacherRoles = new ArrayList<TeacherRole>();
			for (int j = 0; j < teacherRoles.size(); j++) {
				if (teacherRoles.get(j).getRole() == TeacherRoleType.ENCADRANT) {
					coachFirstName = project.getTeacherRoles().get(j)
							.getTeacher().getFirstName();

					coachName = project.getTeacherRoles().get(j).getTeacher()
							.getLastName();
				} else if (teacherRoles.get(j).getRole() == TeacherRoleType.RAPPORTEUR) {
					correctorFirstName = project.getTeacherRoles().get(j)
							.getTeacher().getFirstName();
					correctorName = project.getTeacherRoles().get(j)
							.getTeacher().getLastName();
				}
			}
			ManagedProjects managedproj = new ManagedProjects(project.getId(),
					project.getTopic(), project.getValidationState(), project
							.getCompanycoach().getCompany().getName(),
					coachName, coachFirstName, correctorName,
					correctorFirstName,
					project.getCompanycoach().getLastName(), project
							.getCompanycoach().getFirstName(), project
							.getStudent().getLastName(), project.getStudent()
							.getFirstName(), project.getStartDate(),
					project.getProjectDomains());

			managedprojList.add(managedproj);
		}
		return managedprojList;

	}
	
	
	

	/****************** Listners *********************/
	public void updateProject() {
		selectedProject = getSelectedProject();
		System.out.println("update ok" + selectedProject.getProjTopic());

	}

	public void handleClose(CloseEvent event) {
		selectedProject = new ManagedProjects();
	}

	
	/*************** Constructor ******************/
	public ProjectsBean() {
		super();
	}
	/*************** Getter && Setter ************/
	public ManagedProjects getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(ManagedProjects selectedProject) {
		this.selectedProject = selectedProject;
	}


	public List<ManagedProjects> getManagedProj() {
		return managedProj;
	}

	public void setManagedProj(List<ManagedProjects> managedProj) {
		this.managedProj = managedProj;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public List<Project> getListproj() {
		return listproj;
	}

	public void setListproj(List<Project> listproj) {
		this.listproj = listproj;
	}

	
	public ValidationState[] getListstates() {
		return listStates;
	}

	

}
