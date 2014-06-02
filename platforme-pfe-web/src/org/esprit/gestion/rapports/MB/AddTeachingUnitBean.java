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
	private List<Domain> listDomSource;
	private List<String> listDomTargetId;
	private List<Domain> listDomTarget;

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

		// init domains to select
		listDomSource = new ArrayList<Domain>();

		listDomSource = domainFacade.allDomains();

		if (listDomSource == null) {
			notExistDom = true;
			existDom = false;
		} else {
			notExistDom = false;
			existDom = true;
			listDomTargetId = new ArrayList<String>();

		}

	}

	public void notRenderAffectToDom(ActionEvent event) {
		notAffectToDom = true;
		affectToDom = false;
		existDom = false;
		notExistDom = false;
	}

	public void addTeachingUnit(ActionEvent event) {
		String addResult;

		if (affectToDom) {

			listDomTarget = new ArrayList<Domain>();

			for (int i = 0; i < listDomTargetId.size(); i++) {
				int id = Integer.parseInt(listDomTargetId.get(i));
				Domain dom = new Domain();
				dom.setId(id);
				listDomTarget.add(dom);

			}

			addResult = teachUnitFacade.addTeachingUnit(teachUnitToDB,
					listDomTarget);
		} else {
			addResult = teachUnitFacade.addTeachingUnit(teachUnitToDB, null);
		}

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

	public List<Domain> getListDomSource() {
		return listDomSource;
	}

	public void setListDomSource(List<Domain> listDomSource) {
		this.listDomSource = listDomSource;
	}

	public List<String> getListDomTargetId() {
		return listDomTargetId;
	}

	public void setListDomTargetId(List<String> listDomTargetId) {
		this.listDomTargetId = listDomTargetId;
	}

	public List<Domain> getListDomTarget() {
		return listDomTarget;
	}

	public void setListDomTarget(List<Domain> listDomTarget) {
		this.listDomTarget = listDomTarget;
	}

}
