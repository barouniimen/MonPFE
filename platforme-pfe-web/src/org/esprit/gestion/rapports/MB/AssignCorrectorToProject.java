package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICorrectorFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class AssignCorrectorToProject {

	private boolean sameDom;
	private Teacher selectedCorrector;
	private List<Teacher> listPotentionalCorrectors;
	private List<Domain> selectedProjDom;
	private String cancel;

	@ManagedProperty(value = "#{projectsBean}")
	private ProjectsBean projectBean;
	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;

	@EJB
	ICorrectorFacadeLocal correctorFacade;

	@EJB
	IProjectFacadeLocal projFacade;

	@EJB
	IDomainFacadeLocal domainFacade;
	
	@ManagedProperty(value = "#{tabViewIndexBean}")
	private TabViewIndexBean tabViewBean;

	/******************************** init method ************************************/
	@PostConstruct
	public void init() {
		selectedCorrector = new Teacher();
		setSameDom(false);
		listPotentionalCorrectors = correctorFacade
				.listerCorrectorDisponibles();

	}

	/****************************** action listeners *******************************************/

	public void renderSameDom() {

		if (sameDom) {
			// same dom teachers

			// init list of domains to render
			Project project = new Project();
			project.setId(projectBean.getSelectedProject().getIdPorj());
			setSelectedProjDom(domainFacade.listProjectDomain(project));
			// init project domains to search teacher by
			List<String> projDomToSearch = new ArrayList<String>();
			for (int i = 0; i < selectedProjDom.size(); i++) {
				projDomToSearch.add(selectedProjDom.get(i).getDomainName());
			}
			// find potentional correctors
			listPotentionalCorrectors = correctorFacade
					.listCorrectorsSameDom(projDomToSearch);

			if (listPotentionalCorrectors == null) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Zero Disponibilité!!",
						"Il n'existe aucun enseignant du même domaine de compétence que le projet!!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

		else {
			// retreive all teachers
			listPotentionalCorrectors = correctorFacade
					.listerCorrectorDisponibles();
			if (listPotentionalCorrectors == null) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Zero Disponibilité!!",
						"Aucun enseigants trouvé!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

	}

	public void assignCorrectorToProj(ActionEvent event) {
		int tabIndex = tabViewBean.getTabIndex();
		
		if (selectedCorrector.getId() == -1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Donnée manquante!!",
					"Veuillez sélectionner un enseigant! ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {

			projFacade.assignCorrectorToProject(selectedCorrector, projectBean
					.getSelectedProject().getIdPorj(), authBean.getUser());

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Enseignant notifié!!",
					"Un message a été envoyé à l'enseigant sélectionné ");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			try {
				RequestContext.getCurrentInstance().execute(
						"location.reload();");
			} catch (Exception e) {
			}
			
			tabViewBean.setTabIndex(tabIndex);
		}
	}


	public void cancelCorrectorAssign(){

		int tabIndex= tabViewBean.getTabIndex();
		
		if (cancel.equals("true")) {
			Teacher corrector = new Teacher();
			corrector.setId(projectBean.getAssignCorrectorState().getIdTeacher());

			Project proj = new Project();
			proj.setId(projectBean.getAssignCorrectorState().getIdProj());

			projFacade.cancelCorrectorToProject(corrector, proj, authBean.getUser()
					.getId());
			try {
				RequestContext.getCurrentInstance().execute(
						"CorrectorDialog.hide();");
			} catch (Exception e) {
			}

		} else if (cancel.equals("false")) {
			try {
				RequestContext.getCurrentInstance().execute(
						"location.reload();");
			} catch (Exception e) {
			}
			
			tabViewBean.setTabIndex(tabIndex);
		}
	}
	/************************************* constructor *****************************************/
	public AssignCorrectorToProject() {
		super();
	
	}

	/*********************************** getter & setter *************************************/

	public boolean isSameDom() {
		return sameDom;
	}

	public void setSameDom(boolean sameDom) {
		this.sameDom = sameDom;
	}

	public Teacher getSelectedCorrector() {
		return selectedCorrector;
	}

	public void setSelectedCorrector(Teacher selectedCorrector) {
		this.selectedCorrector = selectedCorrector;
	}

	public List<Teacher> getListPotentionalCorrectors() {
		return listPotentionalCorrectors;
	}

	public void setListPotentionalCorrectors(
			List<Teacher> listPotentionalCorrectors) {
		this.listPotentionalCorrectors = listPotentionalCorrectors;
	}

	public List<Domain> getSelectedProjDom() {
		return selectedProjDom;
	}

	public void setSelectedProjDom(List<Domain> selectedProjDom) {
		this.selectedProjDom = selectedProjDom;
	}

	public void setProjectBean(ProjectsBean projectBean) {
		this.projectBean = projectBean;
	}

	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}
}
