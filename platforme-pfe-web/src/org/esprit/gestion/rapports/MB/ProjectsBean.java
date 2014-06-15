package org.esprit.gestion.rapports.MB;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.esprit.gestion.rapports.Model.ManagedProjects;
import org.esprit.gestion.rapports.persistence.AssignResponseState;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.utils.AssignState;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class ProjectsBean implements Serializable {

	/************ Attributes *************/
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat dateFormat;
	private List<Domain> projDomainList;
	private boolean assignedCoach;
	private boolean toAssignCoach;
	private String dialogHeaderAssignCoach;
	private boolean assignedCorrector;
	private boolean toAssignCorrector;
	private String dialogHeaderAssignCorrector;
	private AssignState assignCoachState;
	private AssignState assignCorrectorState;

	// MBean-------------------------------------------
	@ManagedProperty(value = "#{tabViewIndexBean}")
	private TabViewIndexBean tabViewBean;

	public void setTabViewBean(TabViewIndexBean tabViewBean) {
		this.tabViewBean = tabViewBean;
	}

	// EJB_CRUD----------------------------------------
	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;

	// EJB_Facade--------------------------------------
	@EJB
	private IProjectFacadeLocal projFacade;
	@EJB
	private IDomainFacadeLocal domainFacade;

	// vars ------------------------------------------
	private ManagedProjects selectedProject;
	private List<Project> listprojInProcess;
	private List<ManagedProjects> managedProj;
	private Project project;
	private final static ValidationState[] listStates;

	// intit listStates--------------------------------
	static {
		listStates = new ValidationState[6];
		listStates[0] = ValidationState.VALID;
		listStates[1] = ValidationState.WAITINGDEPO;
		listStates[2] = ValidationState.DEPOSED;
		listStates[3] = ValidationState.WAITINGSOUT;
		listStates[4] = ValidationState.SOUTVALID;
		listStates[5] = ValidationState.SOUTNONVALID;

	}

	/********************** PostConstruct ***************************/
	@PostConstruct
	public void init() {

		// retrieve project list In Process------------------------
		setListproj(new ArrayList<Project>());
		setListproj(projFacade.listProjectsInProcess());

		// format project List In Process-------------------------

		managedProj = new ArrayList<ManagedProjects>();

		managedProj = formatProjList(listprojInProcess, managedProj);
		dateFormat = new SimpleDateFormat("dd-M-yyyy");

		// init project domain list
		projDomainList = new ArrayList<Domain>();

		// init affect Coach
		assignedCoach = false;
		toAssignCoach = false;
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

			// find teacher roles
			teacherRoles = projFacade.findTeacherRolesToProj(project.getId());

			if (teacherRoles != null) {

				for (int j = 0; j < teacherRoles.size(); j++) {

					if (teacherRoles.get(j).getRole()
							.equals(TeacherRoleType.ENCADRANT)) {

						coachFirstName = teacherRoles.get(j).getTeacher()
								.getFirstName();

						coachName = teacherRoles.get(j).getTeacher()
								.getLastName();

					} else if (teacherRoles.get(j).getRole()
							.equals(TeacherRoleType.RAPPORTEUR)) {

						correctorFirstName = teacherRoles.get(j).getTeacher()
								.getFirstName();

						correctorName = teacherRoles.get(j).getTeacher()
								.getLastName();
					}
				}
			}
			ManagedProjects managedproj = new ManagedProjects(project.getId(),
					project.getTopic(), project.getValidationState(),
					project.getProjectDomains(), project.getStartDate(),
					project.getAcademicYear(), project.getCompanycoach()
							.getCompany().getName(), project.getCompanycoach()
							.getCompany().getAdress(), 0, coachName,
					coachFirstName, correctorName, correctorFirstName, project
							.getCompanycoach().getId(), project
							.getCompanycoach().getLastName(), project
							.getCompanycoach().getFirstName(), project
							.getCompanycoach().getEmail(), project
							.getCompanycoach().getPhoneNumber(), project
							.getCompanycoach().getPosition(), project
							.getStudent().getId(), project.getStudent()
							.getLastName(),
					project.getStudent().getFirstName(), project.getStudent()
							.getRegistrationNumber(),project.getFonctionnalitites());

			managedprojList.add(managedproj);

		}
		return managedprojList;

	}

	/****************** Listners *********************/
	public void handleClose() {
		int tabIndex;
		tabIndex = tabViewBean.getTabIndex();
		try {
			RequestContext.getCurrentInstance().execute("location.reload();");
		} catch (Exception e) {
		}
		tabViewBean.setTabIndex(tabIndex);
	}

	public void assignCoachMenu() {
		assignCoachState = new AssignState();
		assignCoachState = projFacade.findCoachAssignement(selectedProject
				.getIdPorj());

		if (assignCoachState == null
				|| assignCoachState.getResponseState().equals(
						AssignResponseState.CANCELED)
				|| assignCoachState.getResponseState().equals(
						AssignResponseState.REFUSED)) {

			dialogHeaderAssignCoach = "Affecter un encadrant";
			assignedCoach = false;
			toAssignCoach = true;

		} else {

			dialogHeaderAssignCoach = "Etat d'affectation - Encadrant";
			assignedCoach = true;
			toAssignCoach = false;
		}
	}

	public void assignCorrectorMenu() {

		// Corrector Assignement state
		assignCorrectorState = new AssignState();

		assignCorrectorState = projFacade
				.findCorrectorAssignement(selectedProject.getIdPorj());

		if (assignCorrectorState == null
				|| assignCorrectorState.getResponseState().equals(
						AssignResponseState.CANCELED)
				|| assignCorrectorState.getResponseState().equals(
						AssignResponseState.REFUSED)) {
			setDialogHeaderAssignCorrector("Affecter un rapporteur");
			setAssignedCorrector(false);
			setToAssignCorrector(true);

		} else {

			setDialogHeaderAssignCorrector("Etat d'affectation - Rapporteur");
			setAssignedCorrector(true);
			setToAssignCorrector(false);
		}
	}

	public void findDomainList(ActionEvent event) {
		Project proj = new Project();
		proj.setId(selectedProject.getIdPorj());
		projDomainList = domainFacade.listProjectDomain(proj);
	}

	public void deleteProject(ActionEvent event) {
		Project projectToDelete = new Project();
		projectToDelete.setId(selectedProject.getIdPorj());
		projFacade.deleteProject(projectToDelete);
		try {
			RequestContext.getCurrentInstance().execute(
					"confirmationRemove.hide();");

			RequestContext.getCurrentInstance().execute(" location.reload();");
		} catch (Exception e) {
		}

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
		return listprojInProcess;
	}

	public void setListproj(List<Project> listproj) {
		this.listprojInProcess = listproj;
	}

	public ValidationState[] getListstates() {
		return listStates;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public List<Domain> getProjDomainList() {
		return projDomainList;
	}

	public void setProjDomainList(List<Domain> projDomainList) {
		this.projDomainList = projDomainList;
	}

	public boolean isAssignedCoach() {
		return assignedCoach;
	}

	public void setAssignedCoach(boolean assignedCoach) {
		this.assignedCoach = assignedCoach;
	}

	public boolean isToAssignCoach() {
		return toAssignCoach;
	}

	public void setToAssignCoach(boolean toAssignCoach) {
		this.toAssignCoach = toAssignCoach;
	}

	public String getDialogHeaderAssignCoach() {
		return dialogHeaderAssignCoach;
	}

	public void setDialogHeaderAssignCoach(String dialogHeaderAssignCoach) {
		this.dialogHeaderAssignCoach = dialogHeaderAssignCoach;
	}

	public AssignState getAssignCoachState() {
		return assignCoachState;
	}

	public void setAssignCoachState(AssignState assignCoachState) {
		this.assignCoachState = assignCoachState;
	}

	public AssignState getAssignCorrectorState() {
		return assignCorrectorState;
	}

	public void setAssignCorrectorState(AssignState assignCorrectorState) {
		this.assignCorrectorState = assignCorrectorState;
	}

	public boolean isAssignedCorrector() {
		return assignedCorrector;
	}

	public void setAssignedCorrector(boolean assignedCorrector) {
		this.assignedCorrector = assignedCorrector;
	}

	public boolean isToAssignCorrector() {
		return toAssignCorrector;
	}

	public void setToAssignCorrector(boolean toAssignCorrector) {
		this.toAssignCorrector = toAssignCorrector;
	}

	public String getDialogHeaderAssignCorrector() {
		return dialogHeaderAssignCorrector;
	}

	public void setDialogHeaderAssignCorrector(
			String dialogHeaderAssignCorrector) {
		this.dialogHeaderAssignCorrector = dialogHeaderAssignCorrector;
	}

}
