package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeachingUnitFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class AddTeachingUnitBean {

	private TeachingUnit teachUnitToDB;
	private boolean notAffectToDom;
	private boolean affectToDom;
	private List<Domain> listDomToSelect;
	private Domain selectedDomain;
	private boolean notExistDom;
	private boolean existDom;

	/***** EJB facade *****/
	@EJB
	ITeachingUnitFacadeLocal teachUnitFacade;
	@EJB
	IDomainFacadeLocal domainFacade;

	/********************************** init method ******************************************/
	@PostConstruct
	public void init() {
		teachUnitToDB = new TeachingUnit();
		notAffectToDom = false;
		affectToDom = false;
		notExistDom = false;
		existDom = false;
	}

	/******************************** action listeners ***************************************/
	public void renderAffectToDom(ActionEvent event) {
		notAffectToDom = false;
		affectToDom = true;
		listDomToSelect = new ArrayList<Domain>();
		selectedDomain = new Domain();
		listDomToSelect = domainFacade.allDomains();
		if (listDomToSelect == null) {
			notExistDom = true;
			existDom = false;
		} else {
			notExistDom = false;
			existDom = true;
		}
	}

	public void notRenderAffectToDom(ActionEvent event) {
		notAffectToDom = true;
		affectToDom = false;
	}

	public void addTeachingUnit(ActionEvent event) {
		String addResult;

		addResult = teachUnitFacade.addTeachingUnit(teachUnitToDB,
				selectedDomain.getId());

		if (addResult == "name exist") {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le nom de l'unité est déjà utilisé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else if (addResult == "local exist") {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le local est déjà utilisé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else if (addResult == "success") {
			try {
				RequestContext.getCurrentInstance().execute(
						" location.reload();");
			} catch (Exception e) {
			}
		}
	}

	/*********************************** constructor ****************************************/
	public AddTeachingUnitBean() {
		super();
	}

	/******************************* getter & setter ****************************************/
	public TeachingUnit getTeachUnitToDB() {
		return teachUnitToDB;
	}

	public void setTeachUnitToDB(TeachingUnit teachUnitToDB) {
		this.teachUnitToDB = teachUnitToDB;
	}

	public boolean isNotAffectToDom() {
		return notAffectToDom;
	}

	public void setNotAffectToDom(boolean notAffectToDom) {
		this.notAffectToDom = notAffectToDom;
	}

	public boolean isAffectToDom() {
		return affectToDom;
	}

	public void setAffectToDom(boolean affectToDom) {
		this.affectToDom = affectToDom;
	}

	public List<Domain> getListDomToSelect() {
		return listDomToSelect;
	}

	public void setListDomToSelect(List<Domain> listDomToSelect) {
		this.listDomToSelect = listDomToSelect;
	}

	public Domain getSelectedDomain() {
		return selectedDomain;
	}

	public void setSelectedDomain(Domain selectedDomain) {
		this.selectedDomain = selectedDomain;
	}

	public boolean isNotExistDom() {
		return notExistDom;
	}

	public void setNotExistDom(boolean notExistDom) {
		this.notExistDom = notExistDom;
	}

	public boolean isExistDom() {
		return existDom;
	}

	public void setExistDom(boolean existDom) {
		this.existDom = existDom;
	}

}
