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
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class affectCoachToProjectBean {

	@ManagedProperty(value = "#{projectsBean}")
	private ProjectsBean projectBean;
	private boolean sameDom;
	private int coachingHoursMax;
	private List<Domain> selectedProjDom;
	private List<Teacher> listPotentionalCoachs;
	private Teacher selectedCoach;
	private int selectedCoachID;

	@EJB
	IProjectFacadeLocal projFacade;

	@EJB
	IDomainFacadeLocal domainFacade;

	@EJB
	ICoachFacadeLocal coachFacade;

	/******************************** init method ************************************/
	@PostConstruct
	public void init() {

		setSameDom(false);
		// init vars select by domain------------------------------------------
		setSelectedProjDom(new ArrayList<Domain>());
		
		

	}

	/******************************* action listeners *********************************/

	public void coachHoursChange() {
		setListPotentionalCoachs(new ArrayList<Teacher>());
		selectedCoach = new Teacher();

		// retreive all teachers
		listPotentionalCoachs = coachFacade.listAllCoach(coachingHoursMax);
		if (listPotentionalCoachs == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Zero Disponibilité!!",
					"Tous les enseignants ont plus que " + "'"
							+ coachingHoursMax + "'"
							+ " heures d'encadrement!!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void affectCoachToProj(ActionEvent event) {

		
		System.out.println("calling facade!!!");

		projFacade.assignCoachToProject(selectedCoach, projectBean.getSelectedProject().getIdPorj());

	
		
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Enseignant notifié!!",
				"Un message a été envoyé à l'enseigant sélectionné ");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		try {
			RequestContext.getCurrentInstance().execute("CoachDialog.hide();growlCoach.show();");
		} catch (Exception e) {
		}

	}

	public void renderSameDom() {
		selectedCoach = new Teacher();
		if (sameDom) {
			// same dom teachers

			// init list of domains to
			// render---------------------------------------
			Project project = new Project();
			project.setId(projectBean.getSelectedProject().getIdPorj());
			setSelectedProjDom(domainFacade.listProjectDomain(project));
			// init project domains to search teacher
			// by----------------------------
			List<String> projDomToSearch = new ArrayList<String>();
			for (int i = 0; i < selectedProjDom.size(); i++) {
				projDomToSearch.add(selectedProjDom.get(i).getDomainName());
			}
			// find potentional coachs
			listPotentionalCoachs = coachFacade.listerCoachSameDom(
					coachingHoursMax, projDomToSearch);

			if (listPotentionalCoachs == null) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Zero Disponibilité!!",
						"Il n'existe aucun enseignant du même domaine de compétence que le projet!!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

		else {
			// retreive all teachers
			listPotentionalCoachs = coachFacade.listAllCoach(coachingHoursMax);
			if (listPotentionalCoachs == null) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Zero Disponibilité!!",
						"Tous les enseignants ont plus que " + "'"
								+ coachingHoursMax + "'"
								+ " heures d'encadrement!!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

	}

	/************************************ constructor *********************************/

	public affectCoachToProjectBean() {
		super();
	}

	/*********************************** getter & setter ******************************/

	public void setProjectBean(ProjectsBean projectBean) {
		this.projectBean = projectBean;
	}

	public boolean isSameDom() {
		return sameDom;
	}

	public void setSameDom(boolean sameDom) {
		this.sameDom = sameDom;
	}

	public int getCoachingHoursMax() {
		return coachingHoursMax;
	}

	public void setCoachingHoursMax(int coachingHoursMax) {
		this.coachingHoursMax = coachingHoursMax;
	}

	public List<Domain> getSelectedProjDom() {
		return selectedProjDom;
	}

	public void setSelectedProjDom(List<Domain> selectedProjDom) {
		this.selectedProjDom = selectedProjDom;
	}

	public List<Teacher> getListPotentionalCoachs() {
		return listPotentionalCoachs;
	}

	public void setListPotentionalCoachs(List<Teacher> listPotentionalCoachs) {
		this.listPotentionalCoachs = listPotentionalCoachs;
	}

	public Teacher getSelectedCoach() {
		return selectedCoach;
	}

	public void setSelectedCoach(Teacher selectedCoach) {
		this.selectedCoach = selectedCoach;
	}

	public int getSelectedCoachID() {
		return selectedCoachID;
	}

	public void setSelectedCoachID(int selectedCoachID) {
		this.selectedCoachID = selectedCoachID;
	}

}
