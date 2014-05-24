package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;

@ManagedBean
@RequestScoped
public class affectCoachToProjectBean {

	@ManagedProperty(value = "#{projectsBean}")
	private ProjectsBean projectBean;
	private boolean allCoachs;
	private boolean sameDom;
	private int coachingHoursMax;
	private List<Domain> selectedProjDom;
	private List<Teacher> listPotentionalCoachs;
	private Teacher selectedCoach;
	private boolean renderListCoachDom;
	private boolean notRenderListCoachDom;

	@EJB
	IProjectFacadeLocal projFacade;

	@EJB
	IDomainFacadeLocal domainFacade;

	@EJB
	ICoachFacadeLocal coachFacade;

	/******************************** init method ************************************/
	@PostConstruct
	public void init() {
		setAllCoachs(false);
		setSameDom(false);
		coachingHoursMax = 10;
		// init vars select by
		// domain------------------------------------------------------------
		setSelectedProjDom(new ArrayList<Domain>());
		setListPotentionalCoachs(new ArrayList<Teacher>());
		selectedCoach = new Teacher();
		renderListCoachDom = false;
		notRenderListCoachDom = false;
	}

	/******************************* action listeners *********************************/
	public void renderSameDom() {

		// render vars
		sameDom = true;
		allCoachs = false;

		// init list of domains to render---------------------------------------
		Project project = new Project();
		project.setId(projectBean.getSelectedProject().getIdPorj());
		setSelectedProjDom(domainFacade.listProjectDomain(project));
		// init project domains to search teacher by----------------------------
		List<String> projDomToSearch = new ArrayList<String>();
		for (int i = 0; i < selectedProjDom.size(); i++) {
			projDomToSearch.add(selectedProjDom.get(i).getDomainName());
		}
		// find potentional coachs
		listPotentionalCoachs = coachFacade.listerCoachDisponibles(
				coachingHoursMax, projDomToSearch);
		System.out.println("on view potionianal: " + listPotentionalCoachs);
		if (listPotentionalCoachs == null) {
			System.out.println("equals null!!");
			setRenderListCoachDom(false);
			setNotRenderListCoachDom(true);
		} else {
			renderListCoachDom = true;
			setNotRenderListCoachDom(false);
		}
	}

	public void renderAllCoachs() {
		sameDom = false;
		allCoachs = true;

	}

	/************************************ constructor *********************************/

	public affectCoachToProjectBean() {
		super();
	}

	/*********************************** getter & setter ******************************/

	public void setProjectBean(ProjectsBean projectBean) {
		this.projectBean = projectBean;
	}

	public boolean isAllCoachs() {
		return allCoachs;
	}

	public void setAllCoachs(boolean allCoachs) {
		this.allCoachs = allCoachs;
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

	public boolean isRenderListCoachDom() {
		return renderListCoachDom;
	}

	public void setRenderListCoachDom(boolean renderListCoachDom) {
		this.renderListCoachDom = renderListCoachDom;
	}

	public boolean isNotRenderListCoachDom() {
		return notRenderListCoachDom;
	}

	public void setNotRenderListCoachDom(boolean notRenderListCoachDom) {
		this.notRenderListCoachDom = notRenderListCoachDom;
	}

}
