package org.esprit.gestion.rapports.MB;

import java.text.SimpleDateFormat;
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

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.SubmissionEvent;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISubmissionFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SubmissionEventBean {

	private List<SubmissionEvent> allSubmissionEvent;
	private SubmissionEvent selectedSubmitEvent;
	private SimpleDateFormat dateFormat;
	private int nbreSessionOpened;
	private SubmissionEvent submitEventToDB;

	@EJB
	ISubmissionFacadeLocal submitEventFacade;

	// MBean-------------------------------------------
	@ManagedProperty(value = "#{tabViewIndexBean}")
	private TabViewIndexBean tabViewBean;
	
	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;


	public void setTabViewBean(TabViewIndexBean tabViewBean) {
		this.tabViewBean = tabViewBean;
	}

	/******************************* init method ******************************/
	@PostConstruct
	public void init() {

		if (submitEventFacade.sessionOpened()) {
			setNbreSessionOpened(1);
		} else {
			setNbreSessionOpened(0);
		}
		allSubmissionEvent = new ArrayList<SubmissionEvent>();
		allSubmissionEvent = submitEventFacade.listAllSubmitEvent();

		dateFormat = new SimpleDateFormat("dd-M-yyyy");

		submitEventToDB = new SubmissionEvent();

	}

	/******************************* getter && setter **************************/
	public void createSubmitEvent(ActionEvent event) {
		if (submitEventToDB.getDueDate().equals(null)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erreur", "Veuillez saisir une date de clôture");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else if (submitEventToDB.getStartDate().equals(null)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erreur", "Veuillez sélectionner une date d'ouverture");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {

			int compare = submitEventToDB.getStartDate().compareTo(
					submitEventToDB.getDueDate());
			if (compare < 0) {
				int tabIndex;
				tabIndex = tabViewBean.getTabIndex();
				try {
					RequestContext.getCurrentInstance().execute(
							"location.reload();");
				} catch (Exception e) {
				}
				tabViewBean.setTabIndex(tabIndex);
				
				//create session
				submitEventFacade.createSubmitEvent(submitEventToDB,(Administrator) authBean.getUser());
			}

			else {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Erreur",
						"La date d'ouverture ne peut pas être ultérieure à la date de clôture");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

	}

	public void handleClose() {
		int tabIndex;
		tabIndex = tabViewBean.getTabIndex();
		try {
			RequestContext.getCurrentInstance().execute("location.reload();");
		} catch (Exception e) {
		}
		tabViewBean.setTabIndex(tabIndex);

	}

	public void tocreateEvent(ActionEvent event) {

		if (submitEventFacade.sessionOpened()) {
			try {
				RequestContext.getCurrentInstance().execute(
						"existSessionDialog.show();");
			} catch (Exception e) {
			}
		}

		else {
			try {
				RequestContext.getCurrentInstance().execute(
						"createSessionDialog.show();");
			} catch (Exception e) {
			}

		}

	}

	/******************************* constructor ******************************/
	public SubmissionEventBean() {
		super();
	}

	/****************************** getter && setter ***************************/
	public List<SubmissionEvent> getAllSubmissionEvent() {
		return allSubmissionEvent;
	}

	public void setAllSubmissionEvent(List<SubmissionEvent> allSubmissionEvent) {
		this.allSubmissionEvent = allSubmissionEvent;
	}

	public SubmissionEvent getSelectedSubmitEvent() {
		return selectedSubmitEvent;
	}

	public void setSelectedSubmitEvent(SubmissionEvent selectedSubmitEvent) {
		this.selectedSubmitEvent = selectedSubmitEvent;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getNbreSessionOpened() {
		return nbreSessionOpened;
	}

	public void setNbreSessionOpened(int nbreSessionOpened) {
		this.nbreSessionOpened = nbreSessionOpened;
	}

	public SubmissionEvent getSubmitEventToDB() {
		return submitEventToDB;
	}

	public void setSubmitEventToDB(SubmissionEvent submitEventToDB) {
		this.submitEventToDB = submitEventToDB;
	}

	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

}
