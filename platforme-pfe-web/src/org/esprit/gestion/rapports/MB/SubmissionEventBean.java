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
import org.esprit.gestion.rapports.persistence.AssignResponseState;
import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.ReportState;
import org.esprit.gestion.rapports.persistence.SubmissionEvent;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IReportFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISubmissionFacadeLocal;
import org.esprit.gestion.rapports.utils.AssignState;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SubmissionEventBean {

	private List<SubmissionEvent> allSubmissionEvent;
	private SubmissionEvent selectedSubmitEvent;
	private SimpleDateFormat dateFormat;
	private int nbreSessionOpened;
	private SubmissionEvent submitEventToDB;
	
	private String startDate;
	private String endDate;
	private List<Report> submittedReports;
	private Report selectedReport;

	private boolean toAssignCorrector;
	private boolean assignedCorrector;
	private AssignState assignCorrectorState;
	private String dialogHeaderAssignCorrector;

	@EJB
	ISubmissionFacadeLocal submitEventFacade;
	@EJB
	private IProjectFacadeLocal projFacade;
	@EJB
	private IReportFacadeLocal reportFacade;
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

		if (submitEventFacade.sessionOpened() !=null) {
			setNbreSessionOpened(1);
		} else {
			setNbreSessionOpened(0);
		}
		allSubmissionEvent = new ArrayList<SubmissionEvent>();
		allSubmissionEvent = submitEventFacade.listAllSubmitEvent();

		dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		submitEventToDB = new SubmissionEvent();

		toAssignCorrector = false;
		assignedCorrector = false;

		

	}

	/************************************** Listeners ***********************************************/
	public void cancelSubmission(ActionEvent event) {

		if (selectedReport == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Aucune sélection", "Veuillez sélectionner un rapport");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {
			selectedReport.setState(ReportState.WAITINGDEPO);
			reportFacade.update(selectedReport);

			try {
				RequestContext.getCurrentInstance().execute(
						"cancelSubmitDialog.hide();");
			} catch (Exception e) {
			}
		}
	}

	public void assignCorrectorMenu() {
		if (selectedReport == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Aucune sélection", "Veuillez sélectionner un rapport");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {
			// Corrector Assignement state
			assignCorrectorState = new AssignState();

			assignCorrectorState = projFacade
					.findCorrectorAssignement(selectedReport.getProject()
							.getId());

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

			try {
				RequestContext.getCurrentInstance().execute(
						"CorrectorDialog.show();");
			} catch (Exception e) {
			}
		}
	}

	public void showReports(ActionEvent event) {
		if (selectedSubmitEvent == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Aucune sélection",
					"Veuillez sélectionner une session de dépôt");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {
			startDate = dateFormat.format(selectedSubmitEvent.getStartDate());
			endDate = dateFormat.format(selectedSubmitEvent.getDueDate());

			setSubmittedReports(new ArrayList<Report>());

			setSubmittedReports(submitEventFacade
					.listSubmittedReports(selectedSubmitEvent));

			selectedReport = new Report();

			try {
				RequestContext.getCurrentInstance().execute(
						"showReportsDialog.show();");
			} catch (Exception e) {
			}
		}
	}

	public void editSubmitEvent(ActionEvent event) {

		submitEventToDB = selectedSubmitEvent;
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

				// create session
				submitEventFacade.updateSubmitEvent(submitEventToDB,
						(Administrator) authBean.getUser());
			}

			else {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Erreur",
						"La date d'ouverture ne peut pas être ultérieure à la date de clôture");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

	}

	public void toEdit(ActionEvent event) {
		if (selectedSubmitEvent == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Aucune sélection",
					"Veuillez sélectionner une session de dépôt");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {
			setStartDate(dateFormat.format(selectedSubmitEvent.getStartDate()));
			setEndDate(dateFormat.format(selectedSubmitEvent.getDueDate()));
			try {
				RequestContext.getCurrentInstance().execute(
						"editSessionDialog.show();");
			} catch (Exception e) {
			}
		}
	}

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

				// create session
				submitEventFacade.createSubmitEvent(submitEventToDB,
						(Administrator) authBean.getUser());
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

		if (submitEventFacade.sessionOpened() == null) {
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



	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Report> getSubmittedReports() {
		return submittedReports;
	}

	public void setSubmittedReports(List<Report> submittedReports) {
		this.submittedReports = submittedReports;
	}

	public Report getSelectedReport() {
		return selectedReport;
	}

	public void setSelectedReport(Report selectedReport) {
		this.selectedReport = selectedReport;
	}

	public boolean isToAssignCorrector() {
		return toAssignCorrector;
	}

	public void setToAssignCorrector(boolean toAssignCorrector) {
		this.toAssignCorrector = toAssignCorrector;
	}

	public boolean isAssignedCorrector() {
		return assignedCorrector;
	}

	public void setAssignedCorrector(boolean assignedCorrector) {
		this.assignedCorrector = assignedCorrector;
	}

	public String getDialogHeaderAssignCorrector() {
		return dialogHeaderAssignCorrector;
	}

	public void setDialogHeaderAssignCorrector(
			String dialogHeaderAssignCorrector) {
		this.dialogHeaderAssignCorrector = dialogHeaderAssignCorrector;
	}

	public AssignState getAssignCorrectorState() {
		return assignCorrectorState;
	}

	public void setAssignCorrectorState(AssignState assignCorrectorState) {
		this.assignCorrectorState = assignCorrectorState;
	}

}
